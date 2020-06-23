
import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;

public class Fantasma extends ObjetoGrafico implements ObjetoMovible{
    int direccion=0;
    double VELOCIDAD = 20;
    boolean volviendo=false;
    int puntos=100;
    public Fantasma(String filename) {
        super(filename);
        direccion = ThreadLocalRandom.current().nextInt(1, 4);
    }

    public void setX(final double x) {
        positionX = x;
    }

    public void setY(final double y) {
        positionY = y;
    }
    public void darPuntos(Bomberman bomberman){
    }
    public double getDesplazamiento() {
        return VELOCIDAD;
    }

    public int getDireccion() {
        return this.direccion;
    }
    public void update(double delta) {
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
}
