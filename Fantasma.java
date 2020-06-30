import java.util.concurrent.ThreadLocalRandom;

public abstract class Fantasma extends ObjetoGrafico implements ObjetoMovible{
    protected int direccion=0;
    protected double VELOCIDAD=20;
    protected int puntos;
    public Fantasma(String filename) {
        super(filename);
        direccion = ThreadLocalRandom.current().nextInt(1, 4);
    }

    public void darPuntos(Bomberman bomberman){
        bomberman.addPuntos(puntos);
    }
    public double getDesplazamiento() {
        return VELOCIDAD;
    }
  
    public int getDireccion() {
        return this.direccion;
    }
    public void update(double delta) {
        switch (direccion) {
            case 1:
                this.setY(this.getY() - VELOCIDAD * delta); // arriba
                break;
            case 2:
                this.setY(this.getY() + VELOCIDAD * delta); // abajo
                break;
            case 3:
                this.setX(this.getX() - VELOCIDAD * delta); // izq
                break;
            case 4:
                this.setX(this.getX() + VELOCIDAD * delta); // der
                break;
        }
    }
    public void direccionContraria(final double delta) {
        
        switch (direccion) {
            case 1: // arriba
                direccion = 2;
                break;
            case 2:// abajo
                direccion = 1;
                break;
            case 3:// izq
                direccion = 4;
                break;
            case 4:// der
                direccion = 3;
                break;
        }

    }
}
