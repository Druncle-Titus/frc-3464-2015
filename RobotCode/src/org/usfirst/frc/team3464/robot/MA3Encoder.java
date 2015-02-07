package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class MA3Encoder extends DigitalInput {
	public static final int TRIAL_COUNT = 15;

	public MA3Encoder(int pin)
	{
		super(pin);
		this.requestInterrupts();
		this.setUpSourceEdge(true,true);

	}
	
	public float getAngle()
	{
		return Config.TWOPI * getRaw();
	}
	
	public float getAngleDegrees()
	{
		return 360 * getRaw();
	}

	private float getRaw()
	{
		this.waitForInterrupt(15);
		this.waitForInterrupt(15);
		double rising = this.readRisingTimestamp();
		double falling = this.readFallingTimestamp();
		return (float) (rising > falling ? (rising - falling) / 0.001026 :
			1 - (falling - rising) / 0.001026);
	}
}