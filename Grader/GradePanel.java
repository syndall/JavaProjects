import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class GradePanel 
extends JPanel {

	private String grade;
	
	public GradePanel(String Stringgrade)
	{
		grade = Stringgrade;
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int w,h;
		
		w = getWidth();
		h = getHeight();
		if(grade == "A")
		{
			g.drawLine((w/2), 0, 0, h);
			g.drawLine((w / 4), (h / 2), (3 * w) / (4), (h / 2));
		    g.drawLine((w/2), 0, w, h);
		   
			
		}
		else
		{
		g.setColor(Color.BLUE);
		Font gFont = new Font("TimesRoman",10,100);
		g.setFont(gFont);
		g.drawString(grade, (w/2), (h/2));
		
		
		}
		
	}
	
	
	
}
