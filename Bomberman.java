import com.entropyinteractive.*;

//import javafx.geometry.Rectangle2D;
import java.awt.geom.Rectangle2D;

import java.util.concurrent.ThreadLocalRandom;
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
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileInputStream; 


//Menu del Juego, donde se puede seleccionar jugar
public class Bomberman extends JGame {

    private Heroe hero;
    private Fondo background;
    private Fondo gris;
    private Vector<Fantasma> vecGhost;
    private Vector<Pared> vecParedes;
    private Vector<Pared> bloquesOcupados;
    private int CANT_LADRILLOS = 50;
    private Vector<Bomba> vecBombas;
    private int cantBombas = 1;
    private Puerta door;
    ////////////Movimiento////////////////
    String restriccion = "libre";
    //////////////////////////////////////
    private Camara camara; 
    // variables de configuraciones 
    private Sonido reproducir; 
    
    private int puntaje;


    public Bomberman() {
        super("Bomberman", 640, 480);
        System.out.println(appProperties.stringPropertyNames());
        setearPropiedades(); 
        //System.out.println(appProperties.stringPropertyNames()); 
         
    }

    public void gameStartup() {
          
        camara = new Camara(0,0);
        camara.setRegionVisible(640, 448); //Ventana 640/480  448

        gris = new Fondo("Recursos/Imagenes/FondoGris.png");
        gris.setPosition(0,0);

        background = new Fondo("Recursos/Imagenes/Fondo.png");
        background.setPosition(0, 96);

        hero = new Heroe();
        hero.setPosition(33,97);

        door = new Puerta();
        
        vecParedes = new Vector<Pared>();
        bloquesOcupados = new Vector<Pared>();

        iniciarBloquesPiedra();
        iniciarBloquesLadrillo();

        vecGhost = new Vector<Fantasma>();
        //funcion meter fantasma
        vecBombas = new Vector<Bomba>();

        
    }
    public void gameUpdate(final double delta) {
        final Keyboard keyboard = this.getKeyboard();
       
        //Movimiento// si se puede sacar a un funcion mejor, sino Meh.
        movimientoHeroe(delta,keyboard);
        camara.seguirPersonaje(hero);

        if (!colision()){
            restriccion = "libre";
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_SPACE)){
            soltarBomba();
        }

        // Esc fin del juego
        final LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
        for (final KeyEvent event: keyEvents) {
            if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                stop(); //bucleJuego.jar
                 //paro la musica aca momentaneamente 
                 reproducir.detener(); 
            }
        }
    }
    
    public void gameDraw(final Graphics2D g) {
        //Mostrar menu

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gris.display(g);
        background.display(g);
        g.setBackground(Color.GRAY); // No anda no se que onda
        g.drawString("HOLAAAA", 64, 64);

        g.translate(camara.getX(),camara.getY());
            hero.display(g);    
            //For para las bombas
            for(int i=0;i<vecParedes.size();i++){ // Dibujo TODAS las paredes 
                vecParedes.elementAt(i).display(g);
            }
            for(int i=0;i<vecBombas.size();i++){ //dibujo las bombas
                vecBombas.elementAt(i).display(g);
            }
        g.translate(-camara.getX(),-camara.getY()); 
    }   
    public void gameShutdown() {}
    //hero.getPosition().intersects(pared.getPosition())

    public int contadorColision(){
        int cont=0; 

        for(int i=0;i<vecParedes.size();i++){
            if (hero.getPosicion().intersects(vecParedes.elementAt(i).getPosicion())){
                cont ++;
            }
        }
        return cont; 
    }

    public boolean colision(){
        boolean result = false; 
        for(int i=0;i<vecParedes.size();i++){
            if (hero.getPosicion().intersects(vecParedes.elementAt(i).getPosicion())){
                result = true;
            }
        }

        for(int i=0;i<vecBombas.size();i++){
            if (hero.getPosicion().intersects(vecBombas.elementAt(i).getPosicion())){
           
            }
        }
        return result;

        //Colision heroe rectangulo de juego [Listo]
        //Colision heroe Pared [Listo]
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
 
    void iniciarBloquesLadrillo(){
        final int bloque = 32;
        double x=0;
        double y=0;
        int cant=0;
        int bloques;
        int randomNumX,randomNumY;
        boolean flag;
        //bloquesOcupados=vecParedes;
        /*
        bloquesOcupados.addElement(new ParedLadrillo(32,96)); //agregar la L del inicio
        bloquesOcupados.addElement(new ParedLadrillo(64,96)); //agregar la L del inicio
        bloquesOcupados.addElement(new ParedLadrillo(32,128)); //agregar la L del inicio
        */
////////////////////////////////////////////////////////////////////////////
        while(cant<CANT_LADRILLOS){
            //int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
            randomNumX = 32 + 32 * (ThreadLocalRandom.current().nextInt(0, 28 + 1));
            randomNumY = 96 + 32 * (ThreadLocalRandom.current().nextInt(0, 10 + 1));
            
            System.out.println("numeroX: " + randomNumX);
            System.out.println("NumeroY: " + randomNumY);
            flag = false;
            bloques = vecParedes.size();
            System.out.println("Bloques: " + bloques);
            for(int i=0; flag == false && i<bloques; i++){
            int i=0;
                x=vecParedes.elementAt(i).getX();
                y=vecParedes.elementAt(i).getY();
                System.out.println("X: " + x);
                System.out.println("Y: " + y);
                if(!(randomNumX == x)){
                    if(!(randomNumY == y)){
                        vecParedes.addElement(new ParedLadrillo(randomNumX,randomNumY));
                        flag = true;
                        cant++;
                        System.out.println("cant: " + cant);

                    }
                }
            }
        }
    }

    void iniciarBloquesPiedra (){ //Guardar las i*j en un vector jeje
        final int bloque = 32;
        for(int i=0;i<992;i+=bloque){ // x
            vecParedes.addElement(new ParedPiedra(i,64)); // Primer fila
            vecParedes.addElement(new ParedPiedra(i,448));// Ultima fila
        }
        for(int j=96;j<(11*bloque)+128;j+=bloque){ // y
            vecParedes.addElement(new ParedPiedra(0,j)); // Primer columna
            vecParedes.addElement(new ParedPiedra(1024-64,j)); // Ultima columna 
        }     
        for (int i=64; i<30*bloque; i+= (bloque*2)){ //32
            for(int j=128; j<(11*bloque)+128; j+= (bloque*2)){
                vecParedes.addElement(new ParedPiedra(i,j));
            }
        }
    }
    public void soltarBomba(){
        if (cantBombas>0){
            int x=0,y=0;
            final int[] arr={0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 
                320, 352, 384, 416, 448, 480, 512, 544, 576, 608, 
                640, 672, 704, 736, 768, 800, 832, 864, 896, 928, 
                960, 992};

            int i=0;
            while (arr[i]<(int)hero.getX()){
                x=arr[i];
                i++;
            }
            int j=0;
            while (arr[j]<(int)hero.getY()){
                y=arr[j];
                j++;
            }
            /*
            System.out.println("hero y:" + (int)hero.getY());
            System.out.println("y:"+ y);
            System.out.println("hero x:" + (int)hero.getX());
            System.out.println("x:"+ x);
            */
            vecBombas.addElement(new Bomba(x,y));
            cantBombas--;
        }
    }

    public void setearPropiedades(){ 
        final Properties propiedades=new Properties(); 
        try { 
            propiedades.load(new FileInputStream("jgame.properties")); 
            // musica 
            if (Boolean.parseBoolean(propiedades.getProperty("OriginalMusic"))){ 
                reproducir = new Sonido("Recursos/Sonidos/Musicas/.wav/03_Stage_Theme.wav"); 
                reproducir.comenzar(); 
                reproducir.loop(); 
            } 
             
        } catch (final Exception exception) { 
            System.out.println("ERROR AL CARGAR PROPERTIES"); 
        } 
    } 
 
    /// No la puedo pasar a Heroe.java porque este no es hijo de Jgame y no tiene los KeyEvent
    public void movimientoHeroe(final double delta, final Keyboard keyboard) { 
        switch(restriccion){
            case "libre": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                    if (colision()){
                        restriccion = "noArriba";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                    if (colision()){
                        restriccion = "noAbajo";
                    }               
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                    if (colision()){
                        restriccion = "noIzquierda";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                    if (colision()){
                        restriccion = "noDerecha";
                    }
                }
            break;
            case "noArriba": 
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noArriba_noIzquieda";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noArriba_noDerecha";
                    }
                }
            break;
            case "noAbajo": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noAbajo_noIzquierda";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noAbajo_noDerecha";
                    }
                }
            break;
            case "noIzquierda": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noIzquierda_noArriba";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noIzquierda_noAbajo";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                }
            break;
            case "noDerecha": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noDerecha_noArriba";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion                 
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                    if(contadorColision() > 2){
                        restriccion = "noDerecha_noAbajo";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                }              
            break;
            /////////////////////////////////////////////////////////////////////////////////
            case "noArriba_noIzquieda":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                    
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                }
            break; 
            case "noArriba_noDerecha":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                } 
            break;

            case "noAbajo_noIzquierda":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                }
            break;

            case "noAbajo_noDerecha":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                } 
            break;
            case "noIzquierda_noArriba":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                }
            break;
            case "noIzquierda_noAbajo":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                }
            break;
            case "noDerecha_noAbajo":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                }
            break;
            case "noDerecha_noArriba":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                }
            break;
        }
    }
}