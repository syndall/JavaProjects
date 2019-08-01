
public class Coursework {

	private double Total;
	private int Count;
	private static double HomeWorkTotal, ClassWorkTotal ;
	private static int HomeWorkCount, ClassWorkCount ;
	
	public Coursework(){
		
		Total = 0;
		Count = 0;
		ClassWorkTotal = 0;
		ClassWorkCount = 0;
	}
	
	

	
	public void addHomeWork(double score)
	{
		if(score < 0)
		{
			score = 0;
		}
		
		if(score > 100)
		{
			score = 100;
		}
			
		Total = Total + score;
		Count++;
		Total = HomeWorkTotal;
		Count = HomeWorkCount;
		
	}
	
	public void addClassWork(double score)
	{
		if(score < 0)
		{
			score = 0;
		}
		
		if(score > 100)
		{
			score = 100;
		}
			
		Total = Total + score;
		Count++;
		
		Total = ClassWorkTotal;
		Count = ClassWorkCount;
		
		
	}
	
	public double getAverage()
	{
		
		return Total / Count;
	}
	
	

	


	
	
	
}
