public class Heroe extends ObjetoGrafico implements ObjetoMovible {

    private boolean muerto=false;
    private double HEROE_DESPLAZAMIENTO=80.0;
    
    public Heroe() {
        super("Recursos/Imagenes/Heroe.png");
	}

    public void setX(final double x){
        positionX=x;
    }
    public void setY(final double y){
        positionY=y;
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
    
    /*
	public void update(double delta, Keyboard keyboard, String restriccion,Bomberman bomberman) {
        switch(restriccion){
            case "libre": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                    if (bomberman.colisionHeroe()){
                        restriccion = "noArriba";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                    if (bomberman.colisionHeroe()){
                        restriccion = "noAbajo";
                    }               
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                    if (bomberman.colisionHeroe()){
                        restriccion = "noIzquierda";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                    if (bomberman.colisionHeroe()){
                        restriccion = "noDerecha";
                    }
                }
            break;
            case "noArriba": 
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noArriba_noIzquieda";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noArriba_noDerecha";
                    }
                }
            break;
            case "noAbajo": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noAbajo_noIzquierda";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noAbajo_noDerecha";
                    }
                }
            break;
            case "noIzquierda": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noIzquierda_noArriba";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noIzquierda_noAbajo";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                }
            break;
            case "noDerecha": 
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noDerecha_noArriba";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion                 
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                    if(bomberman.contadorColision() > 2){
                        restriccion = "noDerecha_noAbajo";
                    }
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                }              
            break;
            /////////////////////////////////////////////////////////////////////////////////
            case "noArriba_noIzquieda":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                    
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                }
            break; 
            case "noArriba_noDerecha":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                } 
            break;

            case "noAbajo_noIzquierda":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                }
            break;

            case "noAbajo_noDerecha":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                } 
            break;
            case "noIzquierda_noArriba":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                }
            break;
            case "noIzquierda_noAbajo":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
                    //Animacion
                    this.setX( this.getX() + this.getDesplazamiento() * delta);
                }
            break;
            case "noDerecha_noAbajo":
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
                    //Animacion la debe hacer Heroe.java
                    this.setY( this.getY() - this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                }
            break;
            case "noDerecha_noArriba":
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
                    //Animacion
                    this.setY( this.getY() + this.getDesplazamiento() * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
                    //Animacion
                    this.setX( this.getX() - this.getDesplazamiento() * delta);
                }
            break;
        }

    }

    public void update(double delta) {

    }
    
    public boolean chocaPared(Vector<Pared> vecParedes){
        boolean result=false;
        for(int i=0;i<vecParedes.size();i++){ //Heroe - Paredes
            if (this.getPosicion().intersects(vecParedes.elementAt(i).getPosicion())){
                result = true;
            }
        }
        return result;
    }

    public int contadorColision(Vector<Pared> vecParedes){
        int cont=0; 
        for(int i=0;i<vecParedes.size();i++){
            if (this.getPosicion().intersects(vecParedes.elementAt(i).getPosicion())){
                cont ++;
            }
        }
        for(int i=0;i<vecBombas.size();i++){
            if (this.getPosicion().intersects(vecBombas.elementAt(i).getPosicion())){
                cont ++;
            }
        }
        return cont; 
    }
    */
 
}

