package com.autovideo.autovideogenerator.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AudioService {

    private final WebClient webClient;

    @Value("${elevenlabs.api.key:}")
    private String apiKey;

    @Value("${app.media.output-dir:./media_factory}")
    private String outputDir;

    public AudioService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.elevenlabs.io/v1").build();
    }

    public String generateAudioFromText(String text, String fileName) throws IOException {
        // Asegurar que exista el directorio media_factory
        Path directoryPath = Paths.get(outputDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String destinationPath = outputDir + "/" + fileName + ".mp3";

        // Si no hay API Key activa, creamos un archivo .mp3 simulación/dummy
        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("tu_elevenlabs_key_aqui")) {
            File dummyFile = new File(destinationPath);
            if (dummyFile.createNewFile()) {
                System.out.println("Audio simulado creado en: " + destinationPath);
            }
            return destinationPath;
        }

        // Llamada real a la API de ElevenLabs (Voice ID de ejemplo)
        String voiceId = "21m00Tcm4TlvDq8ikWAM";

        byte[] audioBytes = webClient.post()
                .uri("/text-to-speech/" + voiceId)
                .header("xi-api-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue("{\"text\": \"" + text + "\", \"model_id\": \"eleven_multilingual_v2\"}")
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        if (audioBytes != null) {
            try (FileOutputStream fos = new FileOutputStream(destinationPath)) {
                fos.write(audioBytes);
            }
        }

        return destinationPath;
    }
}