import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;

import java.awt.image.*;

public class Bomba extends ObjetoGrafico {
    BufferedImage imagen=null;
	private Rectangle2D posicion  = new Rectangle2D.Double();
    
    public Bomba(){

    }

    public void setPosicion(double x, double y){
        posicion.setLocation(x, y);
    }
    public void setImagen(BufferedImage img){
        this.imagen=img;
    }
    public void draw(Graphics2D g){
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }
}