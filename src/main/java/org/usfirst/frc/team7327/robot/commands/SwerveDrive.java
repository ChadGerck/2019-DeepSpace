package org.usfirst.frc.team7327.robot.commands;


import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SwerveDrive extends Command {

	public SwerveDrive() {
		requires(Robot.drivetrain); 
	}

	int setting = 0; 
	public static XboxController Player1 = Robot.oi.Controller0; 
	public static XboxController Player2 = Robot.oi.Controller1; 
	protected void initialize() { 
		setting = 0; 
	}

	double degreesL, magnitudeL, degreesR, magnitudeR, setDegree =  0; 
	double throttle = .75; 

	

	int heightH0 = 2; 
	int heightH1 = 2;
	int heightH2 = 4;
	int heightH3 = 6; 
	
	int heightB0 = 0; 
	int heightB1 = 3;
	int heightB2 = 5;
	int heightB3 = 7; 
	public static boolean flag= true;
	public static boolean kill=false; 
	boolean Dpressed = false; 

	protected void execute(){
		

		
		if(flag) { Robot.drivetrain.setRawElevator(throttle*(Robot.oi.getLeftTrigger(Player2) - Robot.oi.getRightTrigger(Player2))); }

		if((Robot.oi.getBButton(Player2) || Robot.oi.getAButton(Player2) && flag)) {
			Robot.gotoPosition(heightH1);
			flag = false; 
		}
		else if(Robot.oi.getYButton(Player2)&& flag) {
			Robot.gotoPosition(heightH2);
			flag = false; 
		}
		else if(Robot.oi.getXButton(Player2) && flag) {
			Robot.gotoPosition(heightH3);
			flag = false; 
		}
	 
		if(Robot.oi.Dpad(Player2) >= 0) { Dpressed = true; }else{ Dpressed = false; }
		
		if(((Robot.oi.Dpad(Player2) >= 0 && Robot.oi.Dpad(Player2) < 45) || (Robot.oi.Dpad(Player2) >= 315 && Robot.oi.Dpad(Player2) < 360) )&& flag) { 
			Robot.gotoPosition(heightB2); flag = false; }
		else if((Robot.oi.Dpad(Player2) >= 45 && Robot.oi.Dpad(Player2) < 135)&&flag) { Robot.gotoPosition(heightB1);  flag = false; }
		else if((Robot.oi.Dpad(Player2) >= 135 && Robot.oi.Dpad(Player2) < 225)&&flag) { Robot.gotoPosition(heightB0); flag = false; }
		else if((Robot.oi.Dpad(Player2) >= 225 && Robot.oi.Dpad(Player2) < 315)&&flag) { Robot.gotoPosition(heightB3); flag = false; }

		if(Robot.oi.getBackButton(Player2)){ kill = true; }
		

		SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
		
		degreesL = Math.toDegrees(Math.atan2(Robot.oi.getLeftStickY(Player1),  Robot.oi.getLeftStickX(Player1))) + 90;
		magnitudeL = Math.sqrt(Math.pow(Robot.oi.getLeftStickX(Player1), 2) + Math.pow(Robot.oi.getLeftStickY(Player1), 2));

		degreesR = Math.toDegrees(Math.atan2(Robot.oi.getRightStickY(Player1),  Robot.oi.getRightStickX(Player1))) + 90;
		magnitudeR = Math.sqrt(Math.pow(Robot.oi.getRightStickX(Player1), 2) + Math.pow(Robot.oi.getRightStickY(Player1), 2));
		
		NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
		NetworkTableEntry tx = table.getEntry("tx");
		NetworkTableEntry ty = table.getEntry("ty");
		NetworkTableEntry ta = table.getEntry("ta");

		//read values periodically
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
		double area = ta.getDouble(0.0);

		//post to smart dashboard periodically
		SmartDashboard.putNumber("LimelightX", x);
		SmartDashboard.putNumber("LimelightY", y);
		SmartDashboard.putNumber("LimelightArea", area);


		SmartDashboard.putNumber("NWab", Robot.drivetrain.getAbeNW()); 
		SmartDashboard.putNumber("NEab", Robot.drivetrain.getAbeNE()); 
		SmartDashboard.putNumber("SWab", Robot.drivetrain.getAbeSW()); 
		SmartDashboard.putNumber("SEab", Robot.drivetrain.getAbeSE()); 

		SmartDashboard.putNumber("test", Robot.drivetrain.getAbeNW()); 

		if(magnitudeL > .5) setDegree = 360-degreesL;
		
		if(Robot.oi.getStartButton(Player1)) Robot.nav.reset();

		Robot.drivetrain.setRawElevator(throttle*(Robot.oi.getLeftTrigger(Player2) - Robot.oi.getRightTrigger(Player2)));
		
		switch(setting) {
		case 0: //Precision Mode 
			Robot.drivetrain.setAllDegrees(setDegree+Robot.NavAngle());
			Robot.drivetrain.setAllSpeed(-Robot.oi.getRightTrigger(Player1)+Robot.oi.getLeftTrigger(Player1));
			if(magnitudeR > .5) { setting = 1; Robot.drivetrain.turning.setOn(true); }
			break; 
		case 1: 
			Robot.drivetrain.setEachDegree(225, 315, 135, 45);
			Robot.drivetrain.turning.setYaw(degreesR);
			if(magnitudeR <= .5) { setting = 0; Robot.drivetrain.turning.setOn(false); }
			break; 
		case 2: 
			break; 
		}
	}
	
	protected boolean isFinished() {

		return false;
	}

	protected void interrupted() {
		end();
	}
}