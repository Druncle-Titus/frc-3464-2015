package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class SwerveDrive {
	private SwerveModule[] modules;
	private float speed, angle;
	private boolean rotationMode = false;

	public SwerveDrive(SpeedController[] drives, SpeedController[] pivots, MA3Encoder[] sensors)
	{
		int len = drives.length < pivots.length
				? (drives.length < sensors.length ? drives.length : sensors.length)
				: (pivots.length < sensors.length ? pivots.length : sensors.length);

		this.modules = new SwerveModule[len]; 
		for (int i = 0; i < 4; ++i)
			this.modules[i] = new SwerveModule(drives[i],pivots[i],sensors[i]);
	}
	
	public void setAngle(float angle)
	{
		this.angle = angle;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	// Update each of the swerve modules.
	private void update()
	{
		boolean calibrated = true;
		boolean approxCalibrated = true;

		if (rotationMode) {
			modules[0].setAngle((float) (0.25 * Math.PI));
			modules[1].setAngle((float) (0.75 * Math.PI));
			modules[2].setAngle((float) (1.75 * Math.PI));
			modules[3].setAngle((float) (1.25 * Math.PI));
		}
		else
			for (SwerveModule m : modules) {
				m.setAngle(this.angle);
			}
		for (SwerveModule m : modules) {
			m.updateDirection();
		}
		for (SwerveModule m : modules) {
			if (!m.isCalibrated()) {
				calibrated = false;
				break;
			}
		}
		for (SwerveModule m : modules) {
			if (!m.isApproxCalibrated()) {
				approxCalibrated = false;
				break;
			}
		}
		// map((SwerveModule m) -> { m.setAngle; m.updateDirection; },
		//     swerveModules);
		// calibrated = every((SwerveModule m) -> { return m.isCalibrated; },
		//                    swerveModules);

		if (calibrated)
			for (SwerveModule m : modules)
				m.setSpeed(speed);
		else if (approxCalibrated)
			for (SwerveModule m : modules)
				m.setSpeed(speed / 2);
		else
			for (SwerveModule m : modules)
				m.setSpeed(0.0f);
	}

	// Rotate the robot around its base
	public void rotate(float speed)
	{
		rotationMode = true;
		this.speed = speed;
		this.update();
	}

	// Drive the robot. This must be called every few milliseconds or else it WILL
	// NOT WORK.
	public void drive(float speed, float angle)
	{
		rotationMode = false;
		this.setSpeed(speed);
		this.setAngle(angle);
		this.update();
	}
	
	// Zero every swerve module in the drive
	public void zero()
	{
		for (SwerveModule m : modules)
			m.zero();
	}
}