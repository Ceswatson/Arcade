public class Fondo extends ObjetoGrafico {
	boolean mostrando=false;
	
	public Fondo(String filename) {
		super(filename);	
	}

	public void setMostrando(boolean bool){
		mostrando = bool;
	}

	public boolean getMostrando(){
		return mostrando;
	}
}