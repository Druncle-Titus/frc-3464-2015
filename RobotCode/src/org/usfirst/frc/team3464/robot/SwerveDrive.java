package org.usfirst.frc.team3464.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class SwerveDrive {
	private SwerveModule[] modules;
	private float speed, angle;

	public SwerveDrive(SpeedController[] drives, SpeedController[] pivots, int[] sensors)
	{
		int len = drives.length < pivots.length
				? (drives.length < sensors.length ? drives.length : sensors.length)
				: (pivots.length < sensors.length ? pivots.length : sensors.length);

		this.modules = new SwerveModule[len];
		for (int i = 0; i < len; ++i)
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
		for (SwerveModule m : modules) {
			m.setAngle(this.angle);
			m.updateDirection();
			calibrated = calibrated && m.isCalibrated();
		}
		// map((SwerveModule m) -> { m.setAngle; m.updateDirection; },
		//     swerveModules);
		// calibrated = every((SwerveModule m) -> { return m.isCalibrated; },
		//                    swerveModules);

		if (calibrated)
			for (SwerveModule m : modules)
				m.setSpeed(speed);
		else
			for (SwerveModule m : modules)
				m.setSpeed(0.0);
	}
	
	// Drive the robot. This must be called every few milliseconds or else it WILL
	// NOT WORK.
	public void drive(float speed, float angle)
	{
		this.setSpeed(speed);
		this.setAngle(angle);
		this.update();
	}
}