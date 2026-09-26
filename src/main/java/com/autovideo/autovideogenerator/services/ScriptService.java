package com.autovideo.autovideogenerator.services;

import com.autovideo.autovideogenerator.model.dto.ScriptResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class ScriptService {

    private final WebClient webClient;

    @Value("${spring.ai.openai.api.key:}")
    private String apiKey;

    public ScriptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public ScriptResponse generateScript(String topic) {
        // Estructura simplificada del prompt para TikTok/Reels/Shorts
        String prompt = "Crea un guion corto de 30 segundos sobre: " + topic +
                ". Incluye un título atractivo y el texto que leerá el locutor.";

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        // Aquí se hace la llamada asíncrona/reactiva a la API
        // Por ahora simulamos la respuesta si aún no agregas la API Key
        if (apiKey == null || apiKey.isEmpty()) {
            return new ScriptResponse(
                    "Título de prueba sobre " + topic,
                    "Este es un guion generado de prueba para el tema: " + topic
            );
        }

        // Llamada real a OpenAI:
        Map response = webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Extraer texto de la respuesta JSON
        String generatedContent = "Guion de ejemplo generado";
        return new ScriptResponse("Video de " + topic, generatedContent);
    }
}