import java.awt.*;
import java.awt.image.*;

public class Heroe extends ObjetoGrafico implements ObjetoMovible {

    private BufferedImage imagen = null;
    private double HEROE_DESPLAZAMIENTO=80.0;

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