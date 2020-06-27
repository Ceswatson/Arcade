import java.awt.image.*;

public class BonusBomba extends Bonus {

    public BonusBomba(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Bombs.png");
        setPosition(x, y);
    }
    public BonusBomba() {
        super("Recursos/Imagenes/Bonus/Bombs.png");
    }

    public void setImagen(final BufferedImage img){ 
        this.imagen=img; 
    } 
    public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}
    public void darBonus(Bomberman bomberman){
        bomberman.addBombas();
        bomberman.addPuntos(puntos);
    }
}