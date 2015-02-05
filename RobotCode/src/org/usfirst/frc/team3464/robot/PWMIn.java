package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class PWMIn {
	private DigitalInput in;
	
	public PWMIn(int pin)
	{
		in = new DigitalInput(pin);
	}
	
	public float get()
	{
		long start, highEnd, lowEnd;

		while(!in.get());
		start = System.nanoTime();
		while(in.get());
		highEnd = System.nanoTime();
		while(!in.get());
		lowEnd = System.nanoTime();

		return (float) (((double) (highEnd - start)) / (double) (lowEnd - start));
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
		
		return (int) (255 * (highEnd - start) / (lowEnd - start));
	}
}