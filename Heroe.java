import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.*; //nuevo para sonido

public class Heroe extends ObjetoGrafico implements ObjetoMovible {

    private BufferedImage imagen = null;
    private double HEROE_DESPLAZAMIENTO=80.0;
    //private final Rectangle2D posicion = new Rectangle2D.Double();

    public Heroe() {
        super("Recursos/Imagenes/Heroe.png");
	}

    public void setX(final double x){
        positionX=x;
    }
    public void setY(final double y){
        positionY=y;
    }

    public double getDesplazamiento(){
        return HEROE_DESPLAZAMIENTO;
    }
    public void setVelocidad(){
        HEROE_DESPLAZAMIENTO+=20;
    }
    public void ResetVelocidad(){
        HEROE_DESPLAZAMIENTO=80;
    }

	public void update(final double delta) {
	
	}
	public void draw(final Graphics2D g){
        g.drawImage(imagen,(int)getX(),(int)getY(),null);
    }
}