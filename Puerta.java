import java.awt.image.*;
public class Puerta extends ObjetoGrafico {
    Boolean puertaOn=false;
    
    public Puerta(int x,int y) {
        super ("Recursos/Imagenes/Puerta.png");
        setPosition(x, y);
    }
    public Puerta() {
        super ("Recursos/Imagenes/Puerta.png");
    }

    public void setPuertaON(boolean bool){
        puertaOn = bool;
    }
    public boolean getPuertaON(){
        return puertaOn;
    }
    public void siguienteNivel(Bomberman bomberman){
        this.setPuertaON(false);
        bomberman.addPuntos(200);
        bomberman.nextLevel();
        bomberman.gameStartup();
    }
}