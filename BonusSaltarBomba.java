import java.awt.*;
import java.awt.image.*;

public class BonusSaltarBomba extends Bonus { // Desactiba colison Heroe-Bomba
    public BonusSaltarBomba(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Bombpass.png");
        setPosition(x, y);
    }
    public BonusSaltarBomba() {
        super("Recursos/Imagenes/Bonus/Bombpass.png");
    }

    public void setImagen(final BufferedImage img){ 
        this.imagen=img; 
    } 
    public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

    public void draw(final Graphics2D g){
        g.drawImage(imagen,(int)getX(),(int)getY(),null);
    }

    public void darBonus(Bomberman bomberman,Heroe hero){
        bomberman.setSaltoBomba();
        bomberman.addPuntos(puntos);
    }
}