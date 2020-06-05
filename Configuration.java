import com.entropyinteractive.JGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.border.*;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Configuration  extends JPanel implements ActionListener  {
	private String nameLabel[] = { "fullScreen", "Sound", "OriginalMusic", "Paddle", "Controls", "Save", "Reset" }; 
	private JToggleButton[] button;
	private JLabel title;
	protected Properties gameproperties = new Properties();
	protected String fileProperties="jgame.properties";
	protected Thread t;

	// Constructor
	public Configuration(){
		/*
		title = new JLabel("Configuraciones");
		title.setForeground(Color.white);
		title.setFont(new Font("Helvetica", Font.BOLD, 20));
		title.setIcon(new ImageIcon("Recursos/Imagenes/BOMBER_MAN.png"));
		title.setIconTextGap(8);
		setLayout(new GridBagLayout());
		button = new JToggleButton[7];
		readPropertiesFile();
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridheight = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		add(title, c);
		for (int i = 0; i < nameLabel.length; i++) {
			if (nameLabel[i] == "Save")
				c.gridx -= 4;
			if (nameLabel[i] == "Reset") {
				c.gridx += 3;
				c.gridy -= 2;
			}
			c.gridy += 2;

			button[i] = new JToggleButton(nameLabel[i]);button[i].setFocusable(false);
			button[i].setBackground(Color.BLUE);
			button[i].addActionListener(this);
			button[i].setBackground(Color.RED);//WHITE
			button[i].setOpaque(false);
			button[i].setBorderPainted(false);
			button[i].setForeground(Color.white);
			button[i].setFont(new Font("Helvetica", Font.BOLD, 15));
			button[i].setIconTextGap(6);
			if (nameLabel[i] != "Save" && nameLabel[i] != "Reset" && nameLabel[i] != "Controls")
				button[i].setSelected(!Boolean.parseBoolean(gameproperties.getProperty(nameLabel[i])));
				add(button[i], c);
		}
		*/
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == "Save") {
			if (button[0].isSelected()) {
				gameproperties.setProperty("fullScreen", "false");
			} else
				gameproperties.setProperty("fullScreen", "true");
			if (button[1].isSelected())
				gameproperties.setProperty("Sound", "false");
			else
				gameproperties.setProperty("Sound", "true");
			if (button[2].isSelected()) {
				gameproperties.setProperty("OriginalMusic", "false");
			} else
				gameproperties.setProperty("OriginalMusic", "true");
			if (button[3].isSelected())
				gameproperties.setProperty("Paddle", "false");
			else
				gameproperties.setProperty("Paddle", "true");
			button[5].setSelected(false); // Deselecciono Save

			// Guarda en el archivo todas estas nuevas configuraciones
			try {
				FileOutputStream out = new FileOutputStream(fileProperties);
				gameproperties.store(out, null);
				out.close();
			} catch (Exception e) {}
			/*
			Bomberman juego = new Bomberman();
        	t=new Thread(){
       		 public void run(){
        	juego.run(1.0/60.0);}
        	};
        	t.start();
			*/
		}
		
		// los acmbios
		if (evt.getActionCommand() == "Reset") {
			for (int i = 0; i < nameLabel.length; i++)
				button[i].setSelected(false);
			button[5].setSelected(false);
		}
		if (evt.getActionCommand() == "Controls") {
			JDialog jd = new JDialog();
			JTextField keyRight, keyLeft, keyShooting;
			keyRight = new JTextField(KeyEvent.getKeyText(Integer.parseInt(gameproperties.getProperty("RIGHT"))), 25);
			keyLeft = new JTextField(KeyEvent.getKeyText(Integer.parseInt(gameproperties.getProperty("LEFT"))), 25);
			keyShooting = new JTextField(KeyEvent.getKeyText(Integer.parseInt(gameproperties.getProperty("SHOOTING"))), 25);
			keyRight.setEditable(false);
			keyLeft.setEditable(false);
			keyShooting.setEditable(false);
			jd.setModal(true);
			
			jd.setLayout(new GridLayout(0, 1));
			jd.setLocationRelativeTo(null);
			jd.setBackground(Color.BLUE);
			JButton LEFT = new JButton("LEFT");
			jd.add(LEFT);
			jd.add(keyLeft);
			JButton RIGHT = new JButton("RIGHT");
			jd.add(RIGHT);
			jd.add(keyRight);
			JButton SHOOTING = new JButton("SHOOTING");
			jd.add(SHOOTING);
			jd.add(keyShooting);
			KeyListener listener = new KeyListener() {
				public void keyPressed(KeyEvent arg0) {
					switch(((JButton)jd.getFocusOwner()).getActionCommand()){
					case "LEFT":{
						keyLeft.setText(arg0.getKeyText(arg0.getKeyCode()));
						gameproperties.setProperty("LEFT", Integer.toString(arg0.getKeyCode()));
						break;
					}
					case "RIGHT":{
						keyRight.setText(arg0.getKeyText(arg0.getKeyCode()));
						gameproperties.setProperty("RIGHT", Integer.toString(arg0.getKeyCode()));
						break;
					}
					case "SHOOTING":{
						keyShooting.setText(arg0.getKeyText(arg0.getKeyCode()));
						gameproperties.setProperty("SHOOTING", Integer.toString(arg0.getKeyCode()));

					}
					}
				}

				public void keyReleased(KeyEvent arg0) {
				}

				public void keyTyped(KeyEvent arg0) {
				}
			};
			
			jd.addKeyListener(listener);
			RIGHT.addKeyListener(listener);
			LEFT.addKeyListener(listener);
			SHOOTING.addKeyListener(listener);
			jd.setSize(400, 200);
			jd.setVisible(true);
		}
	}
	
	public void readPropertiesFile() {

		try {
			FileInputStream in = new FileInputStream(fileProperties);
			gameproperties.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("Error en metodo  readPropertiesFile(): " + e);
		}

	}	
}