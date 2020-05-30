import java.awt.*;
import java.awt.geom.*;

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

	private boolean onGround = false;
	private boolean saltando = false;

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
	
	public Heroe(String filename){ //Contructor con la imagen
			super(filename);
			w2=this.getWidth()/2;
			h2=this.getHeight()/2;
	}
	void jump() {
		if (onGround) {
			    velocityY = -12.0;
        		onGround = false;			 
		}		 
	}
	void jumpEnd() {
		if(velocityY < -6.0){
			velocityY = -6.0;
		} 
	}
	void quieto() {
		estadoActual = ESTADO_QUIETO;
		//acceleration.mult(0);
	}
	void left() {
		velocityX = -4.0;
		direccionActual = DIRECCION_IZQUIERDA;

		estadoActual = ESTADO_CAMINANDO;	 
	}

	void right() {

		velocityX = 4.0;

		direccionActual = DIRECCION_DERECHA;
		estadoActual = ESTADO_CAMINANDO;
		 
	}

	public void update(double delta) {
		velocityY += gravity;
    	positionY += velocityY;
    	positionX += velocityX;
		
		angulo=(angulo % 360);
	 
		Mundo m = Mundo.getInstance();

		/* Rebota contra los margenes X del mundo */
		if ((positionX+ (this.getWidth())) > m.getWidth()) {
			//positionX = m.getWidth() - (this.getWidth());
			velocityX *= -1 ;
		}
		/* Rebota contra la X=0 del mundo */
		if ((positionX) < 0) {
			velocityX *= -1  ;
			positionX = 0;
		}

	    if(positionY > POSICION_Y_PISO){
	        positionY = POSICION_Y_PISO;
			velocityY = 0.0;
			angulo=0;
	        onGround = true;
	    }
	    if ( velocityY != 0.0){
			//mientras este saltando
			//angulo+=0.05;
			angulo+=15;
		   // System.out.print(".");
	    } 
	}
	//private BufferedImage getImagenRotada()
	public void display(Graphics2D g2) {
		
		AffineTransform ogAT=g2.getTransform();

		g2.rotate(Math.toRadians(this.angulo),(int) this.positionX +w2 ,(int) this.positionY+h2 );
	 	 
		g2.drawImage(imagen,(int) this.positionX,(int) this.positionY,null);

		g2.setTransform(ogAT);
   }
}