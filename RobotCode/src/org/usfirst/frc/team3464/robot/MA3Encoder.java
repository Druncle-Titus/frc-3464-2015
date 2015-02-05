package org.usfirst.frc.team3464.robot;

public class MA3Encoder {
	public static final int TRIAL_COUNT = 15;
	private PWMIn in;

	public MA3Encoder(int pin)
	{
		in = new PWMIn(pin);
	}
	
	public float getAngle()
	{
		return Config.TWOPI * this.getRaw() / 255;
	}
	
	public float getAngleDegrees()
	{
		return 360 * getRaw() / 250;
	}
	
	private int getRaw()
	{
		int[] trials = new int[TRIAL_COUNT];
		for (int i = 0; i < TRIAL_COUNT; ++i)
			trials[i] = in.getInt();
		
		int avg = 0;
		for (int i : trials) avg += i;
		avg /= TRIAL_COUNT;
		
		int closest = trials[0], closestDistance = (int) Math.abs(trials[0] - avg);
		int distance;
		for (int i = 1; i < TRIAL_COUNT; ++i) {
			distance = Math.abs(trials[i] - avg);
			if (distance < closestDistance) {
				closestDistance = distance;
				closest = trials[i];
			}
		}
		
		return closest;
	}
}