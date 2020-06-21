
import java.awt.*;
import java.awt.image.*;

public class BonusFlama extends Bonus {
    public BonusFlama(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Flames.png");
        setPosition(x, y);
    }
    public BonusFlama() {
        super("Recursos/Imagenes/Bonus/Flames.png");
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
        bomberman.setFlama();
        bomberman.addPuntos(puntos);

    }

}