import java.awt.image.*;

public class BonusDetonador extends Bonus {
    private boolean detonadorON=false;

    public BonusDetonador(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Detonator.png");
        setPosition(x, y);
    }
    public BonusDetonador() {
        super("Recursos/Imagenes/Bonus/Detonator.png");
    }

    public void setImagen(final BufferedImage img){ 
        this.imagen=img; 
    } 
    public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

    public void darBonus(Bomberman bomberman){
        detonadorON=true;
        bomberman.addPuntos(puntos);
    }

    public boolean getDetonador(){
        return detonadorON;
    }
    public void setDetonador(boolean bool){
        detonadorON=bool;
    }
}