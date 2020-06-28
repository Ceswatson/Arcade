import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class ParedLadrillo extends Pared {
 
    public ParedLadrillo(int x,  int y) {
        super("Recursos/Imagenes/Ladrillo.png");
        setPosition(x, y);
    }

    public void soltarBonus(Bomberman bomberman,Vector<Bonus> vecBonusRandom,Vector<Bonus> vecBonus,Puerta puerta){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
        int randomBonus = ThreadLocalRandom.current().nextInt(0, vecBonusRandom.size());
        String bonusName;
        boolean salio = false;
        if(!puerta.getPuertaON()){
            if(randomNum<7){
                puerta.setPosition((int)positionX,(int)positionY);
                puerta.setPuertaON(true);
                salio = true;
            }
        }
        if(!bomberman.getDetonador() && salio==false){
            if(randomNum<8){
                vecBonus.addElement(new BonusDetonador((int)positionX,(int)positionY));
                bomberman.setDetonador();
                //detonadorON = true;
                salio = true;
            }
        }
        if(!bomberman.getSaltoBomba() && salio==false){
            if(randomNum<1){
                vecBonus.addElement(new BonusSaltarBomba((int)positionX,(int)positionY));
                //detonadorON = true;
                salio = true;
            }
        }
        if(salio==false){
            if(randomNum<6){ // 2 default
                bonusName = vecBonusRandom.elementAt(randomBonus).getClass().getName();
                switch(bonusName){
                    case "BonusFlama" :
                        vecBonus.addElement(new BonusFlama((int)positionX,(int)positionY));
                    break;
                    case "BonusBomba" :
                        vecBonus.addElement(new BonusBomba((int)positionX,(int)positionY));
                    break;
                    case "BonusVida" :
                        vecBonus.addElement(new BonusVida((int)positionX,(int)positionY));
                    break;
                    case "BonusVelocidad" :
                        vecBonus.addElement(new BonusVelocidad((int)positionX,(int)positionY));
                    break;
                }
            }
        }
    }
}