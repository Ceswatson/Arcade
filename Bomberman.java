
import com.entropyinteractive.*;

//import javafx.scene.paint.Stop;

//import javafx.geometry.Rectangle2D;
import java.awt.geom.Rectangle2D;

import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.awt.event.*; //eventos

import java.awt.image.*; //imagenes
import java.io.File;

import javax.imageio.*; //imagenes
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Graphics2D;

import java.awt.geom.*; //Point2d
import java.util.LinkedList;

import java.util.*;
import java.text.*;

import java.awt.Color;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

//Menu del Juego, donde se puede seleccionar jugar
public class Bomberman extends JGame implements ActionListener {
    
    private Heroe hero;
    private Puerta puerta;
    private Fondo background;
    private Fondo gris;
    private Vector<ObjetoGrafico> Objetos;
    private Vector<Fantasma> vecGhost;
    private Vector<Pared> vecParedes;
    private Vector<Pared> vecBloquesDisponibles;
    private Vector<Bonus> vecBonus;
    private Vector<Bonus> vecBonusRandom;
    private int CANT_LADRILLOS = 90;
    private final int bloque = 32;
    private Vector<Bomba> vecBombas;
    private Vector<Bomba> vecFlama;
    private int CANT_BOMBAS = 1;
    private int CANT_FANTASMAS = 5;
    private int CANT_VIDAS = 3;
    private Puerta door;
    private int EXPLOSION = 1; //modificar con el bonus 
    ////////////Movimiento////////////////
    String restriccion = "libre";
    //////////////////////////////////////
    private Camara camara; 
    // variables de configuraciones 
    private Sonido reproducir;
    private boolean flag_sonido;
    private Sonido boom,soltarbomb,caminata;
    
    private int PUNTAJE=0;
    //////////Tiempo//////////
    Date dInit;
    Date dAhora;
    long TIMER = 200;
    long tiempoTranscurrido;
    ////////////////////////////
    private boolean retardo = false;
    ////////////////////////
    private boolean puertaON = false;

    //shoutdown
    JFrame gameover;
    JLabel label;
    JTextField textField;
    JButton boton;
    //////////////////////
    // ranking
    protected static String[] nombres=new String[10];
    protected static int[] puntos=new int[10];
    protected static int[] niveles=new int[10];
    protected static String[] fechas=new String[10];
    protected  int lineas=0;
    /////////////////////
    public Bomberman() {
        super("Bomberman", 640, 480);
        System.out.println(appProperties.stringPropertyNames());
        setearPropiedades(); 
        //System.out.println(appProperties.stringPropertyNames()); 
       
        dInit = new Date();
    }

    public long getTiempo(){
        dAhora= new Date();

    	long dateDiff = dAhora.getTime() - dInit.getTime();
        long tiempoTranscurrido = dateDiff / 1000 % 60;
        return (TIMER - tiempoTranscurrido);
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

        vecBonusRandom = new Vector<Bonus>();
        vecBonusRandom.addElement(new BonusVida());
        vecBonusRandom.addElement(new BonusBomba());
        vecBonusRandom.addElement(new BonusFlama());

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
        ///TIEMPO
        if(getTiempo()<0){
            muerte();
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
            
            for(int i=0;i<vecParedes.size();i++){ // Dibujo TODAS las paredes 
                vecParedes.elementAt(i).display(g);
            }
            for(int i=0;i<vecBombas.size();i++){ //dibujo las bombas
                vecBombas.elementAt(i).display(g);
            }
            for(int i=0;i<vecFlama.size();i++){ //dibujo las bombas
                vecFlama.elementAt(i).display(g);
            }
            for(int i=0;i<vecGhost.size();i++){ // Dibujo TODAS las paredes 
                vecGhost.elementAt(i).display(g);
            }
            for(int i=0;i<vecBonus.size();i++){ // Dibujo TODAS las paredes 
                vecBonus.elementAt(i).display(g);
            }
            hero.display(g);  
            if(puertaON){
                puerta.display(g);
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
        g.drawString("TIMER: " + getTiempo(),4,41);
        g.drawString("Tecla ESC = Fin del Juego ",501,41);
        g.drawString("Puntaje: "+PUNTAJE,301,41);

    }   
    public void gameShutdown() {

        stop(); //bucleJuego.jar
        gameover = new JFrame("Fin del juego");
        gameover.setLayout(null);
        gameover.setPreferredSize(new Dimension(400, 200));
        label = new JLabel("Nombre Jugador: ");
        textField = new JTextField();
        boton = new JButton("Aceptar");
        boton.addActionListener(this);
        label.setBounds(10, 60, 80, 15);
        textField.setBounds(95, 50, 250, 40);
        boton.setBounds(160, 100, 80, 30);
        gameover.add(label);
        gameover.add(textField);
        gameover.add(boton);
        gameover.setAlwaysOnTop(true);
        gameover.setResizable(false);
        gameover.setLocationRelativeTo(null);
        gameover.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameover.pack();
        gameover.setVisible(true);
        
        
        System.out.println("te quedaste sin vidas y termino el juego");
    }
  
    public void muerte(){
        CANT_VIDAS--;
        if(CANT_VIDAS>0){
            //reset
            gameStartup();
        }
        if(CANT_VIDAS==0){
            //GameOver
            gameShutdown();
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
        for(int i=0;i<vecBombas.size();i++){ //bomba como obstaculo 
            if(vecBombas.elementAt(i).getTimer()>1){
                if (hero.getPosicion().intersects(vecBombas.elementAt(i).getPosicion())){
                    result = true;
                }
            }
        }
    
        for(int i=0;i<vecGhost.size();i++){
            if (hero.getPosicion().intersects(vecGhost.elementAt(i).getPosicion())){
                muerte();
            }
        }
        return result;
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
            if(flag_sonido){
                soltarbomb = new Sonido("Recursos/Sonidos/Efectos/Soltar_Bomba.wav");
                soltarbomb.comenzar();
            }
        }
    }
    public void explotarBomba(){
        int x,y;
        int pasos=0;
        int distancia=32;
        Vector <Bomba> vecArriba = new Vector<Bomba>();
        Vector <Bomba> vecAbajo = new Vector<Bomba>(); 
        Vector <Bomba> vecDerecha = new Vector<Bomba>();
        Vector <Bomba> vecIzquierda = new Vector<Bomba>();
        if(!vecBombas.isEmpty()){
            for(int i=0;i<vecBombas.size();i++){ //recorro bombas en el campo
                
                if(!vecBombas.elementAt(i).getExplotando() && vecBombas.elementAt(i).getTimer()==3){ //Tiempo de explotar
                    vecBombas.elementAt(i).setExplotando(); //setea en true

                    x=(int)vecBombas.elementAt(i).getX();
                    y=(int)vecBombas.elementAt(i).getY();
                    vecBombas.elementAt(i).setFlama("medio");
                    vecFlama.addElement(vecBombas.elementAt(i));
                    
                    //CargoExplosiones segun el largo
                    if(EXPLOSION == 1){
                        vecArriba.addElement(new Bomba(x,y-distancia,"arriba"));
                        vecAbajo.addElement(new Bomba(x,y+distancia,"abajo"));
                        vecIzquierda.addElement(new Bomba(x-distancia,y,"izquierda"));
                        vecDerecha.addElement(new Bomba(x+distancia,y,"derecha"));           
                    }else{

                        do{ //Cargo chorizos de flama
                            vecArriba.addElement(new Bomba(x,y-distancia,"vertical"));
                            vecAbajo.addElement(new Bomba(x,y+distancia,"vertical"));
                            vecIzquierda.addElement(new Bomba(x-distancia,y,"horizontal"));
                            vecDerecha.addElement(new Bomba(x+distancia,y,"horizontal")); 

                            distancia += 32;
                            pasos++;
                        }while(pasos<EXPLOSION);
                        //Acomodo las puntas
                        vecArriba.lastElement().setFlama("arriba");
                        vecAbajo.lastElement().setFlama("abajo");
                        vecIzquierda.lastElement().setFlama("izquierda");
                        vecDerecha.lastElement().setFlama("derecha");
                    }

                    //Miro posibles colisiones con paredes y acomodo las flamas
                    vecArriba = cortarVector(vecArriba);
                    vecAbajo = cortarVector(vecAbajo);
                    vecIzquierda = cortarVector(vecIzquierda);
                    vecDerecha = cortarVector(vecDerecha);

                    //Agrego las flamas a vecFlama 
                    vecFlama.addAll(vecArriba);
                    vecFlama.addAll(vecAbajo);
                    vecFlama.addAll(vecIzquierda);
                    vecFlama.addAll(vecDerecha);

                    // colisiones de llamas con fantasmas
                    for(int h=0; h<vecFlama.size();h++){
                        for(int j=0;j< vecGhost.size();j++){
                            if(vecGhost.elementAt(j).getPosicion().intersects(vecFlama.elementAt(h).getPosicion())){
                                vecGhost.remove(j);
                                PUNTAJE += 100;
                            }
                        }
                    }
                }
                if(vecBombas.elementAt(i).getTimer()>=4){ // Fin de la explosion    
                    vecBombas.remove(i); //saco la bomba, pero me quedo con la flama del centro
                    CANT_BOMBAS++;
                    vecFlama.clear();
                    vecArriba.clear();
                    vecAbajo.clear();
                    vecArriba.clear();
                    vecIzquierda.clear();
                }
            }
        }
    }
    
    public Vector<Bomba> cortarVector(Vector<Bomba> vec){
        for(int j=0; j<vecParedes.size(); j++){ // j recorro paredes
            boolean flag = false;
            boolean freno = false;
            if(freno==false){
                for(int k=0; flag==false && k<vec.size();k++){ // k recorre vectores flamas
                    if (vec.elementAt(k).getPosicion().intersects(vecParedes.elementAt(j).getPosicion())){
                        flag=true;
                        freno=true;
                        if(vecParedes.elementAt(j).getClass().getName() == "ParedLadrillo"){ //choco pared ladrillo
                            soltarBonus((int)vecParedes.elementAt(j).getX(),(int)vecParedes.elementAt(j).getY());
                            
                            vecParedes.remove(j); //Sacamos la pared de ladrillo del campo
                            while(k<vec.size()){ // ACA SE VE LA ANIMACION DEL LADRILLO
                                vec.removeElementAt(k); 
                            } 
                        } 
                    } 
                } 
            } 
        } 
        return vec; 
    }
    
    //if(vecParedes.elementAt(j).getClass().getName() == "ParedLadrillo")

    public void soltarBonus(int x, int y){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10);
        int randomBonus = ThreadLocalRandom.current().nextInt(0, vecBonusRandom.size());
        String bonusName;
        boolean salio = false;
        if(!puertaON){
            if(randomNum<2){
                puerta = new Puerta(x,y);
                puertaON = true;
                salio = true;
            }
        }
        if(salio==false){
            System.out.println("Pruebo bonus!");
            if(randomNum<5){
                System.out.println("Entro bonus!");
                bonusName = vecBonusRandom.elementAt(randomBonus).getClass().getName();
                switch(bonusName){
                    case "BonusFlama" :
                        vecBonus.addElement(new BonusFlama(x,y));
                    break;
                    case "BonusBomba" :
                        vecBonus.addElement(new BonusBomba(x,y));
                    break;
                    case "BonusVida" :
                        vecBonus.addElement(new BonusVida(x,y));
                    break;
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
            if (Boolean.parseBoolean(propiedades.getProperty("Sound"))){ 
                flag_sonido = true; 
            }else  
                flag_sonido = false; 
             
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("Aceptar")){
            // por ahora se le carga el nivel 1
            if(textField.getText().isEmpty()==false){
                escribirRanking(textField.getText(), this.PUNTAJE, 1);
            }

        }
    }

    private void escribirRanking(String nombre, int puntaje, int nivel) {
        String aux_nombre;
        int aux_puntaje;
        int aux_nivel;
        String aux_fecha;
        Calendar fecha=new GregorianCalendar();
        String fecha_actual = fecha.get(Calendar.DAY_OF_MONTH)+"/"+fecha.get(Calendar.MONTH)+"/"+fecha.get(Calendar.YEAR);
        boolean inserto=false;

        try {
            RandomAccessFile datos = new RandomAccessFile("ranking.txt", "rw");
            datos.seek(0);
            while(datos.readLine() != null){
                lineas++;
            }
            System.out.println(lineas);
            datos.seek(0);
            for(int i=0;i<lineas;i++){
                String renglon=datos.readLine();
                System.out.println(renglon);
                String palabras[]=renglon.split("-");
                nombres[i]=palabras[0];
                puntos[i]=Integer.parseInt(palabras[1]);
                niveles[i]=Integer.parseInt(palabras[2]);
                fechas[i]=palabras[3];
            }
            System.out.println("que onda");
            //INSERTAR ORDENADO Y ESCRIBIR
            if(lineas==0){
                nombres[0] = nombre;
                puntos[0] = puntaje;
                niveles[0] = nivel;
                fechas[0] = fecha_actual;
            }else{
                if(puntaje>puntos[lineas-1]){    
                    for(int i=0;i < lineas && inserto == false;i++){
                        if(puntaje>puntos[i]){
                            aux_nombre = nombres[i];
                            aux_puntaje = puntos[i];
                            aux_nivel = niveles[i];
                            aux_fecha = fechas[i];
                            nombres[i] = nombre;
                            puntos[i] = puntaje;
                            niveles[i] = nivel;
                            fechas[i] = fecha_actual;
                            inserto = true;
                            for(int j=lineas-1;j>i;j--){
                                nombres[j+1]=nombres[j];
                                puntos[j+1]=puntos[j];
                                niveles[j+1]=niveles[j];
                                fechas[j+1]=fechas[j];
                            }
                            nombres[i+1]=aux_nombre;
                            puntos[i+1]=aux_puntaje;
                            niveles[i+1]=aux_nivel;
                            fechas[i+1]=aux_fecha;
                        }
                    }
                    //AHORA QUE YA INSERTE TENGO QUE ESCRIBIR EL ARCHIVO
                }else{
                    nombres[lineas] = nombre;
                    puntos[lineas] = puntaje;
                    niveles[lineas] = nivel;
                    fechas[lineas] = fecha_actual; 
                }
            }
            datos.seek(0);
            for(int i=0;i<10;i++){
                if(nombres[i]!=null){
                    datos.writeBytes(nombres[i]+"-");
                    datos.writeBytes(puntos[i]+"-");
                    datos.writeBytes(niveles[i]+"-");
                    datos.writeBytes(fechas[i]+"\n");
                }
            }
            datos.close();
        } catch (Exception e) {
            System.out.println("ERROR AL ESCRIBIR EL RANKING");
            System.out.println(e);
        }
    
    }

}