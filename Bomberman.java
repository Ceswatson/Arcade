import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream; 
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

import com.entropyinteractive.*;

//Menu del Juego, donde se puede seleccionar jugar
public class Bomberman extends JGame implements ActionListener {
    
    private Heroe hero;
    private Puerta puerta;
    private Fondo background;
    private Fondo gris;
    private Fondo STAGE;
    private Fondo GAMEOVER;
    private Fondo MENU;
    private Fondo WIN;
    private Vector<ObjetoGrafico> Objetos;
    private Vector<Fantasma> vecGhost;
    private Vector<Pared> vecParedes;
    private Vector<Pared> vecBloquesDisponibles;
    private Vector<Bonus> vecBonus;
    private Vector<Bonus> vecBonusRandom;
    private Vector<Bomba> vecBombas;
    private Vector<Bomba> vecFlama;
    private String restriccion="libre";
    private boolean retardo = false;
    private final int CANT_LADRILLOS = 90;
    private final int bloque = 32;
//////////////Valores Por Defecto//////////
    private int CANT_BOMBAS = 1;
    private int CANT_FANTASMAS = 5;
    private int CANT_VIDAS = 3;
    private int CANT_FLAMA = 1;
    private int LEVEL=1;
    private int PUNTAJE=0;
    private boolean DETONADOR=false;
    private boolean saltoBomba = false;
    //////////////////////////////////////
    private Camara camara; 
    private Sonido musica,boom,soltarbomb,caminata,bonus,muerte,finJuego,siguienteLevel;
    private boolean flag_sonido, flagMusica;
    ////////// TIMER //////////
    private Date dInit;
    private Date dAhora;
    private long TIMER = 203;
    private long tiempoTranscurrido;
    ///////Frame Para Fin de Juego//////
    private JFrame gameover;
    private JLabel label, label2;
    private JTextField textField;
    private JButton boton;
    private JTextArea JtaRanking;
    private JScrollPane JspRanking;
    ///////Ranking///////////////
    protected static String[] nombres=new String[10];
    protected static int[] puntos=new int[10];
    protected static int[] niveles=new int[10];
    protected static String[] fechas=new String[10];
    protected  int lineas=0;
    /////////////////////////////////////////
    
    public Bomberman() {
        super("Bomberman", 640, 480);
        setearPropiedades(); 
    }
    public long getTiempo(){
        dAhora= new Date();

    	long dateDiff = dAhora.getTime() - dInit.getTime();
        long tiempoTranscurrido = dateDiff / 1000;
        return (TIMER - tiempoTranscurrido);
    }
    public void setFlama(){
        if(CANT_FLAMA<5){
            CANT_FLAMA++;
        }
    }
    public int getFlama(){
        return CANT_FLAMA;
    }
    public void addBombas(){
        if(CANT_BOMBAS<3){
            CANT_BOMBAS++;
        }
    }
    public void setVidas(){
        if(CANT_VIDAS<5){
            CANT_VIDAS++;
        }
    }
    public void setDetonador(){
        DETONADOR = true;
    }
    public boolean getDetonador(){
        return DETONADOR;
    }
    public void setSaltoBomba(){
        saltoBomba=true;
    }
    public boolean getSaltoBomba(){
        return saltoBomba;
    }
    public void nextLevel(){
        LEVEL++;
    }

    public void addPuntos(int puntos){
        PUNTAJE += puntos;
    }
    public void resetValues(){
        MENU.setMostrando(true);
        retardo = false;
        puerta.setPuertaON(false);
        saltoBomba = false;
        CANT_BOMBAS = 1;
        CANT_VIDAS = 3;
        CANT_FLAMA = 1;
        LEVEL=1;
        DETONADOR=false;
        hero.setMuerte(false);
        WIN.setMostrando(false);
        PUNTAJE=0;
    }

    public void gameStartup() {
        
        dInit = new Date();
    
        camara = new Camara(0,0); //0,0
        camara.setRegionVisible(640, 448); //Ventana 640/480  448

        gris = new Fondo("Recursos/Imagenes/FondoGris.png");
        gris.setPosition(0,0);

        background = new Fondo("Recursos/Imagenes/Fondo.png");
        background.setPosition(0, 96);

        MENU = new Fondo("Recursos/Imagenes/BOMBER_MAN_MENU.png");
        MENU.setPosition(0,0);

        WIN = new Fondo("Recursos/Imagenes/Win.png");
        WIN.setPosition(0,0);

        if(LEVEL==1){
            STAGE = new Fondo("Recursos/Imagenes/stageOne.png");
        }else{
            STAGE = new Fondo("Recursos/Imagenes/STAGETWO.png");
        }
        STAGE.setPosition(0,0);
       
        GAMEOVER = new Fondo("Recursos/Imagenes/gameover.png"); 
        GAMEOVER.setPosition(0,0);

        hero = new Heroe();
        hero.setPosition(33,97);
       
        puerta = new Puerta();

        vecBloquesDisponibles = new Vector<Pared>();
        BloquesDisponibles();

        vecParedes = new Vector<Pared>();
        iniciarBloquesPiedra();
        iniciarBloquesLadrillo();

        vecGhost = new Vector<Fantasma>();
            iniciarFantasmas();
            if(LEVEL==2){
                 iniciarFantasmasAzules(3);
            }
        vecBombas = new Vector<Bomba>();
        vecFlama = new Vector<Bomba>();

        vecBonus = new Vector<Bonus>();

        vecBonusRandom = new Vector<Bonus>(); //Vector que tiene todos los bonus posibles
        vecBonusRandom.addElement(new BonusVida());
        vecBonusRandom.addElement(new BonusBomba());
        vecBonusRandom.addElement(new BonusFlama());
        vecBonusRandom.addElement(new BonusDetonador());
        vecBonusRandom.addElement(new BonusVelocidad());
        vecBonusRandom.addElement(new BonusSaltarBomba());
        
        if(flagMusica){
            musica = new Sonido("Recursos/Sonidos/Musicas/.wav/03_Stage_Theme.wav");
            musica.comenzar();
            musica.loop();
        }
    }
 
    public void gameUpdate(final double delta) {
        Keyboard keyboard = this.getKeyboard();

        if(MENU.getMostrando() == true){ //Para mostrar el Menu
            if(keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
                MENU.setMostrando(false);
                dAhora = new Date();
                dInit = dAhora;
            }
        }else{
            
            movimientoHeroe(delta,keyboard);
        
            if (hero.getX()<658){ // para que no te siga hasta el infinito 
                camara.seguirPersonaje(hero);
            }

            if (!colisionHeroe()){
                restriccion = "libre";
            }

            if (retardo == false && (keyboard.isKeyPressed(KeyEvent.VK_SPACE))){
                soltarBomba();
            }
            retraso();
            updateTimerBombas();
            explotarBombas();
            colisionFlama();
            
            moverFantasmas(delta);
            redireccionarFantasmas(delta);

            if(DETONADOR == true){  
                if(keyboard.isKeyPressed(KeyEvent.VK_CONTROL)){
                    detonarBombas();
                }  
            }
            // Esc fin del juego
            final LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
            for (final KeyEvent event: keyEvents) {
                if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                    (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                    if(flagMusica){
                        musica.detener();
                    }
                    stop(); //bucleJuego.jar
                }               
            }
            ///TIEMPO
            if(getTiempo()<0){
                muerte();
            }
        } 
    }  
    public void gameDraw(final Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(MENU.getMostrando()){
            MENU.display(g);
        }else{
            gris.display(g);
            background.display(g);
            g.setBackground(Color.GRAY); // No anda no se que onda

            g.translate(camara.getX(),camara.getY());
                
                if(puerta.getPuertaON()){
                    puerta.display(g);
                } 
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
            g.translate(-camara.getX(),-camara.getY()); 
            
            g.setColor(Color.BLACK);
            g.drawString("BOMBERMAN NES // By: VITALE & WATSON", 3, 490);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            g.drawString("TIMER: " + getTiempo(),3,50);
            g.drawString("VIDAS: " + CANT_VIDAS ,550,50);
            g.drawString("PUNTAJE: "+PUNTAJE,260,50);
            
            g.setColor(Color.white);
            g.drawString("TIMER: " + getTiempo(),4,51);
            g.drawString("VIDAS: " + CANT_VIDAS ,551,51);
            g.drawString("PUNTAJE: "+PUNTAJE,261,51);

            if(getTiempo()>200){
                STAGE.display(g);
            }
        }
        if(hero.getMuerte()){
            GAMEOVER.display(g);
        }
        if(WIN.getMostrando()){
            WIN.display(g);
        }

    }   
    public void gameShutdown() {
        if(!hero.getMuerte()){
            hero.setMuerte(true);
            if(flagMusica){
                musica.detener();
                finJuego = new Sonido("Recursos/Sonidos/Musicas/09_Game_Over.wav");
                finJuego.comenzar();
                finJuego.loop();
            }
            gameover = new JFrame("Fin del juego");
            gameover.setLayout(null);
            gameover.setPreferredSize(new Dimension(640, 480));
            label = new JLabel("Nombre Jugador: ");
            label2 = new JLabel("RANKING");
            textField = new JTextField();
            boton = new JButton("Aceptar");
            JtaRanking = new JTextArea();
            JspRanking = new JScrollPane(JtaRanking);

            JtaRanking.setText("");
            
            boton.addActionListener(this);
            label.setBounds(10, 20, 210, 20);
            label2.setBounds(10,60,120,20);
            textField.setBounds(150, 20, 180, 30);
            boton.setBounds(360, 20, 80, 30);
            JspRanking.setBounds(10, 90, 600, 200);

            gameover.add(label);
            gameover.add(label2);
            gameover.add(textField);
            gameover.add(boton);
            gameover.add(JspRanking);

            gameover.setAlwaysOnTop(true);
            gameover.setResizable(false);
            gameover.setLocationRelativeTo(null);
            gameover.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameover.pack();
            gameover.setVisible(true);
        }
    }
    
    public void detonarBombas(){
        if(!vecBombas.isEmpty()){
            for(int i=0;i<vecBombas.size();i++){
                if(!vecBombas.elementAt(i).getExplotando()){
                    vecBombas.elementAt(i).setExplotarAhora();
                    vecBombas.elementAt(i).setTimer();
                }
            }
        }
    }
      
    public void updateTimerBombas(){
        if(!vecBombas.isEmpty()){
            for(int i=0;i<vecBombas.size();i++){
                vecBombas.elementAt(i).updateTimer();
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
    public void muerte(){
        //puertaON=false;
        puerta.setPuertaON(false);
        CANT_VIDAS--; 
        CANT_BOMBAS=1;
        CANT_FLAMA=1;
        saltoBomba=false;
        hero.ResetVelocidad();
        if(flagMusica){
            musica.detener();
        }
        if(flag_sonido && CANT_VIDAS > 0){
            muerte = new Sonido("Recursos/Sonidos/Efectos/Muerte.wav");
            muerte.comenzar();
        }

        if(CANT_VIDAS>0){
            //reset
            gameStartup();
        }
        if(CANT_VIDAS==0){
            gameShutdown();
        }
    } 
    
    public void colisionFlama(){     
        if(!vecFlama.isEmpty()){
            for(int i=0; i<vecFlama.size();i++){
                if(puerta.getPuertaON()){ // Siempre y cuando exista la puerta
                    if(puerta.getPosicion().intersects(vecFlama.elementAt(i).getPosicion())){
                        iniciarFantasmasAzules(5);
                    }
                }
                for(int j=0;j<vecBonus.size();j++){ // Flama-Bonus/Puerta
                    if(vecBonus.elementAt(j).getPosicion().intersects(vecFlama.elementAt(i).getPosicion())){
                        vecBonus.removeElementAt(j);
                        iniciarFantasmasAzules(5);
                    }
                }
                for(int j=0;j< vecGhost.size();j++){ // Flama-Fantasma
                    if(vecGhost.elementAt(j).getPosicion().intersects(vecFlama.elementAt(i).getPosicion())){     
                        vecGhost.elementAt(j).darPuntos(this);
                        vecGhost.remove(j);
                    }
                }
                for(int j=0;j< vecBombas.size();j++){ // Flama-Bomba
                    if(vecBombas.elementAt(j).getPosicion().intersects(vecFlama.elementAt(i).getPosicion())){
                        if(!vecBombas.elementAt(j).getExplotarAhora()){
                            vecBombas.elementAt(j).setExplotarAhora();
                            vecBombas.elementAt(j).setTimer();
                        }
                    }
                }
            }
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
    public boolean colisionHeroe(){
        boolean result = false; 
        if(puerta.getPuertaON() && vecGhost.isEmpty()){ //Heroe - Puerta
           if(hero.getPosicion().intersects(puerta.getPosicion())){ 
                if(LEVEL==2){
                    WIN.setMostrando(true);
                    //flagWin=true;
                    gameShutdown();
                }else{
                    if(flagMusica){
                        musica.detener();
                        siguienteLevel = new Sonido("Recursos/Sonidos/Musicas/05_Stage_Complete.wav");
                        siguienteLevel.comenzar();
                    }
                    puerta.siguienteNivel(this);
                }
           }
        }
        for(int i=0;i<vecParedes.size();i++){ //Heroe - Paredes
            if (hero.getPosicion().intersects(vecParedes.elementAt(i).getPosicion())){
                result = true;
            }
        }
        if(!saltoBomba){ //Bonus de salto de bombas
            for(int i=0;i<vecBombas.size();i++){ //Heroe - Bomba (Obstaculo)
                if(vecBombas.elementAt(i).getTimer()>1){
                    if (hero.getPosicion().intersects(vecBombas.elementAt(i).getPosicion())){
                        result = true;
                    }
                }
            }
        }
        for(int i=0;i<vecGhost.size();i++){ // Heroe - Fantasma
            if (hero.getPosicion().intersects(vecGhost.elementAt(i).getPosicion())){
                muerte();
            }
        }
        for(int i=0;i<vecBonus.size();i++){ // Heroe - Bonus
            if (hero.getPosicion().intersects(vecBonus.elementAt(i).getPosicion())){
                if(vecBonus.elementAt(i).getClass().getName()=="BonusVelocidad"){
                    vecBonus.elementAt(i).darBonus(this,hero);
                }else{
                    vecBonus.elementAt(i).darBonus(this);
                }
                if(flag_sonido){
                    bonus = new Sonido("Recursos/Sonidos/Efectos/Bonus.wav");
                    bonus.comenzar();
                }
                vecBonus.removeElementAt(i);
            }
        }
        for(int i=0;i<vecFlama.size();i++){ // Heroe - Flama
            if (hero.getPosicion().intersects(vecFlama.elementAt(i).getPosicion())){
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
                vecGhost.addElement(new FantasmaRosa(x,y));  
                cant++;  
            }
        }
    }
    public void iniciarFantasmasAzules(int cantidad){
        int x,y;
        int cant=0;
        while(cant<cantidad){
            int cantbloques = vecBloquesDisponibles.size();
            int randomNum = ThreadLocalRandom.current().nextInt(0, cantbloques);
            x=(int)vecBloquesDisponibles.elementAt(randomNum).getX();
            y=(int)vecBloquesDisponibles.elementAt(randomNum).getY();
            if(!(x==32&&y==96) && !(x==32&&y==128) && !(x==64&&y==96)){ ///Saco la L inicial   
                vecBloquesDisponibles.remove(randomNum); //Saco el bloqueDisponible
                vecGhost.addElement(new FantasmaAzul(x,y));  
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
    
    public void explotarBombas(){
        if(!vecBombas.isEmpty()){
            for(int i=0;i<vecBombas.size();i++){ //recorro bombas en el campo
                if(vecBombas.elementAt(i).getExplotarAhora()){ //Seteo las condiciones para que explote
                }
                if(!vecBombas.elementAt(i).getExplotando() && vecBombas.elementAt(i).getTimer()==3){ //Tiempo de explotar
                    vecBombas.elementAt(i).explotar(vecFlama, this);
                }
                if(vecBombas.elementAt(i).getTimer()>=4){ // 1 Segundos despues Frena 
                    vecBombas.remove(i); 
                    CANT_BOMBAS++;
                    vecFlama.clear();
                }
            }
        }
    }

    public Vector<Bomba> cortarFlama(Vector<Bomba> vec){
        for(int j=0; j<vecParedes.size(); j++){ // j recorro paredes
            boolean flag = false;
            boolean freno = false;
            if(freno==false){
                for(int k=0; flag==false && k<vec.size();k++){ // k recorre vectores flamas
                    if (vec.elementAt(k).getPosicion().intersects(vecParedes.elementAt(j).getPosicion())){
                        flag=true;
                        freno=true;
                        if(vecParedes.elementAt(j).getClass().getName() == "ParedLadrillo"){ //choco pared ladrillo
                            
                            //soltarBonus((int)vecParedes.elementAt(j).getX(),(int)vecParedes.elementAt(j).getY());
                            
                            vecParedes.elementAt(j).soltarBonus(this,vecBonusRandom,vecBonus,puerta);
                           
                            vecParedes.remove(j); //Sacamos la pared de ladrillo del campo
                            while(k<vec.size()){ // ACA SE VE LA ANIMACION DEL LADRILLO
                                vec.removeElementAt(k); 
                            }                               
                        }else{ //choco pared piedra
                            while(k<vec.size()){
                                vec.removeElementAt(k);
                            }
                        }
                    }
                }
            }
        }
        return vec;
    }
   
    public void setearPropiedades(){ 
        final Properties propiedades=new Properties(); 
        try { 
            propiedades.load(new FileInputStream("jgame.properties")); 
            // musica 
            if (Boolean.parseBoolean(propiedades.getProperty("OriginalMusic"))){ 
                flagMusica = true;
            }else{
                flagMusica = false;
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
    public void movimientoHeroe(double delta, Keyboard keyboard) { 
        switch(restriccion){
            case "libre": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    hero.setY( hero.getY() - hero.getDesplazamiento() * delta);
                    if (colisionHeroe()){
                        restriccion = "noArriba";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    hero.setY( hero.getY() + hero.getDesplazamiento() * delta);
                    if (colisionHeroe()){
                        restriccion = "noAbajo";
                    }               
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    hero.setX( hero.getX() - hero.getDesplazamiento() * delta);
                    if (colisionHeroe()){
                        restriccion = "noIzquierda";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    hero.setX( hero.getX() + hero.getDesplazamiento() * delta);
                    if (colisionHeroe()){
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
    
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("Aceptar")){
            if(textField.getText().isEmpty()==false){
                escribirRanking(textField.getText(), this.PUNTAJE, LEVEL);
                mostrarRank(JtaRanking);
                finJuego.detener();
                resetValues();
                gameStartup();
            }
        }
    }
    private void escribirRanking(String nombre, int puntaje, int nivel) {
        String aux_nombre;
        int aux_puntaje;
        int aux_nivel;
        String aux_fecha;
        Calendar fecha=new GregorianCalendar();
        String fecha_actual = fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR);
        boolean inserto=false;

        try {
            RandomAccessFile datos = new RandomAccessFile("ranking.txt", "rw");
            datos.seek(0);
            while(datos.readLine() != null){
                lineas++;
            }
            datos.seek(0);
            for(int i=0;i<lineas;i++){
                String renglon=datos.readLine();
                String palabras[]=renglon.split("-");
                nombres[i]=palabras[0];
                puntos[i]=Integer.parseInt(palabras[1]);
                niveles[i]=Integer.parseInt(palabras[2]);
                fechas[i]=palabras[3];
            }
            //INSERTAR ORDENADO Y ESCRIBIR
            if(lineas==0){
                nombres[0] = nombre;
                puntos[0] = puntaje;
                niveles[0] = nivel;
                fechas[0] = fecha_actual;
            }else{
                if(puntaje>=puntos[lineas-1]){
                    for(int i=0;i < lineas && inserto == false;i++){
                        if(puntaje>=puntos[i]){
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
                                nombres[j]=nombres[j-1];
                                puntos[j]=puntos[j-1];
                                niveles[j]=niveles[j-1];
                                fechas[j]=fechas[j-1];
                            }
                            nombres[i+1]=aux_nombre;
                            puntos[i+1]=aux_puntaje;
                            niveles[i+1]=aux_nivel;
                            fechas[i+1]=aux_fecha;
                        }
                    }
                    //AHORA QUE YA INSERTE TENGO QUE ESCRIBIR EL ARCHIVO
                }else{
                    if(lineas<10){
                        nombres[lineas] = nombre;
                        puntos[lineas] = puntaje;
                        niveles[lineas] = nivel;
                        fechas[lineas] = fecha_actual;
                    }
                    
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
    public void mostrarRank(JTextArea jta){
        int cont = 0;
        String linea;
        try {
            RandomAccessFile datos = new RandomAccessFile("ranking.txt", "r");
            while((linea = datos.readLine()) != null && cont<10){
                jta.append(linea + "\n");
                cont++;
            }
        }catch (Exception e) {}
    }
}