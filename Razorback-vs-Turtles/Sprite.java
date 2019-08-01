import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;



public abstract class Sprite {
	
	public int x;
    public int y;
    private Image image;

    
    Sprite(int posx, int posy, String parimage)
    {
    	  try {
              image = ImageIO.read(new File(parimage));
          } catch (IOException ioe) {
              System.out.println("Unable to load image file.");
          }
    	x = posx;
    	y = posy;
    }

public void setX(int xIn) { x = xIn; }
public void setY(int yIn) { y = yIn; }	
public int getX() { return x; }
public int getY() { return y; }
public Image getImage(){return image;}
public abstract void update(Graphics g);

}

