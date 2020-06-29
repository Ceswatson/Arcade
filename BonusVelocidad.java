import java.awt.image.*;

public class BonusVelocidad extends Bonus {
    public BonusVelocidad(int x,  int y) {
        super("Recursos/Imagenes/Bonus/Speed.png");
        setPosition(x, y);
    }
    public BonusVelocidad() {
        super("Recursos/Imagenes/Bonus/Speed.png");
    }

    public void setImagen(final BufferedImage img){ 
        this.imagen=img; 
    } 
    public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

    public void darBonus(Bomberman bomberman){
        bomberman.getHeroe().setVelocidad();
        bomberman.addPuntos(puntos);
    }
}