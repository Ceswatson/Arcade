import com.entropyinteractive.*;

//import javafx.geometry.Rectangle2D;
import java.awt.geom.Rectangle2D;


import java.awt.*;
import java.awt.event.*; //eventos

import java.awt.image.*; //imagenes
import java.io.File;

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

//Menu del Juego, donde se puede seleccionar jugar
public class Bomberman extends JGame {
    private int x = 1024;
    private int y = 768;

    
    //private BufferedImage fondo=null;
    //private BufferedImage logo=null; 

    private Heroe hero;
    private Fondo background;
    private Vector<Fantasma> vecGhost;
    //private Vector<Pared> vecPared;
    private Puerta door;



    final double HEROE_DESPLAZAMIENTO=150.0;

    public Bomberman() {
        super("Bomberman", 1024, 768);

        System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        
        background = new Fondo("Recursos/Imagenes/Fondo.png");
        try{

        hero = new Heroe("Recursos/Imagenes/Heroe.jpg");
        hero.setImagen(ImageIO.read(getClass().getResource("Recursos/Imagenes/heroe.jpg")));
        hero.setPosition(getWidth() / 2,getHeight() / 2 ); // setear poscicion de inicio
        
        //vecGhost = new Vector<Fantasma>();
        //vecPared = new Vector<Pared>();
    

        }
        catch(Exception e){}
    }
    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
        // Procesar teclas de direccion
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
            hero.setY( hero.getY() - HEROE_DESPLAZAMIENTO * delta);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
            hero.setY( hero.getY() + HEROE_DESPLAZAMIENTO * delta);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
            hero.setX( hero.getX() - HEROE_DESPLAZAMIENTO * delta);
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
            hero.setX( hero.getX() + HEROE_DESPLAZAMIENTO * delta);
        }
        // Esc fin del juego
        LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
        for (KeyEvent event: keyEvents) {
            if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                stop(); //bucleJuego.jar
            }
        }
    }
    
    public void gameDraw(Graphics2D g) {
    
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        background.display(g);
        hero.display(g);
    }   
    public void gameShutdown() {}

  
    /*
    public boolean colision(){
        //Colision heroe rectangulo de juego
        //Colision heroe Pared
        //Colision heroe bomba (sin explotar)
        //Colision heroe bomba (explosion)
        //Colision heroe Fantasma
        //Colision heroe bonus
        //Colision heroe puerta

        //explosion de bombas
        //pieda corta explosion
        //ladrillo rompe
        //fantasma, mata
        //puerta, rompe genera fantamas
        //bonus tambien

        //fantasmas
        // rectangulo de juego, Pared, *Fantasma*, bomba
    }
    */
}