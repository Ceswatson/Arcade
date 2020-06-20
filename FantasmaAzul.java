import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;

public class FantasmaAzul extends Fantasma{
    
    public FantasmaAzul(int x, int y){
        super("Recursos/Imagenes/FantasmaAzul.png");
        setPosition(x+1, y+1);
    }
    public void draw(final Graphics2D g) {
        g.drawImage(imagen, (int) getX(), (int) getY(), null);
    }
}
    
    
    
