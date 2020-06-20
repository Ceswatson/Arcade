
import java.awt.*;
import java.awt.image.*;

public class BonusVida extends Bonus{

    public BonusVida(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Vida.png");
        setPosition(x, y);
    }
    public BonusVida() {
        super("Recursos/Imagenes/Bonus/Vida.png");
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
        bomberman.setVidas();
    }
}