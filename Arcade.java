/*
Compilar
javac -cp ".;bucleJuego.jar" Arcade.java

Ejecutar
java -cp ".;bucleJuego.jar" Arcade
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.entropyinteractive.*;

public class Arcade extends JPanel implements ActionListener,ListSelectionListener { 
  JGame juego;
  Thread hilo;
  

  private JFrame miframe;
  private JPanel mipanel,imagenes,score;
  GridBagLayout gbl=new GridBagLayout();
  GridBagConstraints gbc=new GridBagConstraints();
  private JList<String> mylList;
  String[] contenedores={"pacman","Bomberman","street fighter"};
  CardLayout cardLayout;

  public Arcade(){
    miframe = new JFrame("PRUEBA");
    miframe.setSize(640, 480);
    miframe.setVisible(true);
    miframe.setLocationRelativeTo(null);
    miframe.setLayout(new BorderLayout());

    mipanel = new JPanel();
    mipanel.setBackground(Color.GRAY);
    mipanel.setLayout(gbl);

    gbc.insets = new Insets(5,5,5,40);

    mylList = new JList<>(contenedores);
    mylList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    mylList.addListSelectionListener(this);
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 3;
    gbc.weightx = 0.0;
    gbc.weighty = 1.25;
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.anchor = GridBagConstraints.WEST;
    mipanel.add(mylList, gbc);

    imagenes = new JPanel();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 1.25;
    gbc.weighty = 1.25;
    gbc.fill = GridBagConstraints.BOTH;
    mipanel.add(imagenes,gbc);
    imagenes.setBackground(Color.RED);
    cardLayout = new  CardLayout();
    imagenes.setLayout(cardLayout);
    imagenes.add(contenedores[0],new JLabel(new ImageIcon("C:\\Users\\avita\\OneDrive\\Escritorio\\PROYECTO FINAL BOMBERMAN\\repositorio\\Arcade\\Recursos\\Imagenes/imagen1.jpeg")));
    imagenes.add(contenedores[1],new JLabel(new ImageIcon("C:\\Users\\avita\\OneDrive\\Escritorio\\PROYECTO FINAL BOMBERMAN\\repositorio\\Arcade\\Recursos\\Imagenes/imagen211.png")));
    imagenes.add(contenedores[2],new JLabel(new ImageIcon("C:\\Users\\avita\\OneDrive\\Escritorio\\PROYECTO FINAL BOMBERMAN\\repositorio\\Arcade\\Recursos\\Imagenes/imagen3.jpeg")));


    score = new JPanel();
    score.setBackground(Color.GREEN);
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 1.25;
    gbc.weighty = 1.25;
    gbc.fill = GridBagConstraints.BOTH;
    mipanel.add(score,gbc); 

    JButton boton = new JButton("Jugar");
    boton.addActionListener(this);
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 1.25;
    gbc.weighty = 0.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mipanel.add(boton, gbc);
        
  miframe.add(mipanel);

        miframe.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent windowEvent){
                        System.exit(0);
            }        
        });
    //this.setLayout(new GridLayout(0,2));
    /*
      Armar la ventana del arcade
    */
    // Armo un solo boton que tira el bomberman (por ahora una pantalla negra)

    // Button boton=new Button("Bomberman");
    // boton.addActionListener(this);
    // this.add(boton);
    
  }
  
  public void actionPerformed(ActionEvent e){
    /*
      e.getActionCommand() y...
      run() para el juego
    */
    //Escucho el boton y runeo el Bomberman
    if (e.getActionCommand().equals("Jugar")){
      juego = new Bomberman();

     hilo = new Thread() {
         public void run() {
             juego.run(1.0 / 60.0);
         }
     };
     hilo.start();
    }

  }



  public void valueChanged(ListSelectionEvent e) {
    // TODO Auto-generated method stub
    cardLayout.show(imagenes, mylList.getSelectedValue());
  }


  public static void main(String[] args){
		// Frame f= new Frame ("Arcade");
    // Arcade ar = new Arcade();
		// f.add(ar);
		// WindowListener l=new WindowAdapter() {
    //   public void windowClosing(WindowEvent e) {
    //     System.exit(0);
    //   };
    // };
    // f.addWindowListener(l);
    // f.pack();
    // f.setVisible(true);
     // f.setLocationRelativeTo(null);
    Arcade ar = new Arcade();
  }


  
}