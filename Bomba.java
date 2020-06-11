import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

public class Bomba extends ObjetoGrafico {
    long timer;
    private Rectangle2D posicion = new Rectangle2D.Double();

    Date dInit;
    Date dAhora;

    public Bomba(int x, int y) {
        super("Recursos/Imagenes/Bomba.png");
        setPosition(x, y);
        dInit = new Date();
    }

    public long getTimer(){
        dAhora= new Date( );
    	long dateDiff = dAhora.getTime() - dInit.getTime();
        long timer = dateDiff / 1000 % 60;
        return timer;
    }
    public void setImagen(BufferedImage img){
        this.imagen=img;
    }
    public void draw(Graphics2D g){
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }
}