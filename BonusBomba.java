
import java.awt.*;
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

    public void draw(final Graphics2D g){
        g.drawImage(imagen,(int)getX(),(int)getY(),null);
    }

    public void darBonus(Bomberman bomberman){
        bomberman.setBombas();
    }

}