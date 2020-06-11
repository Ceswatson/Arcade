import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;

public class Fantasma extends ObjetoGrafico implements ObjetoMovible {
    double VELOCIDAD = 20;
    int direccion=0;
    boolean volviendo=false;
    public Fantasma(final int x, final int y) {
        super("Recursos/Imagenes/Fantasma.png");
        setPosition(x+1, y+1);
        direccion = ThreadLocalRandom.current().nextInt(1, 4);
    }

    public void setX(final double x) {
        positionX = x;
    }

    public void setY(final double y) {
        positionY = y;
    }

    public double getDesplazamiento() {
        return VELOCIDAD;
    }

    public int getDireccion() {
        return this.direccion;
    }

    public void update(final double delta) { //Inicializa los moviminetos
        switch (direccion) {
            case 1:
                this.setY(this.getY() - VELOCIDAD * delta); // arriba
                break;
            case 2:
                this.setY(this.getY() + VELOCIDAD * delta); // abajo
                break;
            case 3:
                this.setX(this.getX() - VELOCIDAD * delta); // izq
                break;
            case 4:
                this.setX(this.getX() + VELOCIDAD * delta); // der
                break;
        }
    }
    public void direccionContraria(final double delta) {
        
        switch (direccion) {
            case 1: // arriba
                direccion = 2;
                break;
            case 2:// abajo
                direccion = 1;
                break;
            case 3:// izq
                direccion = 4;
                break;
            case 4:// der
                direccion = 3;
                break;
        }

    }

    public void draw(final Graphics2D g) {
        g.drawImage(imagen, (int) getX(), (int) getY(), null);
    }

}
