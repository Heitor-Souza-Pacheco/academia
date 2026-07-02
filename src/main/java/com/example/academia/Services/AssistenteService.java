package com.example.academia.Services;

import com.example.academia.Dtos.ChatMessageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

/**
 * Assistente virtual da Cranium. Fala com a API da Groq (gratuita, compatível
 * com o padrão OpenAI) a partir do backend, para que a chave nunca fique
 * exposta no front. Trocar de provedor é só mexer aqui.
 */
@Service
public class AssistenteService {

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final int MAX_HISTORICO = 20;
    private static final int MAX_TOKENS = 1024;

    private final String apiKey;
    private final String model;

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String SYSTEM_PROMPT = """
            Você é o assistente virtual da Cranium, uma academia. Seu papel é ajudar os
            usuários da plataforma de forma acolhedora, direta e motivadora.

            Você responde dúvidas sobre:
            - Uso da plataforma Cranium (encontrar e salvar fichas de treino, ver
              exercícios, assistir aos vídeos de demonstração, criar/editar fichas quando
              o usuário é administrador, verificação de e-mail e login).
            - Treino: montagem de treinos, execução de exercícios, séries e repetições,
              frequência, descanso, aquecimento e progressão de carga.
            - Dieta e nutrição de forma geral voltada a quem treina (alimentação
              pré/pós-treino, hidratação, ideias de refeições, noções de proteína/carbo/gordura).
            - Assuntos gerais de academia, condicionamento físico e hábitos saudáveis.

            Diretrizes:
            - Responda sempre em português do Brasil, com tom amigável e objetivo.
            - Seja conciso: use frases curtas e, quando fizer sentido, listas com hífens.
            - Dê orientações gerais e seguras. Você não é médico nem nutricionista:
              para lesões, dores, condições de saúde, uso de suplementos/medicamentos ou
              dietas restritivas, recomende procurar um profissional de saúde qualificado.
            - Se a pergunta fugir totalmente do universo de academia, treino, dieta ou do
              uso da plataforma, explique gentilmente que você é o assistente da Cranium e
              foca nesses temas.
            - Nunca invente recursos da plataforma que você não conhece; se não tiver
              certeza sobre um detalhe do app, oriente o usuário a explorar o menu ou falar
              com um administrador.
            """;

    public AssistenteService(
            @Value("${groq.api.key:}") String apiKey,
            @Value("${groq.model:llama-3.3-70b-versatile}") String model) {
        this.apiKey = apiKey == null ? "" : apiKey.trim();
        this.model = (model == null || model.isBlank()) ? "llama-3.3-70b-versatile" : model.trim();
    }

    public String responder(List<ChatMessageRequest> mensagens) {
        if (mensagens == null || mensagens.isEmpty()) {
            throw new RuntimeException("Envie ao menos uma mensagem para o assistente.");
        }

        String key = !apiKey.isBlank() ? apiKey : System.getenv("GROQ_API_KEY");
        if (key == null || key.isBlank()) {
            throw new RuntimeException("Assistente indisponível: a chave da API (Groq) não foi configurada.");
        }

        // Corpo no formato OpenAI/Groq: primeiro a system message, depois o histórico.
        ObjectNode body = mapper.createObjectNode();
        body.put("model", model);
        body.put("max_tokens", MAX_TOKENS);
        body.put("temperature", 0.6);
        ArrayNode msgs = body.putArray("messages");
        msgs.addObject().put("role", "system").put("content", SYSTEM_PROMPT);

        int inicio = Math.max(0, mensagens.size() - MAX_HISTORICO);
        for (int i = inicio; i < mensagens.size(); i++) {
            ChatMessageRequest m = mensagens.get(i);
            String texto = m.getConteudo() == null ? "" : m.getConteudo().trim();
            if (texto.isEmpty()) continue;
            String role = "assistant".equalsIgnoreCase(m.getRole()) ? "assistant" : "user";
            msgs.addObject().put("role", role).put("content", texto);
        }

        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(GROQ_URL))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + key)
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() < 200 || res.statusCode() >= 300) {
                String detalhe = extrairErro(res.body());
                throw new RuntimeException("Não foi possível falar com o assistente agora."
                        + (detalhe != null ? " (" + detalhe + ")" : ""));
            }

            JsonNode conteudo = mapper.readTree(res.body())
                    .path("choices").path(0).path("message").path("content");
            String resposta = conteudo.isTextual() ? conteudo.asText().trim() : "";
            return resposta.isEmpty()
                    ? "Desculpe, não consegui gerar uma resposta agora. Tente reformular a pergunta."
                    : resposta;

        } catch (RuntimeException e) {
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Não foi possível falar com o assistente agora. Tente novamente em instantes.");
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível falar com o assistente agora. Tente novamente em instantes.");
        }
    }

    // Extrai a mensagem de erro que a Groq devolve em { "error": { "message": ... } }.
    private String extrairErro(String body) {
        try {
            JsonNode msg = mapper.readTree(body).path("error").path("message");
            return msg.isTextual() ? msg.asText() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
