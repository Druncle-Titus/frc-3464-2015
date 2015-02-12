package org.usfirst.frc.team3464.robot;

// #define CONST public static final
// if only...

public class Config {
	// This is just 2 * PI. Don't change it.
	public static final float TWOPI = (float) (2.0 * Math.PI);
	
	// The number joy stick to use to control the drive train
	public static final int DRIVE_STICK_ID = 0;
	// The number joy stick to use to control the elevator and claw
	public static final int ARM_STICK_ID = 1;
	
	public static final float DRIVE_DEADZONE = 0.20f;
	
	// An array of the PWMs motors to be used for driving
	public static final int[] DRIVE_MOTOR_PINS = {0, 1, 2, 3};
	// An array of the PWMs to be used for turning. Must correspond to the
	// drive motor array.
	public static final int[] PIVOT_MOTOR_PINS = {4, 5, 6, 7};
	// The DIO pins to be used for the encoders. Must correspond to the
	// drive motor array.
	public static final int[] ENCODER_PINS = {0, 1, 2, 3};
	
	// The precision, in radians, to be used when adjusting the angle of the swerve modules.
	public static final float SWERVE_PRECISION = 0.25f;
	// The speed to turn the pivot motors at.
	public static final float SWERVE_PIVOT_SPEED = 0.5f;
	// The amount of time, in milliseconds, to spin the pivot motors before
	// updating the encoder readings.
	public static final long SWERVE_ADJUSTMENT_PERIOD = 10;
	
	// The default mode of operation for the robot.
	public static final RobotMode DEFAULT_MODE = RobotMode.DRIVE_ONLY;
}