/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7327.robot;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team7327.robot.commands.SwerveDrive;
import org.usfirst.frc.team7327.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;


public class Robot extends TimedRobot { 
	public static OI oi;
	public static DriveTrain drivetrain;
	public static SwerveDrive swervedrive; 

	public static AHRS nav;  

	//Compressor c = new Compressor(0);
	
	public static double NWdegree, NEdegree, SWdegree, SEdegree = 0;
	
	public static Timer myTimer = new Timer();
	public static boolean done = true; 
	
	PlotThread _plotThread;
	
	@Override
	public void robotInit() {
		myTimer.reset();
		myTimer.start();

		nav = new AHRS(I2C.Port.kMXP);
				
		oi = new OI();
		drivetrain = new DriveTrain();

		//c.setClosedLoopControl(true);

	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		myTimer.reset();
		myTimer.start();
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		nav.reset();

		drivetrain.SetElevatorStatus();
		drivetrain.ConfigElevator();
		
		/* Fire the plotter */
		_plotThread = new PlotThread(this);
		new Thread(_plotThread).start();
		
	}
	

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}

	public static double NavAngle() {
		double angle = Robot.nav.getAngle(); 
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360;
		return angle; 
	}
	public static double NavAngle(double add) {
		double angle = Robot.nav.getAngle(); 
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360;
		return angle; 
	}

	class PlotThread implements Runnable {
		Robot robot;

		public PlotThread(Robot robot) {
			this.robot = robot;
		}

		public void run() {
			/**
			 * Speed up network tables, this is a test project so eat up all of
			 * the network possible for the purpose of this test.
			 */

			while (true) {
				/* Yield for a Ms or so - this is not meant to be accurate */
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					/* Do Nothing */
				}

				/* Grab the latest signal update from our 1ms frame update */
				SmartDashboard.putNumber("vel", drivetrain.getLiftVelocity());
				SmartDashboard.putNumber("Position: ", drivetrain.getLiftPosition()); 
			}
		}
	}
	
}