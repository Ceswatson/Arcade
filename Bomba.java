import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;

import java.awt.image.*;



public class Bomba extends ObjetoGrafico {
    BufferedImage imagen=null;
    int timer; 
	private Rectangle2D posicion  = new Rectangle2D.Double();
    
    public Bomba(int x,  int y){
        super("Recursos/Imagenes/Bomba.png");
        setPosition(x, y);
    }

    public void setImagen(BufferedImage img){
        this.imagen=img;
    }
    public void draw(Graphics2D g){
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }
}