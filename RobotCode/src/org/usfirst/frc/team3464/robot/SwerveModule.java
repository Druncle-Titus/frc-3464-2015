package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.SpeedController;

import static org.usfirst.frc.team3464.robot.Config.*;

public class SwerveModule {
	// The number of times the encoder gear will spin for one rotation of the
	// entire module.
	public static final float ENC_TO_WHEEL_RATIO = 4.0f  / 1.2f;
	
	// The absolute encoder
	private MA3Encoder enc;
	// The drive motor drives. The pivot motor turns.
	private SpeedController drive, pivot;

	// Since the encoder spins multiple times for each rotation of the module, we
	// need to count how many times it goes all the way around. This variable keeps
	// track of that count.
	private int rotationCount = 0;
	// The last angle read from the encoder. This lets us know when it wraps around.
	private float prevEncAngle;

	// The position the encoder starts at, which is assumed to be 0 degrees.
	private float encoderZero;
	// The target angle is the direction that we want to pointing at.
	private float targetAngle = 0;
	// The speed to turn the pivot motor at.
	private float pivotSpeed = 0;
	// Whether we are pointing at the right direction or not.
	private boolean calibrated = true;
	// Whether we are approximately disabled
	private boolean approxCalibrated = true;

	// Initialize a new SwerveModule.
	// IMPORTANT NOTE: When the pivot motor has a positive speed, the value on the
	// absolute encoder should be increasing.
	public SwerveModule(SpeedController drive,
			SpeedController pivot, MA3Encoder encoder)
	{
		this.drive = drive;
		this.pivot = pivot;
		enc = encoder;
		this.zero();
	}

	// Calibrate the sensors with the current value as zero degrees
	public void zero()
	{
		rotationCount = 0;
		targetAngle = 0;
		pivotSpeed = 0;
		calibrated = true;
		approxCalibrated = true;
		encoderZero = enc.getAngle();
		prevEncAngle = encoderZero;
	}

	// Compute the actual angle that the pivot is pointed at.
	public float getActualAngle()
	{
		// First, see if the encoder has wrapped around.
		float curEncAngle = enc.getMedAngle();
		float delta = Math.abs(curEncAngle - prevEncAngle);
		float deltaWrapped = TWOPI - delta;
		if (deltaWrapped < delta)
			if (prevEncAngle > curEncAngle)
				++rotationCount;
			else
				--rotationCount;

		// Update the previous encoder reading.
		prevEncAngle = curEncAngle;

		// Calculate the angle given by the current absolute encoder reading
		float encFrac = (curEncAngle - encoderZero) / ENC_TO_WHEEL_RATIO;
		// Calculate the angle given by the number of cached rotations
		float rotFrac = rotationCount * TWOPI / ENC_TO_WHEEL_RATIO;
		float angle = encFrac + rotFrac;

		// Ensure that the angle is positive and between 0 and 2 * PI
		while (angle < 0) angle += TWOPI;
		while (angle > TWOPI) angle -= TWOPI;

		return angle;
	}

	// Get the angle that the module is set to point at.
	public float getAngle()
	{
		return targetAngle;
	}

	// Set the new target angle. Note: this does not actually get the wheels to
	// pivot. It only makes it so that the updateDirection() method will actually
	// do something.
	public void setAngle(float angle)
	{
		// Ensure that the angle is positive and between 0 and 2 * PI
		while (angle < 0) angle += TWOPI;
		while (angle > TWOPI) angle -= TWOPI;
		this.targetAngle = angle;
		updateCalibration();
	}

	// Check whether the actual angle matches the current angle and update the
	// calibrated variable.
	private void updateCalibration()
	{
		float diff = Math.abs(targetAngle - getActualAngle());
		this.calibrated = diff < SWERVE_PRECISION || TWOPI - diff < SWERVE_PRECISION;
		this.approxCalibrated = diff < SWERVE_APPROX_PRECISION || TWOPI - diff < SWERVE_APPROX_PRECISION;
	}

	// Decide the speed to turn the pivot motor at.
	private void decidePivotSpeed()
	{
		// Get the difference between the current angle and the target angle.
		float diff = targetAngle - this.getActualAngle();
		// Ensure that the difference angle is positive.
		while (diff < 0) diff += TWOPI;

		// If we the difference is less than Pi, we set the speed to positive so
		// that we turn with the angle increasing. Otherwise, set it to negative.
		pivotSpeed = diff < Math.PI ? SWERVE_PIVOT_SPEED : -SWERVE_PIVOT_SPEED;
	}

	// Tell if the module is calibrated-- if the pivot motor is pointing in the
	// right direction.
	public boolean isCalibrated()
	{
		return calibrated;
	}
	
	// Tell if the module is approximately calibrated-- that is, if it is close enough to start driving a bit
	public boolean isApproxCalibrated()
	{
		return approxCalibrated;
	}

	// Decide whether the pivot motor needs to be on or not, and start it
	// if it does.
	public void updateDirection()
	{
		// Check if we're already pointed in the right direction.
		updateCalibration();
		if (calibrated) {
			// If we are, then make sure the pivot is stopped and return
			pivot.set(0.0);
			return;
		}

		// Otherwise, make sure the pivot motor is spinning, and in the right
		// direction.
		decidePivotSpeed();
		pivot.set(pivotSpeed);
	}

	// Set the speed of the drive motor.
	public void setSpeed(double speed)
	{
		drive.set(speed);
	}
	
	// Get the speed of the drive motor.
	public double getSpeed()
	{
		return drive.get();
	}
}
