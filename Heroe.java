public class Heroe extends ObjetoGrafico implements ObjetoMovible {

    private boolean muerto=false; // Sin vidas!
    private double HEROE_DESPLAZAMIENTO=80.0;
    
    public Heroe() {
        super("Recursos/Imagenes/Heroe.png");
	}

    public void setMuerte(boolean bool){
        muerto=bool;
    }
    public boolean getMuerte(){
        return muerto;
    }

    public double getDesplazamiento(){
        return HEROE_DESPLAZAMIENTO;
    }
    public void setVelocidad(){
        HEROE_DESPLAZAMIENTO+=20;
    }
    public void ResetVelocidad(){
        HEROE_DESPLAZAMIENTO=80;
    }

    public void update(double delta) { 
    }
    
}

