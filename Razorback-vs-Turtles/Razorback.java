import java.awt.Graphics;
import java.util.Random;

class Razorback extends Sprite{
	
	private int width;
	private int height;
	private int xslope;
	private int yslope;
	
	 Razorback(int posx, int posy, int posw, int posh)
	 { 
		 super(posx, posy,"razorback.png");
		 width = posw;
		 height = posh;
		
		Object slope;
		slope = new Random();
		xslope = (((Random)slope).nextInt(11) - 5);
		yslope = (((Random)slope).nextInt(11) - 5);
		
	}
	 
	  public void update(Graphics g) {
		  
		  int posx = getX() + xslope;
		  int posy = getY() + yslope;
		 
	        if (posx < 0) 
	        {
	            posx = width;
	        } 
	        if 
	        (posx > width) 
	        {
	           posx = 0 ;
	        }
	        
	        if (posy < 0) 
	        {
	            posy = height;
	        } 
	        if (posy > height)
	        {
	            posy = 0;
	        }

	        
	       setX(posx);
	       setY(posy);
	        g.drawImage(getImage(), posx, posy, 100, 100, null);
	    }

	

}
