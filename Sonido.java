/*
 Ejemplo original

 https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html

 */

// Aca hay que cambiar los path y los nombre de los archivos 

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {
    

    Clip sonido;

    public Sonido(String ubicacion){
        try {
            sonido = AudioSystem.getClip(); //se obtiene un clip de sonido
            sonido.open(AudioSystem.getAudioInputStream(new File(ubicacion)));  //se carga un fichero wav
        } catch (Exception e) {
            System.out.println("epaaaa" + e);
        }
    }

    public void comenzar(){
        sonido.start();
    }

    public void loop(){
        sonido.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void detener(){
        sonido.stop();
    }
}