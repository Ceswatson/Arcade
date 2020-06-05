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

//Menu del Juego, donde se puede seleccionar jugar o configuraciones.
public class Bomberman extends JGame {
    private int x = 1024;
    private int y = 768;

    
    private BufferedImage fondo=null;
    private BufferedImage logo=null; 

    private Heroe hero;
    private Fondo background;
    private Vector<Fantasma> vecGhost;
    private Vector<Pared> vecPared;
    private Puerta door;




    public Bomberman() {
        super("Bomberman", 1024, 768);

        System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        
        background = new Fondo();
        
        hero = new Heroe();
        hero.setImagen(ImageIO.read(getClass().getResource("Recursos/Imagenes/Heroe.jpg")));
        hero.setPosition(getWidth() / 2,getHeight() / 2 ); // setear poscicion de inicio
        
       
        vecGhost = new Vector<Fantasma>();
        vecPared = new Vector<Pared>();



        /*try{
         
        }
        catch(Exception e){}
        */
    }
    public void gameUpdate(double delta) {}
    
    public void gameDraw(Graphics2D g) {
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.ITALIC,25));
        g.drawString("START GAME",this.x/2,this.y/2);
        //g.drawString("RANKING",this.x/2,this.y/2+this.getHeight()/10+this.getHeight()/100);
        g.drawString("CONFIGURATION",this.x/2,this.y/2+2*this.getHeight()/10+2*this.getHeight()/100);
        //.drawString("HELP",this.x/2,this.y/2+3*this.getHeight()/10+3*this.getHeight()/100);
        g.drawString("EXIT",this.x/2,this.y/2+4*this.getHeight()/10+4*this.getHeight()/100);

    }
    public void gameShutdown() {}
}