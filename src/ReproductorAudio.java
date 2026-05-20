package tarea_final;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class ReproductorAudio {
    
    public static void reproducirMusicaFondo(String nombreArchivo) {
        try {
            // Utilizamos getResource para que Java busque la música dentro del propio paquete 'tarea_final'
            URL urlMusica = ReproductorAudio.class.getResource(nombreArchivo);
            
            if (urlMusica != null) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(urlMusica);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                
                // Hace que la música se repita en bucle infinito
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                
            } else {
                System.err.println("Aviso: No se encontró el archivo de música: " + nombreArchivo);
                System.err.println("Asegúrate de que el archivo .wav esté dentro del paquete 'tarea_final'.");
            }
        } catch (Exception ex) {
            System.err.println("Error al reproducir el audio.");
            ex.printStackTrace();
        }
    }
}