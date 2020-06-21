import java.awt.*;
import java.awt.image.*;

public class BonusVelocidad extends Bonus {
    public BonusVelocidad(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Speed.png");
        setPosition(x, y);
    }
    public BonusVelocidad() {
        super("Recursos/Imagenes/Bonus/Speed.png");
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

    public void darBonus(Bomberman bomberman,Heroe hero){
        hero.setVelocidad();
        bomberman.addPuntos(puntos);
    }
}