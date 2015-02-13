package org.usfirst.frc.team3464.robot;

// #define CONST public static final
// if only...

public class Config {
	// This is just 2 * PI. Don't change it.
	public static final float TWOPI = (float) (2.0 * Math.PI);
	
	// The number joy stick to use to control the drive train
	public static final int DRIVE_STICK_ID = 0;
	// The number joy stick to use to control the elevator and claw
	public static final int ELEVATOR_STICK_ID = 1;
	
	public static final float STICK_DEADZONE = 0.08f;
	public static final float ROTATION_DEADZONE = 0.25f;
	// The upper limit of the rotation speed
	public static final float ROTATION_SPEED_CAP = 0.25f;
	
	// *** All the arrays in this section are indexed FrontLeft,FrontRight,RearLeft,RearRight
	// An array of the PWMs motors to be used for driving.
	public static final int[] DRIVE_MOTOR_PINS = {0, 1, 2, 3};
	// An array of the PWMs to be used for turning. Must correspond to the
	// drive motor array.
	public static final int[] PIVOT_MOTOR_PINS = {4, 5, 6, 7};
	// The DIO pins to be used for the encoders. Must correspond to the
	// drive motor array.
	public static final int[] ENCODER_PINS = {0, 1, 2, 3};
	public static final int LOWER_LIMIT_SWITCH_PIN = 5;
	public static final int UPPER_LIMIT_SWITCH_PIN = 4;
	public static final int ELEVATOR_MOTOR_PIN = 8;

	// The precision, in radians, to be used when adjusting the angle of the swerve modules.
	public static final float SWERVE_PRECISION = 0.15f;
	// The precision, in radians, at which the Swerve module can be considered approximately calibrated
	public static final float SWERVE_APPROX_PRECISION = 0.5f;
	// The speed to turn the motors on when on carpet

	public static final float SWERVE_PIVOT_SPEED_CARPET = 0.35f;
	// The speed to turn the motors on when on a sleeker floor surface
	public static final float SWERVE_PIVOT_SPEED_FLOOR = 0.25f;

	// The speed to turn the pivot motors at.
	public static final float SWERVE_PIVOT_SPEED = SWERVE_PIVOT_SPEED_FLOOR;
	// The amount of time, in milliseconds, to spin the pivot motors before
	// updating the encoder readings.
	public static final long SWERVE_ADJUSTMENT_PERIOD = 0;

	public static final float ELEVATOR_SPEED_CAP = 0.4f;
	
	// The default mode of operation for the robot.
	public static final RobotMode DEFAULT_MODE = RobotMode.COMPETITION;
	// The swerve module and encoder IDs to use for tests
	public static final int TEST_ID = 2;
}