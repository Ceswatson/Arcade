import java.awt.*;
import java.awt.geom.Rectangle2D;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

public class Bomba extends ObjetoGrafico {
    long timer;
    private Rectangle2D posicion = new Rectangle2D.Double();

    boolean explotando = false;
    boolean explotarAhora = false;
    Date dInit;
    Date dAhora;
    long addTres=0;
    BufferedImage bombaGrande = null;
    BufferedImage bombaMedia = null;
    BufferedImage bombaFlaca = null;
    boolean grande = true;
    boolean flaca = false;

    public Bomba(int x, int y) {
        super("Recursos/Imagenes/Bomba/Bomba1.png");
        setPosition(x, y);
        dInit = new Date();
    }

    public Bomba(int x, int y,String cadena) {
        super("Recursos/Imagenes/Bomba/Bomba1.png");
        setPosition(x, y);
        dInit = new Date();
        setFlama(cadena);
    }
    
    public void setExplotarAhora(){
        explotarAhora = true;
    }
    public boolean getExplotarAhora(){
        return explotarAhora;
    }

    public void setTimer(){
        dAhora = new Date();
        this.dInit = dAhora;
        addTres=3;
    }
    
    public long getTimer(){
        return this.timer;
    }
    
    public void updateTimer(){
        dAhora= new Date( );
        long dateDiff = dAhora.getTime() - dInit.getTime();
        this.timer = (dateDiff / 1000 % 60)+addTres;
    }

    public void setExplotando(){
        this.explotando = true;
    }
    public boolean getExplotando(){
        return explotando;
    }
    public void setFlama(String direccion){
        switch(direccion){
            case "medio":
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
    public void draw(Graphics2D g){
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }
}