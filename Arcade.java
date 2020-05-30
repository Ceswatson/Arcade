/*
Compilar
javac -cp ".;bucleJuego.jar" Arcade.java

Ejecutar
java -cp ".;bucleJuego.jar" Arcade
*/

import java.awt.*;
import java.awt.event.*;
  
import com.entropyinteractive.*; //las librerias JGame,JGameLoop,KeyBoard,Mouse,etc...

public class Arcade extends Panel implements ActionListener { 
  JGame juego;
  Thread hilo;

  public Arcade(){
    this.setLayout(new GridLayout());
    /*
      Armar la ventana del arcade
    */
    // Armo un solo boton que tira el bomberman (por ahora una pantalla negra)

    Button boton=new Button("Bomberman");
    boton.addActionListener(this);
    this.add(boton);
  }
  
  public void actionPerformed(ActionEvent e){
    /*
      e.getActionCommand() y...
      run() para el juego
    */
    //Escucho el boton y runeo el Bomberman
    if (e.getActionCommand().equals("Bomberman")){
      juego = new Bomberman();

     hilo = new Thread() {
         public void run() {
             juego.run(1.0 / 60.0);
         }
     };
     hilo.start();
    }
  }

  public static void main(String[] args){
		Frame f= new Frame ("Arcade");
    Arcade ar = new Arcade();
		f.add(ar);
		WindowListener l=new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      };
    };
    f.addWindowListener(l);
    f.pack();
    f.setVisible(true);
	 	f.setLocationRelativeTo(null);
  }
  
}