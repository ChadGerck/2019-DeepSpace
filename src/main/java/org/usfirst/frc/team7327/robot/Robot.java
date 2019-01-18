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
import org.usfirst.frc.team7327.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.I2C; 

public class Robot extends TimedRobot { 
	public static OI oi;
	public static DriveTrain drivetrain;
	public static SwerveDrive swervedrive; 
	//CameraServer Camera;
	
	//public static ADXRS450_Gyro gyro; 


	public static AHRS nav;  
	
	public static Vision vision; 
	
	public static double NWdegree, NEdegree, SWdegree, SEdegree = 0;
	
	public static Timer myTimer = new Timer();
	public static boolean done = true; 
	
	public static I2C arduino; 
	
	@Override
	public void robotInit() {
		myTimer.reset();
		myTimer.start();
		
		//gyro = new ADXRS450_Gyro(Port.kOnboardCS0);

		nav = new AHRS(I2C.Port.kMXP);
				
		oi = new OI();
		drivetrain = new DriveTrain();
		//Camera = CameraServer.getInstance();
		//Camera.startAutomaticCapture();
		//Camera.getVideo();


		
		//gyro.calibrate();
		
		arduino = new I2C(I2C.Port.kOnboard, 0x54);
		
		vision = new Vision();
		
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
		//gyro.reset();
		
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		//gyro.reset();
		nav.reset();
		
	}
	

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
	
	public static void AddressWorking()
	{
		System.out.println(arduino.addressOnly()); 
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
	/*
	public static double GyroAngle() {
		double angle = Robot.gyro.getAngle();
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360;
		return angle; 
	}

	public static double GyroAngle(double add) {
		double angle = Robot.gyro.getAngle() + add;
		while(angle > 360)  angle -= 360; 
		while(angle < 0)    angle += 360; 
		return angle; 
	}
	*/

	
}