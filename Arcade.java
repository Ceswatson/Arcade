/*
Compilar
javac -cp ".;bucleJuego.jar" Arcade.java

Ejecutar
java -cp ".;bucleJuego.jar" Arcade
*/

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.entropyinteractive.*;

public class Arcade extends JPanel implements ActionListener,ListSelectionListener { 
  private JGame juego;
  private Thread hilo;
  
  private Configuration settings;

  private JButton boton,botonConfig;
  private JFrame miframe;
  private JPanel mipanel,imagenes,score;
  private GridBagLayout gbl=new GridBagLayout();
  private GridBagConstraints gbc=new GridBagConstraints();
  private JList<String> myList;
  private String[] contenedores={"Pacman","Bomberman","Street Fighter II"};
  private CardLayout cardLayout;
  private boolean BombermanFlag=false;
  private JLabel ranking;
  private JTextArea area;
  private JScrollPane scp;

  public Arcade(){
    miframe = new JFrame("ARCADE");
    miframe.setSize(800, 600);
    miframe.setVisible(true);
    
    miframe.setLocationRelativeTo(null);
    miframe.setLayout(new BorderLayout());

    mipanel = new JPanel();
    mipanel.setBackground(Color.GRAY);
    mipanel.setLayout(gbl);

    gbc.insets = new Insets(5,5,5,40);

    myList = new JList<>(contenedores);
    myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myList.addListSelectionListener(this);
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1; 
    gbc.gridheight = 3;
    gbc.weightx = 0.0;
    gbc.weighty = 1.25;
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.anchor = GridBagConstraints.WEST;
    mipanel.add(myList, gbc);

    imagenes = new JPanel();
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 1.25;
    gbc.weighty = 1.25;
    gbc.fill = GridBagConstraints.BOTH;
    mipanel.add(imagenes,gbc);
    imagenes.setBackground(Color.GRAY);
    cardLayout = new  CardLayout();
    imagenes.setLayout(cardLayout);
    
    try {
      imagenes.add(contenedores[0],new JLabel(new ImageIcon("Recursos/Imagenes/imagen1.jpeg")));
      imagenes.add(contenedores[1],new JLabel(new ImageIcon("Recursos/Imagenes/imagen211.png")));
      imagenes.add(contenedores[2],new JLabel(new ImageIcon("Recursos/Imagenes/imagen3.jpeg")));
    } catch (Exception e) {
      System.out.println("ERROR AL CARGAR IMAGENES DE JUEGOS");
      System.out.println(e);
  }

    score = new JPanel();
    score.setLayout(new BorderLayout());
    score.setBackground(Color.BLACK);
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 1.25;
    gbc.weighty = 1.25;
    gbc.fill = GridBagConstraints.BOTH;
    ranking = new JLabel("RANKING");
    ranking.setForeground(Color.WHITE);
    area = new JTextArea("");
    area.setBackground(Color.BLACK);
    area.setForeground(Color.WHITE);
    gbc.weightx = 3;
    gbc.weighty = 3;
    scp = new JScrollPane(area);
    scp.setFont(new java.awt.Font("Tahoma",0,14));
    score.add(ranking,BorderLayout.NORTH);
    score.add(scp,BorderLayout.CENTER);
    score.setSize(30, 50);
    mipanel.add(score,gbc);


    boton = new JButton("Jugar");
    boton.addActionListener(this);
    gbc.gridx = 1; //arranque
    gbc.gridy = 2;
    gbc.gridwidth = 1; //cuantas columnas o filas o cupe
    gbc.gridheight = 1;
    gbc.weightx = 1.25; // que tanto de ancho que extiende la columna cuando se pasa a fullscreen
    gbc.weighty = 0.0;
    gbc.fill = GridBagConstraints.HORIZONTAL; //llenar horizontal 
    mipanel.add(boton, gbc);

    botonConfig = new JButton("Configuracion");
    botonConfig.addActionListener(this);
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mipanel.add(botonConfig, gbc);

        
    miframe.add(mipanel);

    miframe.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent windowEvent){
          System.exit(0);
        }        
    });

    miframe.pack();
  }
  
  public void actionPerformed(ActionEvent e){
    if (e.getActionCommand().equals("Jugar") && BombermanFlag==true){
      juego = new Bomberman();

     hilo = new Thread() {
         public void run() { 
            juego.run(1.0 /144);
         }
     };
     hilo.start();
    }
    
    if (e.getActionCommand().equals("Configuracion")){
      
      settings = new Configuration();
      JFrame fconfig = new JFrame();
      fconfig.setSize(800,600);
      fconfig.add(settings);
      
      fconfig.setVisible(true);
      fconfig.setLocationRelativeTo(null);

      JDialog jd = new JDialog(fconfig, "configuraciones");
      
    }
  }

  public void valueChanged(ListSelectionEvent e) {
    // TODO Auto-generated method stub
    cardLayout.show(imagenes, myList.getSelectedValue());
    if (myList.getSelectedValue() == "Bomberman"){
        BombermanFlag = true;
        cargarRank();
      }else{
        BombermanFlag = false;
        area.setText("no hay datos");
      }  
  }

  public void cargarRank(){
    area.setText("");
    int cont = 0;
    String linea;
    try {
      RandomAccessFile datos = new RandomAccessFile("ranking.txt", "r");
      while((linea = datos.readLine()) != null && cont<10){
        area.append(linea + "\n");
        cont++;
      }
      datos.close();
    } catch (Exception e) {
      //TODO: handle exception
    }
  }

  public static void main(String[] args){
    Arcade ar = new Arcade();
  }
}