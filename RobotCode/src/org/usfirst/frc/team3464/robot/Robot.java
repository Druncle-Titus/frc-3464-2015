
package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
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

    public Robot() {
    	Talon[] driveMotors = new Talon[4];
    	Talon[] pivotMotors = new Talon[4];
    	MA3Encoder[] encoders = new MA3Encoder[4];
    	for (int i = 0; i < 4; ++i) {
    		driveMotors[i] = new Talon(DRIVE_MOTOR_PINS[i]);
    		pivotMotors[i] = new Talon(PIVOT_MOTOR_PINS[i]);
    		encoders[i] = new MA3Encoder(SENSOR_PINS[i]);
    	}
        //robotDrive = new SwerveDrive(driveMotors, pivotMotors, encoders);
        driveStick = new Joystick(DRIVE_STICK_ID);
        testEnc = encoders[0];
        testModule = new SwerveModule(driveMotors[0],pivotMotors[0],encoders[0]);
    }


    public void autonomous() {
    }
    
    // Get a reading from the encoder and display it on the Smart Dashboard
    public void testEncoder() {	
    	SmartDashboard.putString("DB/String 1",
    			"Encoder 0 reading: " + testEnc.getAngle());
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
    	testModule.updateDirection();
    }

    // Drive the robot using input from the drive stick
    public void driveRobot() {
    	robotDrive.drive((float)driveStick.getMagnitude(),
    			(float)driveStick.getDirectionRadians());
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
    			driveRobot();
    			break;
    		case ARMS_ONLY:
    			break;
    		case COMPETITION:
    			break;
    		}
    		try {
    			Thread.sleep(5);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    }

    public void test() {
    }
}
