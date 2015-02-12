package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static org.usfirst.frc.team3464.robot.Config.*;
import static org.usfirst.frc.team3464.robot.RobotMode.*;

public class Robot extends SampleRobot {
    SwerveDrive robotDrive;
    Joystick driveStick;
    RobotMode mode = DEFAULT_MODE;
    MA3Encoder testEnc;
    SwerveModule testModule;
    
    float lastDriveAngle = 0;

    public Robot() {
    	TalonSRX[] driveMotors = new TalonSRX[DRIVE_MOTOR_PINS.length];
    	Talon[] pivotMotors = new Talon[PIVOT_MOTOR_PINS.length];
    	MA3Encoder[] encoders = new MA3Encoder[ENCODER_PINS.length];
    	for (int i = 0; i < DRIVE_MOTOR_PINS.length; ++i) {
    		driveMotors[i] = new TalonSRX(DRIVE_MOTOR_PINS[i]);
    		pivotMotors[i] = new Talon(PIVOT_MOTOR_PINS[i]);
    		encoders[i] = new MA3Encoder(ENCODER_PINS[i]);
    	}

        robotDrive = new SwerveDrive(driveMotors, pivotMotors, encoders);
        driveStick = new Joystick(DRIVE_STICK_ID);
        testEnc = encoders[3];
        testModule = new SwerveModule(driveMotors[3],pivotMotors[3],encoders[3]);
    }


    public void autonomous() {
    }
    
    // Get a reading from the encoder and display it on the Smart Dashboard
    public void testEncoder() {	
    	SmartDashboard.putString("DB/String 1",
    			"Encoder 0 reading: " + testEnc.getAngleDegrees());
    }
    
    // Control a swerve module with a joystick
    public void testSwerveModule() {
    	testEncoder();
    	float angle = (float)driveStick.getDirectionRadians();
    	SmartDashboard.putString("DB/String 2", "Drivestick angle: " + angle);
    	if (driveStick.getRawButton(1)) 
    	{
    		testModule.setAngle(angle);
    	}
		SmartDashboard.putString("DB/String 3", "Module 0 target: " + testModule.getAngle());
		SmartDashboard.putString("DB/String 4", "Module 0 angle: " + 180 * testModule.getActualAngle() / Math.PI);
		SmartDashboard.putString("DB/String 5", "Module 0 calib: "  + testModule.isCalibrated());
		testModule.updateDirection();
    }

    // Drive the robot using input from the drive stick
    public void driveRobot() {
    	float speed = (float) driveStick.getMagnitude();
    	float angle = (float) driveStick.getDirectionRadians();
    	if (speed > DRIVE_DEADZONE) {
    		robotDrive.drive(speed - DRIVE_DEADZONE / (1.0f - DRIVE_DEADZONE), angle);
    		lastDriveAngle = angle;
    	}
    	else
    		robotDrive.drive(0.0f, lastDriveAngle);
    }
    
    public void operatorControl() {
    	// Select the appropriate operation mode
    	while (isOperatorControl() && isEnabled()) {
    		switch (mode) {
    		case ENCODER_TEST:
    			testEncoder();
    			break;
    		case SWERVE_MODULE_TEST:
    			testSwerveModule();
    			break;
    		case DRIVE_ONLY:
    			testEncoder();
    			driveRobot();
    			break;
    		case ARMS_ONLY:
    			break;
    		case COMPETITION:
    			break;
    		}
    		try {
    			Thread.sleep(10);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    }

    public void test() {
    	
    }
}
