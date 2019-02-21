package org.usfirst.frc.team7327.robot;

import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team7327.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;

public class Robot extends TimedRobot { 
	public static OI oi;
	public static DriveTrain drivetrain;
	public static AHRS nav;  

	Compressor c = new Compressor(0);
	
	@Override
	public void robotInit() {
		nav = new AHRS(I2C.Port.kMXP);
		oi = new OI();
		drivetrain = new DriveTrain();
		c.setClosedLoopControl(true);
	}

	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() { Scheduler.getInstance().run(); }

	@Override
	public void autonomousInit() { }
	
	@Override
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }

	@Override
	public void teleopInit() {
		nav.reset();
		drivetrain.SetElevatorStatus();
		drivetrain.ConfigElevator();
	}
	
	@Override
	public void teleopPeriodic() { Scheduler.getInstance().run(); }

	@Override
	public void testPeriodic() { }

	public static double NavAngle() {
		double angle = Robot.nav.getAngle(); 
		while(angle > 360) { angle -= 360; }
		while(angle < 0)   {angle += 360; }
		return angle; 
	}
	public static double NavAngle(double add) {
		double angle = Robot.nav.getAngle() + add; 
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360;
		return angle; 
	}
	
}