
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
    private Vector<ObjetoGrafico> Objetos;
    private Vector<Fantasma> vecGhost;
    private Vector<Pared> vecParedes;
    private Vector<Pared> vecBloquesDisponibles;
    private Vector<Bonus> vecBonus;
    private int CANT_LADRILLOS = 90;
    private int bloque = 32;
    private Vector<Bomba> vecBombas;
    private Vector<Bomba> vecFlama;
    private int CANT_BOMBAS = 1;
    private int CANT_FANTASMAS = 10;
    private int CANT_VIDAS = 3;
    private Puerta door;
    private int EXPLOSION = 1; //modificar con el bonus 
    //private Vector<Bonus> vecBonus;
    ////////////Movimiento////////////////
    String restriccion = "libre";
    //////////////////////////////////////
    private Camara camara; 
    // variables de configuraciones 
    private Sonido reproducir; 
    
    private int PUNTAJE=0;
    //////////Tiempo//////////
    Date dInit = new Date();
    Date dAhora;
    SimpleDateFormat ft = new SimpleDateFormat ("mm:ss");

    private boolean retardo = false;
    ////////////////////////
    public Bomberman() {
        super("Bomberman", 640, 480);
        System.out.println(appProperties.stringPropertyNames());
        setearPropiedades(); 
        //System.out.println(appProperties.stringPropertyNames()); 

    }

    public void gameStartup() {
          
        camara = new Camara(0,0); //0,0
        camara.setRegionVisible(640, 448); //Ventana 640/480  448

        gris = new Fondo("Recursos/Imagenes/FondoGris.png");
        gris.setPosition(0,0);

        background = new Fondo("Recursos/Imagenes/Fondo.png");
        background.setPosition(0, 96);

        hero = new Heroe();
        hero.setPosition(33,97);

        vecBloquesDisponibles = new Vector<Pared>();
        BloquesDisponibles();

        vecParedes = new Vector<Pared>();
        iniciarBloquesPiedra();
        iniciarBloquesLadrillo();

        vecGhost = new Vector<Fantasma>();
        iniciarFantasmas();
        vecBombas = new Vector<Bomba>();
        vecFlama = new Vector<Bomba>();

        vecBonus = new Vector<Bonus>();
    }
    public void gameUpdate(final double delta) {
        final Keyboard keyboard = this.getKeyboard();
       
        //Movimiento// si se puede sacar a un funcion mejor, sino Meh.
        movimientoHeroe(delta,keyboard);
        if (hero.getX()<658){ // para que no te siga hasta el infinito 
            camara.seguirPersonaje(hero);
        }

        if (!colision()){
            restriccion = "libre";
        }


        if (retardo == false && (keyboard.isKeyPressed(KeyEvent.VK_SPACE))){
        
            soltarBomba();
            
        }
        retraso();

        explotarBomba();
        moverFantasmas(delta);
        redireccionarFantasmas(delta);

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

    public void retraso(){    
        if(vecBombas.isEmpty()){
            retardo = false;
        }else{
            if(vecBombas.elementAt(vecBombas.size()-1).getTimer()>=1){
                retardo = false;
            }else{
                retardo = true;
            }
        }   
    }

    public void gameDraw(final Graphics2D g) {
        //Mostrar menu
        

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gris.display(g);
        background.display(g);
        g.setBackground(Color.GRAY); // No anda no se que onda

        g.translate(camara.getX(),camara.getY());
            hero.display(g);  
            for(int i=0;i<vecParedes.size();i++){ // Dibujo TODAS las paredes 
                vecParedes.elementAt(i).display(g);
            }
            for(int i=0;i<vecGhost.size();i++){ // Dibujo TODAS las paredes 
                vecGhost.elementAt(i).display(g);
            }
            for(int i=0;i<vecBombas.size();i++){ //dibujo las bombas
                vecBombas.elementAt(i).display(g);
            }
            for(int i=0;i<vecFlama.size();i++){ //dibujo las bombas
                vecFlama.elementAt(i).display(g);
            }
            
        g.translate(-camara.getX(),-camara.getY()); 
        
        g.setColor(Color.BLACK);
        g.drawString("BOMBERMAN NES // By: VITALE & WATSON", 3, 490);
        dAhora= new Date( );
    	long dateDiff = dAhora.getTime() - dInit.getTime();
    	long diffSeconds = dateDiff / 1000 % 60;
        long diffMinutes = dateDiff / (60 * 1000) % 60;
        g.drawString("Tiempo de Juego: "+diffMinutes+":"+diffSeconds,3,40);
        g.drawString("Tecla ESC = Fin del Juego ",500,40);
        g.drawString("Puntaje: "+PUNTAJE,300,40);
    	g.setColor(Color.white);
    	g.drawString("Tiempo de Juego: "+diffMinutes+":"+diffSeconds,4,41);
        g.drawString("Tecla ESC = Fin del Juego ",501,41);
        g.drawString("Puntaje: "+PUNTAJE,301,41);

        System.out.println(vecBombas.size());

    }   
    public void gameShutdown() {
        
    }
    public void muerte(){
        CANT_VIDAS--;
        if(CANT_VIDAS>0){
            //reset
        }
        if(CANT_VIDAS==0){
            //GameOver
        }
    }

    public int contadorColision(){
        int cont=0; 

        for(int i=0;i<vecParedes.size();i++){
            if (hero.getPosicion().intersects(vecParedes.elementAt(i).getPosicion())){
                cont ++;
            }
        }
        for(int i=0;i<vecBombas.size();i++){
            if (hero.getPosicion().intersects(vecBombas.elementAt(i).getPosicion())){
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
        /*
        for(int i=0;i<vecBombas.size();i++){ //bomba como obstaculo 
            if (hero.getPosicion().intersects(vecBombas.elementAt(i).getPosicion())){
                //result = true; atravisa las bombas
            }
        }
        */

        for(int i=0;i<vecGhost.size();i++){
            if (hero.getPosicion().intersects(vecGhost.elementAt(i).getPosicion())){
                muerte();
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
        // Pared, *Fantasma*, bomba
    }

    public void moverFantasmas(double delta){
        
        for(int i=0;i<vecGhost.size();i++){
            vecGhost.elementAt(i).update(delta);
        }
    }
    public void redireccionarFantasmas(double delta){
        for(int i=0;i<vecGhost.size();i++){
            for(int j=0;j<vecParedes.size();j++){
                if (vecGhost.elementAt(i).getPosicion().intersects(vecParedes.elementAt(j).getPosicion())){
                    vecGhost.elementAt(i).direccionContraria(delta);
                }
            }
            for(int j=0;j<vecBombas.size();j++){
                if (vecGhost.elementAt(i).getPosicion().intersects(vecBombas.elementAt(j).getPosicion())){
                    vecGhost.elementAt(i).direccionContraria(delta);
                }
            }
        }
    }


    public void BloquesDisponibles(){
        int bloque = 32;
        for (int i=32; i<30*bloque; i+=bloque){ //32
            for(int j=96; j<(10*bloque)+128; j+= (bloque*2)){
                vecBloquesDisponibles.addElement(new ParedLadrillo(i,j));
            }
        }
        for (int i=32; i<30*bloque; i+= (bloque*2)){ //32
            for(int j=128; j<(10*bloque)+128; j+= (bloque*2)){
                vecBloquesDisponibles.addElement(new ParedLadrillo(i,j));
            }
        }
    }

    public void iniciarBloquesLadrillo(){
        int x=0;
        int y=0;
        int cant=0;
        int randomNum;
        int cantBloques;
       
        while(cant<CANT_LADRILLOS){
            cantBloques = vecBloquesDisponibles.size();
            randomNum = ThreadLocalRandom.current().nextInt(0, cantBloques);
            x=(int)vecBloquesDisponibles.elementAt(randomNum).getX();
            y=(int)vecBloquesDisponibles.elementAt(randomNum).getY();
            if(!(x==32&&y==96) && !(x==32&&y==128) && !(x==64&&y==96)){ ///Saco la L inicial   
                vecBloquesDisponibles.remove(randomNum); //Saco el bloqueDisponible
                ////////////Agrego un posible bonus al ladrillo///////////
                vecParedes.addElement(new ParedLadrillo(x,y));  
                cant++;  
            }
        }
    }

    public void iniciarBloquesPiedra (){
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

    public void iniciarFantasmas(){
        int x=0;
        int y=0;
        int cant=0;
        int randomNum;
        int cantBloques;

        while(cant<CANT_FANTASMAS){
            cantBloques = vecBloquesDisponibles.size();
            randomNum = ThreadLocalRandom.current().nextInt(0, cantBloques);
            x=(int)vecBloquesDisponibles.elementAt(randomNum).getX();
            y=(int)vecBloquesDisponibles.elementAt(randomNum).getY();
            if(!(x==32&&y==96) && !(x==32&&y==128) && !(x==64&&y==96)){ ///Saco la L inicial   
                vecBloquesDisponibles.remove(randomNum); //Saco el bloqueDisponible
                vecGhost.addElement(new Fantasma(x,y));  
                cant++;  
            }
        }
    }

    public void soltarBomba(){
        
        if (CANT_BOMBAS>=1){
            int x=0,y=0;
            final int[] arr={0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 
                320, 352, 384, 416, 448, 480, 512, 544, 576, 608, 
                640, 672, 704, 736, 768, 800, 832, 864, 896, 928, 
                960, 992};

            int i=0;
            while (arr[i]<(int)hero.getX()+15){
                x=arr[i];
                i++;
            }
            int j=0;
            while (arr[j]<(int)hero.getY()+15){
                y=arr[j];
                j++;
            }
              
            vecBombas.addElement(new Bomba(x,y));
            CANT_BOMBAS--;
        }
    }
    public void explotarBomba(){
        int x,y;
        boolean piedraArriba=false;
        int pasos=0;
        Vector <Bomba> vecArriba = new Vector<Bomba>();
        Vector <Bomba> vecAbajo = new Vector<Bomba>(); 
        Vector <Bomba> vecDerecha = new Vector<Bomba>();
        Vector <Bomba> vecIzquierda = new Vector<Bomba>();
        Objetos= new Vector<ObjetoGrafico>(); 
        int distancia = 32;
        String cadena = "medio";
        boolean colision = false;

        if(!vecBombas.isEmpty()){
            for(int i=0;i<vecBombas.size();i++){
                if(vecBombas.elementAt(i).getTimer()==3){ //Tiempo de explotar

                    x=(int)vecBombas.elementAt(i).getX();
                    y=(int)vecBombas.elementAt(i).getY();
                    vecBombas.elementAt(i).setFlama("medio", 1);
                    vecFlama.addElement(vecBombas.elementAt(i));

                    vecDerecha.addElement(new Bomba(x+32,y));
                       
                    for(int j=0;colision==false && j<vecParedes.size();j++){
                        if (vecDerecha.elementAt(0).getPosicion().intersects(vecParedes.elementAt(i).getPosicion())){
                            colision = true;
                        } 
                    }
                    if(colision==false){
                        vecDerecha.elementAt(0).setFlama("derecha", 1);
                        vecFlama.addElement(vecDerecha.elementAt(0));
                    }

                    /*
                    while(pasos<EXPLOSION){
                        
                        pasos++;
                        distancia += 32;
                    }
                    */
                    //Ahora tengo q ver contra que choca, si choca paredes frenar
                    // vector.size pasarlo como int a la flama 

                    switch(EXPLOSION){
                        case 1:
                        // ver para arriba 
                       
                        

                        // ver para abajo



                        break;


                    }
                                    
                    CANT_BOMBAS++;
                }
                if(vecBombas.elementAt(i).getTimer()>=4){ // Fin de la explosion

                    vecBombas.remove(i); //saco la bomba, pero me quedo con la flama del centro
                    vecFlama.clear();
                    vecArriba.clear();
                    vecAbajo.clear();
                    vecArriba.clear();
                    vecIzquierda.clear();
                }
            }
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