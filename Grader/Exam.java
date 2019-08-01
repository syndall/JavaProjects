
public class Exam {

	private int score;
	private String grade;
	
	public Exam(int percent, String lettergrade)
	{
		score = percent;
		
		grade = lettergrade;
		
	}
	
	public void setScore(int percent)
	{
		if(percent < 0)
			percent = 0;
		if(percent > 100)
			percent = 100;
		score = percent;	
	}
	
	
	public int getScore()
	{
		return score;
	}
	
	public void setGrade(String lettergrade)
	{
		
		
		switch(lettergrade)
		{
		case "A":lettergrade = grade;
				break;
		case "B":lettergrade = grade;
				break;
		case "C":lettergrade = grade;
				break;
		case "D":lettergrade = grade;
				break;
		case "F":lettergrade = grade;
				break;
		default: grade = "F";
				System.out.printf("You have submitted a bad grade for an Exam, value of: %s\n", lettergrade);
				
		}
	
			
	}	
	
	
	
	public String getGrade()
	{
		return grade;
	}
	
	
	
	
	
	
}
