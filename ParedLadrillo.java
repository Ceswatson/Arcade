import java.awt.*; 
import java.awt.image.*; 

public class ParedLadrillo extends Pared{ 
 
    public ParedLadrillo(int x,  int y) {
        super("Recursos/Imagenes/Ladrillo.png");
        setPosition(x, y);
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