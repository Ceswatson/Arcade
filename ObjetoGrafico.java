import java.awt.*;
import java.awt.geom.*;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class ObjetoGrafico {
	protected BufferedImage imagen = null;
	int puntos=0;
	double positionX = 0;
	double positionY = 0;
	
    public ObjetoGrafico(String filename) {
		try{
			imagen=ImageIO.read(new File(filename));
		}catch(IOException e){
			System.out.print("error dibujar objeto");
		}
    }

	public int getWidth(){
		return imagen.getWidth();
	}
	public int getHeight(){

		return imagen.getHeight();
	}

	public void setImagen(BufferedImage img){
        this.imagen=img;
    }

	public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

   	public void display(Graphics2D g2) {
		g2.drawImage(this.imagen,(int) this.positionX,(int) this.positionY,null);
   	}

	public double getX(){
		return positionX;
	}

	public double getY(){
		return positionY;
	}
	
	//Para las colisiones --- se pide un cuadrado en la posicion donde se cuentra el objeto
	public Rectangle2D getPosicion(){
        return new Rectangle2D.Double(positionX, positionY,30,30);
	}
}