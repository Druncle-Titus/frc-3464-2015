package org.usfirst.frc.team3464.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.DigitalInput;

public class MA3Encoder extends DigitalInput {
	public static final int TRIAL_COUNT = 5;

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

	public float getMedAngle()
	{
		float trials[] = new float[TRIAL_COUNT];
		for (int i = 0; i < TRIAL_COUNT; ++i)
			trials[i] = this.getAngle();
		Arrays.sort(trials);
		return trials[TRIAL_COUNT / 2];
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