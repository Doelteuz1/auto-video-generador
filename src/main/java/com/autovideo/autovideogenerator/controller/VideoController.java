package com.autovideo.autovideogenerator.controller;

import com.autovideo.autovideogenerator.model.dto.ScriptResponse;
import com.autovideo.autovideogenerator.model.dto.VideoRequest;
import com.autovideo.autovideogenerator.services.AudioService;
import com.autovideo.autovideogenerator.services.ScriptService;
import com.autovideo.autovideogenerator.services.VideoAssemblyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final ScriptService scriptService;
    private final AudioService audioService;
    private final VideoAssemblyService videoAssemblyService;

    public VideoController(ScriptService scriptService,
                           AudioService audioService,
                           VideoAssemblyService videoAssemblyService) {
        this.scriptService = scriptService;
        this.audioService = audioService;
        this.videoAssemblyService = videoAssemblyService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateVideo(@RequestBody VideoRequest request) {
        try {
            // 1. Generar guion
            ScriptResponse script = scriptService.generateScript(request.getTopic());

            // 2. Convertir texto a audio MP3
            String audioPath = audioService.generateAudioFromText(script.getScriptText(), "audio_temp");

            // 3. Ruta de un clip/imagen de fondo (puedes colocar una imagen fija en media_factory/background.jpg para probar)
            String backgroundPath = "./media_factory/background.mp4";

            // 4. Renderizar video final
            String finalVideoPath = videoAssemblyService.createVideo(audioPath, backgroundPath, "video_result");

            return ResponseEntity.ok("Video generado con éxito en: " + finalVideoPath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en la generación: " + e.getMessage());
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> probarBackend() {
        return ResponseEntity.ok("¡El generador de videos está en línea y funcionando perfectamente!");
    }

}