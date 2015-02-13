package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.DigitalInput;
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
    Joystick driveStick, elevatorStick;
    DigitalInput upperSwitch, lowerSwitch;
    RobotMode mode = DEFAULT_MODE;
    MA3Encoder testEnc;
    SwerveModule testModule;
    Talon elevator;
    
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
        elevatorStick = new Joystick(ELEVATOR_STICK_ID);
        testEnc = encoders[TEST_ID];
        testModule = new SwerveModule(driveMotors[TEST_ID],pivotMotors[TEST_ID],encoders[TEST_ID]);

        elevator = new Talon(ELEVATOR_MOTOR_PIN);
        upperSwitch = new DigitalInput(UPPER_LIMIT_SWITCH_PIN);
        lowerSwitch = new DigitalInput(LOWER_LIMIT_SWITCH_PIN);
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
    	float rotation = (float) driveStick.getZ();
    	float speed = (float) driveStick.getMagnitude();
    	float angle = (float) driveStick.getDirectionRadians();
    	if (rotation > ROTATION_DEADZONE) {
    		rotation = ROTATION_SPEED_CAP * (rotation - ROTATION_DEADZONE) / (1.0f - ROTATION_DEADZONE);
    		robotDrive.rotate(rotation);
    	} else if (rotation < -ROTATION_DEADZONE) {
    		rotation = ROTATION_SPEED_CAP * (rotation + ROTATION_DEADZONE) / (1.0f - ROTATION_DEADZONE);
    		robotDrive.rotate(rotation);
    	} else if (speed > ROTATION_DEADZONE) {
    		speed = (speed > 0 ?
    				(speed - ROTATION_DEADZONE) :
    				(speed + ROTATION_DEADZONE)) / (1.0f - ROTATION_DEADZONE);
    		robotDrive.drive(speed, angle);
    		lastDriveAngle = angle;
    	}
    	else
    		robotDrive.drive(0.0f, lastDriveAngle);
    }
    
    // Drive the elevator using input from the elevator stick
    public void driveElevator() {
    	float speed = (float) elevatorStick.getY();
    	if (Math.abs(speed) > STICK_DEADZONE) {
    		if (speed < 0 && !lowerSwitch.get())
    			speed = (speed + STICK_DEADZONE) / (1.0f - STICK_DEADZONE);
    		else if (speed > 0 && !upperSwitch.get())
    			speed = (speed - STICK_DEADZONE) / (1.0f - STICK_DEADZONE);
    		else speed = 0;
    		elevator.set(speed);
    	}
    	else elevator.set(0.0);
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
    		case ELEVATOR_ONLY:
    			driveElevator();
    			break;
    		case COMPETITION:
    			testEncoder();
    			driveRobot();
    			driveElevator();
    			break;
    		}
    		try {
    			Thread.sleep(10);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    }

    // Use test mode to recalibrate the wheels
    public void test() {
        lastDriveAngle = 0;
    	robotDrive.zero();
    }
}
