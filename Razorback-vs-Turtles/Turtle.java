import java.awt.Graphics;


class Turtle extends Sprite
{
    private static int dest_x;
    private  static int dest_y;

   
    Turtle(int posx, int posy) {
    	
    	super(posx,posy, "turtle.png");

    }
    


    public void update(Graphics g) {
     
    	int posx = getX();
    	int posy = getY();
    	if (posx < dest_x)
        {
            posx++;
        } else if (posx > dest_x) {
            posx--;
        }
     
        if (posy < dest_y) 
        {
            posy++;
        } else if (posy > dest_y) 
        {
            posy--;
        }
        

	     setX(posx);
	     setY(posy);
	     
        g.drawImage(getImage(), posx, posy, 100, 100, null);
    }

    public  static void setDest(int x, int y) {
        dest_x = x;
        dest_y = y;
    }
}