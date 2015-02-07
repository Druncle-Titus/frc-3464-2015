package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PWMIn {
	private DigitalInput in;
	Counter countHi;
	Counter countLo;
	
	public PWMIn(int pin)
	{
		in = new DigitalInput(pin);
		countHi = new Counter(in);
		countLo = new Counter(in);

		countHi.setSemiPeriodMode(true);
		countLo.setSemiPeriodMode(false);

		countHi.setPulseLengthMode(1.0);
		countLo.setPulseLengthMode(1.0);
	}
	
	public float get()
	{
		countHi.reset();
		countLo.reset();

		try { Thread.sleep(15); } catch (InterruptedException e) { }

		double axH = countHi.getPeriod();
		double axL = countLo.getPeriod();

		SmartDashboard.putString("DB/String 2",
    			"axh: " + axH);
    	SmartDashboard.putString("DB/String 3",
    			"axl: " + axL);

		return (float) ( axH / (axH + axL));

/*		long start, highEnd, lowEnd;

		while(!in.get());
		start = System.nanoTime();
		while(in.get());
		highEnd = System.nanoTime();
		while(!in.get());
		lowEnd = System.nanoTime();


    	SmartDashboard.putString("DB/String 2",
    			"NUM: " + (highEnd - start) / 1000);
    	SmartDashboard.putString("DB/String 3",
    			"DEN: " + (lowEnd - start) / 1000);

		return (float) (((double) (highEnd - start)) / (double) (lowEnd - start));*/
	}
	
	public int getInt()
	{
		long start, highEnd, lowEnd;

		while(!in.get());
		start = System.nanoTime();
		while(in.get());
		highEnd = System.nanoTime();
		while(!in.get());
		lowEnd = System.nanoTime();

    	SmartDashboard.putString("DB/String 2",
    			"NUM: " + (highEnd - start) / 1000);
    	SmartDashboard.putString("DB/String 3",
    			"DEN: " + (lowEnd - start) / 1000);

		return (int) (255 * (highEnd - start) / (1026000));
	}
}