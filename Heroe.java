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

	/*
	final int DIRECCION_DERECHA = 0;
	final int DIRECCION_IZQUIERDA = 1;

	final int ESTADO_QUIETO = -1;
	final int ESTADO_CAMINANDO = 0;
	final int ESTADO_ARROJANDO_GRANADA = 4;
	final int ESTADO_MURIENDO = 5;
	int direccionActual;
	int estadoActual;

	double velocityX = 4.0;
	double velocityY = 0.0;
	double gravity = 0.5;
	double angulo=0.0;
	
	final int POSICION_Y_PISO=360;
	private int w2,h2;
	*/

	BufferedImage imagen=null;
	private Rectangle2D posicion  = new Rectangle2D.Double();

	public Heroe(){ 

	}

	public void setImagen(BufferedImage img){
        this.imagen=img;
    }
	public void setPosicion(double x, double y){
        posicion.setLocation(x, y);
    }
    public void setX(double x){
        posicion.x=x;
    }
    public void setY(double y){
        posicion.y=y;
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