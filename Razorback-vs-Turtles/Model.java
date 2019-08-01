import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class Model
{
    private ArrayList<Sprite> arrsprite;
    int pos;
    
    
    Model() throws IOException {
    	
    	arrsprite = new ArrayList();
    }

    public void update(Graphics g) {
    	
    	for (Sprite list : arrsprite) {
    	      list.update(g);
    	}
  
    }

    public void setDestination(int x, int y, int width, int height) {
    	
    	Object Move;
	
    if(pos == 0)
    {
    	Move = new Random();
    	int i = ((Random)Move).nextInt(width);
    	int c = ((Random)Move).nextInt(height);
    	Turtle now = new Turtle(i,c);
    	arrsprite.add(now);
    	Turtle.setDest(x, y);
    	pos+=1;
    }
    	else
   {
    	Move = new Razorback(x,y,width,height);
    	 this.arrsprite.add((Razorback)Move);
    	 pos = 0;
 
    	
    	
    	
    	
    }
    	
}
    
}
    	
    	
    	 
    	
    	
    	
   
   

