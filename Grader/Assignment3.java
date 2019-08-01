import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Assignment3 {
	
	public static void main(String[] args)
	{
		
		String First, Last, ACount, HCount, AGrade, HGrade,
		Finalint, Finalstring, Midtermint, Midtermstring;
		try
		{
		
		First = JOptionPane.showInputDialog("Please enter the Student's first name");
		Last = JOptionPane.showInputDialog("Please enter the Student's last name");
		Student s1 = new Student(First, Last);
		
		
		ACount = JOptionPane.showInputDialog("Please enter the number of activity grades");
		int j = Integer.parseInt(ACount);
		for(int i = 1; i <= j; i++)
		{
			AGrade = JOptionPane.showInputDialog("Please enter the score for activity #" + i);
			int g = Integer.parseInt(AGrade);
			s1.addGradedActivity(g);
			
			
		}
		
		HCount = JOptionPane.showInputDialog("Please enter the number of homework grades");
		int h =  Integer.parseInt(HCount);
	      for (int a = 1; a <= h; a++)
	      {
	        HGrade = JOptionPane.showInputDialog("Please enter the score for homework #" + a);
	        int g1 = Integer.parseInt(HGrade);
	        s1.addGradedHomework(g1);
	      }
	      
	      Midtermint = JOptionPane.showInputDialog("Please enter the percentage score on the midterm");
	      int mid = Integer.parseInt(Midtermint);
	      Midtermstring = JOptionPane.showInputDialog("Please enter the letter grade on the midterm");
	      
	      s1.setMidterm(mid, Midtermstring);
	      
	      Finalint = JOptionPane.showInputDialog("Please enter the percentage score on the Final");
	      int fin = Integer.parseInt(Finalint);
	      Finalstring = JOptionPane.showInputDialog("Please enter the letter grade on the Final");
	      
	      s1.setFinal(fin, Finalstring);
	      
	      GradePanel displayGrade = new GradePanel(s1.printGrade());
	   
	      
	      JFrame displayJFrame = new JFrame();
	      displayJFrame.add(displayGrade);
	      displayJFrame.setSize(250, 300);
	      displayJFrame.setDefaultCloseOperation(2);
	      displayJFrame.setVisible(true);
	      
	      
	      
	
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Invalid data entered. Exiting.");
		}
		
		
		
	      
	      
	      
		
		
		
		
		
		
		
		
		
	}

}
