
public class Student {
	
	private String firstName, lastName;
	private Exam Midterm, Final;
	private Coursework Thomework, Tclasswork;
	private String courseGrade;
	private int UnadjustedGrade;
	
	public Student(String first, String last){
		
		firstName = first;
		lastName= last;
		Thomework = new Coursework();
		Tclasswork = new Coursework();

	}
	

	public void setMidterm(int score, String grade)
	{
		Midterm = new Exam(score, grade);
		Midterm.setScore(score);
		Midterm.setGrade(grade);
	}
	
	public void setFinal(int score, String grade)
	{
		Final = new Exam(score, grade);
		Final.setScore(score);
		Final.setGrade(grade);
	}
	
	public void addGradedHomework(int score)
	{	
		
		
		Thomework.addHomeWork(score);

		
	}
	
	public void addGradedActivity(int score)
	{

		
		Tclasswork.addClassWork(score);
		
	}
	
	public int  MinExam(){
		
		String Letter, Letter2;
		int check, check2;
	
		Letter = Final.getGrade();
		Letter2 = Midterm.getGrade();
		switch(Letter)
		{
		case "A": check = 5;
				break;
		case "B": check = 4;
				break;
		case "C": check = 3;
				break;
		case "D": check = 2;
				break;
		case "F": check = 1;
				break;	
	
		default: check = 10;
		
		}
		
		switch(Letter2)
		{
		case "A": check2 = 5;
				break;
		case "B": check2 = 4;
				break;
		case "C": check2 = 3;
				break;
		case "D": check2 = 2;
				break;
		case "F":check2 = 1;
				break;
	
		default: check2 = 10;
		
		}
		
		if(check < check2)
		{
			
			return check;
		}
		if( check > check2)
		{
			return check2;
		}
		
		else return check;
		
	}
	
	
	public String CurvedGrade(int LowestTestGrade, int FinalGrade )
	{
		int CurvedGrade;
		if(LowestTestGrade > FinalGrade)
		{
			CurvedGrade = LowestTestGrade;
		}
		else
		{
				 CurvedGrade = FinalGrade;
		}
		switch(CurvedGrade)
		{
		case 5 : courseGrade = "A";
				break;
		case 4: courseGrade = "B";
				break;
		case 3: courseGrade = "C";
				break;
		case 2: courseGrade = "D";
				break;
		case 1 : courseGrade = "F";
				break;
		
		}
		
		return courseGrade;
		
	}
 
	public String printGrade()
	{
		double courseScore;
		int lowestExam;
		
		courseScore = ((Tclasswork.getAverage()) * 0.15) + ((Thomework.getAverage()) * 0.25) +
				(Midterm.getScore() * 0.25) + (Final.getScore() * 0.35 );
		
		if(courseScore < 60 )
		{
			courseGrade = "F";
			UnadjustedGrade = 1;
		}
			
		if(courseScore >= 60 && courseScore  < 70 )
		{
			courseGrade = "D";
			UnadjustedGrade = 2;
		}
			
		if(courseScore >= 70 && courseScore  < 80 )
		{
			courseGrade = "C";
			UnadjustedGrade = 3;
		}
			
		if(courseScore >= 80 && courseScore  < 90 )
		{
			courseGrade = "B";
			UnadjustedGrade = 4;
			
		}
			
		if(courseScore >= 90 )
		{
			courseGrade = "A";
			UnadjustedGrade =  5;
			
		}
		
		lowestExam = MinExam();
		courseGrade = CurvedGrade(lowestExam,UnadjustedGrade);
		return courseGrade;
	}
	
	
	
}
