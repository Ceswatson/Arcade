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

    public void darBonus(Bomberman bomberman){
        bomberman.setSaltoBomba();
        bomberman.addPuntos(puntos);
    }
}