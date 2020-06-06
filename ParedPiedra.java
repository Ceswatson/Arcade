import java.awt.*; 
import java.awt.geom.*; 
import java.awt.geom.Rectangle2D; 
 
import java.awt.image.*; 
import javax.imageio.*; 
import java.io.*; 
import java.net.*; 
 
public class ParedPiedra extends Pared{ 
    
     
    public ParedPiedra(int x,  int y) {
        super("Recursos/Imagenes/Piedra.png");
        setPosition(x, y);
    }

    public void setImagen(final BufferedImage img) {
        this.imagen=img; 
    } 
    
    public void draw(final Graphics2D g){
        g.drawImage(imagen,(int)getX(),(int)getY(),null);
    }
 
}