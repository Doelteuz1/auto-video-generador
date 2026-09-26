package com.autovideo.autovideogenerator.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class VideoAssemblyService {

    @Value("${app.media.output-dir:./media_factory}")
    private String outputDir;

    /**
     * Ensambla un video uniendo un archivo de audio (.mp3) con un archivo de video o imagen de fondo (.mp4 / .jpg)
     */
    public String createVideo(String audioPath, String backgroundPath, String outputFileName) throws IOException, InterruptedException {
        String finalOutputPath = outputDir + "/" + outputFileName + ".mp4";

        // Comando FFmpeg para combinar el video/imagen con la pista de audio
        // -shortest hace que el video final dure exactamente lo mismo que el audio
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-y",                   // Sobrescribir archivo de salida si existe
                "-stream_loop", "-1",   // Repetir el video de fondo si es más corto que el audio
                "-i", backgroundPath,   // Entrada 1: Video o Imagen de fondo
                "-i", audioPath,        // Entrada 2: Audio (locución)
                "-c:v", "libx264",      // Codec de video estándar H.264
                "-tune", "stillimage",  // Optimización si la entrada es una imagen fija
                "-c:a", "aac",          // Codec de audio estándar AAC
                "-b:a", "192k",
                "-pix_fmt", "yuv420p",  // Formato de píxeles compatible con reproductores y redes sociales
                "-shortest",            // Cortar cuando termine el audio
                finalOutputPath
        );

        // Redirigir errores para poder ver la salida de FFmpeg en la consola si algo falla
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Video generado exitosamente en: " + finalOutputPath);
            return finalOutputPath;
        } else {
            throw new RuntimeException("Error al ensamblar el video con FFmpeg. Código de salida: " + exitCode);
        }
    }
}