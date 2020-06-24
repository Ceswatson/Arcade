
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {
    
    Clip sonido;
    boolean sonando=false;

    public Sonido(String ubicacion){
        try {
            sonido = AudioSystem.getClip(); //se obtiene un clip de sonido
            sonido.open(AudioSystem.getAudioInputStream(new File(ubicacion)));  //se carga un fichero wav
        } catch (Exception e) {
            System.out.println("epaaaa" + e);
        }
    }
    public boolean getSonando(){
        return sonando;
    }

    public void comenzar(){
        sonido.start();
        sonando=true;
    }

    public void loop(){
        sonido.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void detener(){
        sonido.stop();
        sonando=false;
    }
}