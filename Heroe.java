import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.*; //nuevo para sonido

/*public class Heroe implements ObjetoMovible {
    public void update(double delta){};
	public double getX(){};
	public double getY(){};
}*/

public class Heroe extends ObjetoGrafico implements ObjetoMovible {


	BufferedImage imagen=null;
	private Rectangle2D posicion  = new Rectangle2D.Double();

	public Heroe(String filename){ 
        super (filename);
	}

	public void setImagen(BufferedImage img){
        this.imagen=img;
    }

    public void setX(double x){
        positionX=x;
    }
    public void setY(double y){
        positionY=y;
    }
    public double getX(){
        return posicion.getX(); 
    }
    public double getY(){
        return posicion.getY(); 
    }

	public void update(double delta) {
	
	}
	public void draw(Graphics2D g){
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }
}