package org.usfirst.frc.team3464.robot;

public enum RobotMode {
	ENCODER_TEST,        // Test the encoder connected the first DIO in the
	                     // ENCODER_PINS array
	SWERVE_MODULE_TEST,  // Test the first swerve module in the arrays
	DRIVE_ONLY,          // Enable the drive train code only
	ELEVATOR_ONLY,           // Enable the arms only
	COMPETITION,         // The complete competition code
}