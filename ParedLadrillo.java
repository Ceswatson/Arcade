import java.awt.*; 


import java.awt.image.*; 

public class ParedLadrillo extends Pared { 
 
    public ParedPiedra(int x,  int y) {
        super("Recursos/Imagenes/Ladrillo.png");
        setPosition(x, y);
    }
 
    public void setImagen(BufferedImage img){ 
        this.imagen=img; 
    } 

    public void draw(final Graphics2D g){
        g.drawImage(imagen,(int)getX(),(int)getY(),null);
    }
}