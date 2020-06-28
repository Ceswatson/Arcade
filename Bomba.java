import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Bomba extends ObjetoGrafico {
    private long timer;
    private boolean explotando = false;
    private boolean explotarAhora = false;
    private Date dInit;
    private Date dAhora;
    private long addTres=0;

    public Bomba(int x, int y) {
        super("Recursos/Imagenes/Bomba/Bomba1.png");
        setPosition(x, y);
        dInit = new Date();
    }

    public Bomba(int x, int y,String cadena) {
        super("Recursos/Imagenes/Bomba/Bomba1.png");
        setPosition(x, y);
        dInit = new Date();
        setFlama(cadena);
    }

    public void setExplotarAhora(){
        explotarAhora = true;
    }
    public boolean getExplotarAhora(){
        return explotarAhora;
    }

    public void setTimer(){
        dAhora = new Date();
        this.dInit = dAhora;
        addTres=3;
    }
    public long getTimer(){
        return this.timer;
    }

    public void updateTimer(){
        dAhora= new Date( );
        long dateDiff = dAhora.getTime() - dInit.getTime();
        this.timer = (dateDiff / 1000 % 60)+addTres;
    }

    public void setExplotando(){
        this.explotando = true;
    }
    public boolean getExplotando(){
        return explotando;
    }
    public void setFlama(String direccion){
        switch(direccion){
            case "medio":
            try {
                this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/MedioBomba.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
            case "arriba":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaArriba.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "abajo":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaAbajo.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "izquierda":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaIzquierda.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "derecha":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/PuntaDerecha.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "horizontal":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/MedioHorizontal.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case "vertical":
                try {
                    this.imagen = ImageIO.read(getClass().getResource("Recursos/Imagenes/Explosion/MedioVertical.png"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
        }
    }
    public void explotar(Vector<Bomba> vecFlama,Bomberman bomberman){
        Vector <Bomba> vecArriba = new Vector<Bomba>();
        Vector <Bomba> vecAbajo = new Vector<Bomba>(); 
        Vector <Bomba> vecDerecha = new Vector<Bomba>();
        Vector <Bomba> vecIzquierda = new Vector<Bomba>();
        int pasos=0;
        int distancia=32;

        this.setExplotando();
        this.setFlama("medio");
        vecFlama.addElement(this);
    
        if( bomberman.getFlama()== 1){
            vecArriba.addElement(new Bomba((int)positionX,(int)positionY-distancia,"arriba"));
            vecAbajo.addElement(new Bomba((int)positionX,(int)positionY+distancia,"abajo"));
            vecIzquierda.addElement(new Bomba((int)positionX-distancia,(int)positionY,"izquierda"));
            vecDerecha.addElement(new Bomba((int)positionX+distancia,(int)positionY,"derecha"));
        }else{
            do{ //Cargo chorizos de flama
                vecArriba.addElement(new Bomba((int)positionX,(int)positionY-distancia,"vertical"));
                vecAbajo.addElement(new Bomba((int)positionX,(int)positionY+distancia,"vertical"));
                vecIzquierda.addElement(new Bomba((int)positionX-distancia,(int)positionY,"horizontal"));
                vecDerecha.addElement(new Bomba((int)positionX+distancia,(int)positionY,"horizontal")); 

                distancia += 32;
                pasos++;
            }while(pasos<bomberman.getFlama());
            //Acomodo las puntas
            vecArriba.lastElement().setFlama("arriba");
            vecAbajo.lastElement().setFlama("abajo");
            vecIzquierda.lastElement().setFlama("izquierda");
            vecDerecha.lastElement().setFlama("derecha");
        }

        vecArriba = bomberman.cortarFlama(vecArriba);
        vecAbajo = bomberman.cortarFlama(vecAbajo);
        vecIzquierda = bomberman.cortarFlama(vecIzquierda);
        vecDerecha = bomberman.cortarFlama(vecDerecha);

        //Agrego las flamas a vecFlama 
        vecFlama.addAll(vecArriba);
        vecFlama.addAll(vecAbajo);
        vecFlama.addAll(vecIzquierda);
        vecFlama.addAll(vecDerecha);    
    }

}