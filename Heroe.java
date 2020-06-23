import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

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
    public void setImagen(String direccion){
        switch(direccion){
            case "Arriba":
            try {
                this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/MedioBomba.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
            case "arriba":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaArriba.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "abajo":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaAbajo.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "izquierda":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaIzquierda.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "derecha":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaDerecha.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }     
            break;
            case "horizontal":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/MedioHorizontal.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }     
            break;
            case "vertical":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/MedioVertical.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }     
            break;     
        }
    }
}