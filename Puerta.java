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

    public void setImagen(final BufferedImage img){ 
        this.imagen=img; 
    } 
    public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
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