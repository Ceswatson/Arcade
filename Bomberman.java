import com.entropyinteractive.*;
 
import java.awt.*;
import java.awt.event.*; //eventos

import java.awt.image.*;  //imagenes
import javax.imageio.*; //imagenes

import java.awt.Graphics2D;

import java.awt.geom.*; //Point2d
import java.util.LinkedList;

import java.util.*;
import java.text.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Bomberman extends JGame {
    //Scene
    //sonido
    //reloj?
    //fondo
    //camara
    Heroe heroe;
    Mundo mundo;
    //Date // para el tiempo de juego
    Fondo img_fondo;

    public Bomberman() {
        super("Bomberman", 800, 600);
        System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        try{
            img_fondo = new Fondo("Recursos/Imagenes/Fondo.png");
            heroe = new Heroe("recursos/imagenes/geometry-01.png");
        }
        catch(Exception e){}

    }
    public void gameUpdate(double delta) {}
    public void gameDraw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Mundo mundo = Mundo.getInstance();
        
        img_fondo.display(g);

    }
    public void gameShutdown() {}

}