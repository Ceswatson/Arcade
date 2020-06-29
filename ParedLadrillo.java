import java.util.concurrent.ThreadLocalRandom;

public class ParedLadrillo extends Pared {
 
    public ParedLadrillo(int x,  int y) {
        super("Recursos/Imagenes/Ladrillo.png");
        setPosition(x, y);
    }

    public void soltarBonus(Bomberman bomberman){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
        int randomBonus = ThreadLocalRandom.current().nextInt(0, bomberman.getvecBonusRandom().size());
        String bonusName;
        boolean salio = false;
        if(!bomberman.getPuerta().getPuertaON()){
            if(randomNum<7){
                bomberman.getPuerta().setPosition((int)positionX,(int)positionY);
                bomberman.getPuerta().setPuertaON(true);
                salio = true;
            }
        }
        if(!bomberman.getDetonador() && salio==false){
            if(randomNum<8){
                bomberman.getvecBonus().addElement(new BonusDetonador((int)positionX,(int)positionY));
                bomberman.setDetonador();
                //detonadorON = true;
                salio = true;
            }
        }
        if(!bomberman.getSaltoBomba() && salio==false){
            if(randomNum<1){
                bomberman.getvecBonus().addElement(new BonusSaltarBomba((int)positionX,(int)positionY));
                //detonadorON = true;
                salio = true;
            }
        }
        if(salio==false){
            if(randomNum<6){ // 2 default
                bonusName = bomberman.getvecBonusRandom().elementAt(randomBonus).getClass().getName();
                switch(bonusName){
                    case "BonusFlama" :
                        bomberman.getvecBonus().addElement(new BonusFlama((int)positionX,(int)positionY));
                    break;
                    case "BonusBomba" :
                        bomberman.getvecBonus().addElement(new BonusBomba((int)positionX,(int)positionY));
                    break;
                    case "BonusVida" :
                        bomberman.getvecBonus().addElement(new BonusVida((int)positionX,(int)positionY));
                    break;
                    case "BonusVelocidad" :
                        bomberman.getvecBonus().addElement(new BonusVelocidad((int)positionX,(int)positionY));
                    break;
                }
            }
        }
    }
}