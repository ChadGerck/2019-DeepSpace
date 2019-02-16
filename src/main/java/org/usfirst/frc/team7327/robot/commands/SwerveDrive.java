package org.usfirst.frc.team7327.robot.commands;

import org.usfirst.frc.team7327.robot.Robot;

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
	int ElevSetting = 0; 
	public static XboxController Player1 = Robot.oi.Controller0; 
	public static XboxController Player2 = Robot.oi.Controller1; 
	protected void initialize() { 
		setting = 0; 
		ElevSetting = 0; 
	}

	double degreesL, magnitudeL, degreesR, magnitudeR, setDegree =  0; 
	double throttle = .5; 

	//subtract 1888
	int heightH0 = 0; 
	int heightH1 = 0;
	int heightH2 = 17033;
	int heightH3 = 30973; 
	
	int heightB0 = 0; 
	int heightB1 = 9557;
	int heightB2 = 24141;
	int heightB3 = 35000; 

	double throottle = 0; 
	double ballThrottle = .4; 

	protected void execute(){
		
		if(Robot.oi.getRightBumperDown(Player2) == true) { throottle = .85; }
		else if(Robot.oi.getLeftBumperDown(Player2) == true) { throottle = -.85;}
		else { throottle = 0; }

		Robot.drivetrain.setRawIntake(throottle);
		
		Robot.drivetrain.setRawBallIn(ballThrottle * -Robot.oi.getRightStickY(Player2)); 
		
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

		if(magnitudeL > .5) setDegree = 180-degreesL;
		
		if(Robot.oi.getStartButton(Player1)) Robot.nav.reset();

		if(Robot.oi.getStartButton(Player2)) { Robot.drivetrain.Elevator.ElevatorReset();}
		
		if(Robot.oi.Dpad(Player2) >= 0 || Robot.oi.getAButtonDown(Player2) || Robot.oi.getBButtonDown(Player2) || Robot.oi.getYButtonDown(Player2) || Robot.oi.getXButtonDown(Player2)) { 
			
			if(((Robot.oi.Dpad(Player2) >= 0 && Robot.oi.Dpad(Player2) < 45) || (Robot.oi.Dpad(Player2) >= 315 && Robot.oi.Dpad(Player2) < 360))) { 
				Robot.drivetrain.Elevator.setOn(true); ElevSetting = 3;  }
			else if((Robot.oi.Dpad(Player2) >= 45 && Robot.oi.Dpad(Player2) < 135)) { 
				Robot.drivetrain.Elevator.setOn(true); ElevSetting = 2; }
			else if((Robot.oi.Dpad(Player2) >= 135 && Robot.oi.Dpad(Player2) < 225)) { 
				Robot.drivetrain.Elevator.setOn(true); ElevSetting = 1;}
			else if((Robot.oi.Dpad(Player2) >= 225 && Robot.oi.Dpad(Player2) < 315)) { 
				Robot.drivetrain.Elevator.setOn(true); ElevSetting = 4; } 
			else if((Robot.oi.getBButtonDown(Player2) || Robot.oi.getAButtonDown(Player2))) {
				Robot.drivetrain.Elevator.setOn(true); ElevSetting = 5;}
			else if(Robot.oi.getYButtonDown(Player2)) {
				Robot.drivetrain.Elevator.setOn(true); ElevSetting = 6;}
			else if(Robot.oi.getXButtonDown(Player2)) {
				Robot.drivetrain.Elevator.setOn(true); ElevSetting = 7;}
		}
		else{ Robot.drivetrain.Elevator.setOn(false); ElevSetting = 0;  }

		switch(ElevSetting) {
		case 0: 
			Robot.drivetrain.setRawElevator(throttle*(-Robot.oi.getLeftTrigger(Player2) + Robot.oi.getRightTrigger(Player2)));
			break; 
		case 1: 
			Robot.drivetrain.Elevator.setPosition(heightB0);
			break; 
		case 2: 
			Robot.drivetrain.Elevator.setPosition(heightB1);
			break; 
		case 3: 
			Robot.drivetrain.Elevator.setPosition(heightB2);
			break;
		case 4: 
			Robot.drivetrain.Elevator.setPosition(heightB3);
			break; 
		case 5:
			Robot.drivetrain.Elevator.setPosition(heightH1);
			break;
		case 6: 
			Robot.drivetrain.Elevator.setPosition(heightH2);
			break; 
		case 7: 
			Robot.drivetrain.Elevator.setPosition(heightH3);
			break; 
		}

		
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
		}
	}
	
	protected boolean isFinished() {

		return false;
	}

	protected void interrupted() {
		end();
	}
}