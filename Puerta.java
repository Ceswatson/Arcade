
import java.awt.*;
import java.awt.image.*;
public class Puerta extends ObjetoGrafico {
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

    public void draw(final Graphics2D g){
        g.drawImage(imagen,(int)getX(),(int)getY(),null);
    }
}