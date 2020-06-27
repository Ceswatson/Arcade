
import com.entropyinteractive.*;
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
	private String nameLabel[] = { "fullScreen", "Sound", "OriginalMusic", "Controls", "Save", "Reset" }; 
	private JToggleButton[] button;
	private JLabel title;
	protected Properties gameproperties = new Properties();
	protected String fileProperties="jgame.properties";
	protected Thread t;

	// Constructor
	public Configuration(){
		setBackground(Color.LIGHT_GRAY);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		button = new JToggleButton[6];
		readPropertiesFile();

		title = new JLabel();
		//title = new JLabel("Configuraciones");
		title.setForeground(Color.BLACK);
		title.setFont(new Font("Helvetica", Font.BOLD, 20));
		title.setIcon(new ImageIcon("Recursos/Imagenes/BOMBER_MAN.png"));
		title.setIconTextGap(8);
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
			button[i].setBackground(Color.GRAY);
			button[i].addActionListener(this);
			button[i].setBackground(Color.WHITE);//WHITE
			button[i].setOpaque(false);
			button[i].setBorderPainted(false);
			button[i].setForeground(Color.BLACK);
			button[i].setFont(new Font("Helvetica", Font.BOLD, 15));
			button[i].setIconTextGap(6);
			if (nameLabel[i] != "Save" && nameLabel[i] != "Reset" && nameLabel[i] != "Controls")
				button[i].setSelected(Boolean.parseBoolean(gameproperties.getProperty(nameLabel[i])));
			add(button[i], c);
		}
		
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == "Save") {
			if (button[0].isSelected()) {
				gameproperties.setProperty("fullScreen", "true");
			}else
				gameproperties.setProperty("fullScreen", "false");	
			if (button[1].isSelected()){
				gameproperties.setProperty("Sound", "true");
			}else
				gameproperties.setProperty("Sound", "false");
			if (button[2].isSelected()) {
				gameproperties.setProperty("OriginalMusic", "true");
			}else	
				gameproperties.setProperty("OriginalMusic", "false");
			
			button[4].setSelected(false); // Deselecciono Save

			// Guarda en el archivo todas estas nuevas configuraciones
			try {
				FileOutputStream out = new FileOutputStream(fileProperties);
				gameproperties.store(out, null);
				out.close();
			} catch (Exception e) {}
		}

		if(evt.getActionCommand() == "Controls"){
			JFrame jf = new JFrame();
			jf.setLayout(new BorderLayout());
			jf.setSize(805, 460);
			jf.setVisible(true);
			JLabel JL = new JLabel();
			JL.setIcon(new ImageIcon("Recursos/Imagenes/Controles.jpeg"));
			jf.setLocationRelativeTo(null);
			jf.add(JL);
			button[3].setSelected(false);
		}
				
		if (evt.getActionCommand() == "Reset") {
			for (int i = 0; i < nameLabel.length; i++)
				button[i].setSelected(false);
			button[5].setSelected(false);
			try {
				FileOutputStream out = new FileOutputStream(fileProperties);
				gameproperties.store(out, null);
				out.close();
			} catch (Exception e) {}
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