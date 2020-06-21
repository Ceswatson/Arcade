import java.awt.*;
import java.awt.image.*;

public class BonusDetonador extends Bonus {
    public BonusDetonador(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Detonator.png");
        setPosition(x, y);
    }
    public BonusDetonador() {
        super("Recursos/Imagenes/Bonus/Detonator.png");
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
        bomberman.setDetonador();
        bomberman.addPuntos(puntos);

    }
}