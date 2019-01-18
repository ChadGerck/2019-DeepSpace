package org.usfirst.frc.team7327.robot.commands;

import org.usfirst.frc.team7327.robot.Robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive extends Command {
	public SwerveDrive() {
		requires(Robot.drivetrain); 
	}

	int setting = 0; 
	public static XboxController Player1 = Robot.oi.Controller0; 
	protected void initialize() { 
		setting = 0; 
	}
	double setDegree = 0;
	
	
	protected void execute(){
		
		Robot.vision.testGearPixy();
		
		if(Robot.oi.getYButton(Player1)) {
			Robot.AddressWorking();
		}
		
		SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle());
		SmartDashboard.putNumber("Angular Setpoint", Robot.drivetrain.getSteeringSetpoint());
		SmartDashboard.putNumber("Angular Error", Robot.drivetrain.getSteeringError());
		SmartDashboard.putNumber("Angular Position", Robot.drivetrain.getSteeringPosition());

		SmartDashboard.putNumber("NWab", Robot.drivetrain.abeNW.get()); 
		SmartDashboard.putNumber("NEab", Robot.drivetrain.abeNE.get()); 
		SmartDashboard.putNumber("SWab", Robot.drivetrain.abeSW.get()); 
		SmartDashboard.putNumber("SEab", Robot.drivetrain.abeSE.get()); 
		
		double degreesL = Math.toDegrees(Math.atan2(Robot.oi.getLeftStickY(Player1),  Robot.oi.getLeftStickX(Player1))) + 90;
		double magnitudeL = Math.sqrt(Math.pow(Robot.oi.getLeftStickX(Player1), 2) + Math.pow(Robot.oi.getLeftStickY(Player1), 2));

		double degreesR = Math.toDegrees(Math.atan2(Robot.oi.getRightStickY(Player1),  Robot.oi.getRightStickX(Player1))) + 90;
		double magnitudeR = Math.sqrt(Math.pow(Robot.oi.getRightStickX(Player1), 2) + Math.pow(Robot.oi.getRightStickY(Player1), 2));
		
		if(magnitudeR > .5) setDegree = 360-degreesR;
		
		System.out.println(setting); 
		
		if(Robot.oi.getStartButton(Player1)) Robot.nav.reset();
		
		if(Robot.oi.getAButton(Player1))    { setting = 0; Robot.drivetrain.turning.setOn(false); }
		if(Robot.oi.getLeftBumper(Player1)) { setting = 0; Robot.drivetrain.turning.setOn(false); }
		if(Robot.oi.getRightBumper(Player1)){ setting = 1; Robot.drivetrain.turning.setOn(false); }
		if(Robot.oi.getBButton(Player1))    { setting = 4; Robot.drivetrain.turning.setOn(false); }
		if(Robot.oi.getYButton(Player1))    { setting = 7; Robot.drivetrain.turning.setOn(false); }
		
		if(Robot.oi.Dpad(Player1) >= 0 )    { setting = 2; Robot.drivetrain.turning.setOn(true);  }
		
		
		switch(setting) {
		case 7: //Power Mode
			Robot.drivetrain.setAllDegrees(setDegree+Robot.NavAngle());
			Robot.drivetrain.setAllSpeed(Robot.oi.getLeftStickY(Player1)-Robot.oi.getRightTrigger(Player1)+Robot.oi.getLeftTrigger(Player1));
			break;
		case 1: //Turn Mode
			Robot.drivetrain.setEachDegree(225, 315, 135, 45);
			Robot.drivetrain.setAllSpeed(Robot.oi.getLeftStickX(Player1)+Robot.oi.getRightTrigger(Player1)-Robot.oi.getLeftTrigger(Player1));
			break;
		case 2: //D-Pad in Use
			Robot.drivetrain.setEachDegree(225, 315, 135, 45);
			Robot.drivetrain.turning.setYaw(Robot.oi.Dpad(Player1));
			//if(Robot.oi.Dpad(Player1) == -1) { setting = 0; Robot.drivetrain.turning.setOn(false); }
			break; 
		case 4: //Precision Mode 
			Robot.drivetrain.setAllDegrees(setDegree+Robot.NavAngle());
			Robot.drivetrain.setAllSpeed(-Robot.oi.getRightTrigger(Player1)+Robot.oi.getLeftTrigger(Player1));
			if(magnitudeL > .5) { setting = 5; Robot.drivetrain.turning.setOn(true); }
			break; 
		case 5: 
			Robot.drivetrain.setEachDegree(225, 315, 135, 45);
			Robot.drivetrain.turning.setYaw(degreesL);
			if(magnitudeL <= .5) { setting = 4; Robot.drivetrain.turning.setOn(false); }
			break; 
		case 0:
			driveRadially(Robot.oi.getLeftStickX(Player1), Robot.oi.getLeftStickY(Player1), -Robot.oi.getRightTrigger(Player1)+Robot.oi.getLeftTrigger(Player1)+Robot.oi.getRightStickY(Player1));
			System.out.println("hey"); 
			break; 

		}
		
	}

	
	private double width = 1.0;
	private double length = 1.0;

	public void driveRadially(double centerX, double centerY, double velocity) {
		double NWAngle, SWAngle, NEAngle, SEAngle = 0.0;
		if (velocity > 0) {
			NWAngle = Math.toDegrees(Math.atan2(length/2.0 - centerY, -width/2.0 - centerX)) - 90.0;
			SWAngle = Math.toDegrees(Math.atan2(-length/2.0-centerY, -width/2.0-centerX)) - 90.0;
			NEAngle = Math.toDegrees(Math.atan2(length/2.0-centerY, width/2.0-centerX)) - 90.0;
			SEAngle = Math.toDegrees(Math.atan2(-length/2.0-centerY, width/2.0-centerX)) - 90.0;
		} else {
			NWAngle = Math.toDegrees(Math.atan2(length/2.0 - centerY, -width/2.0 - centerX)) + 90.0;
			SWAngle = Math.toDegrees(Math.atan2(-length/2.0-centerY, -width/2.0-centerX)) + 90.0;
			NEAngle = Math.toDegrees(Math.atan2(length/2.0-centerY, width/2.0-centerX)) + 90.0;
			SEAngle = Math.toDegrees(Math.atan2(-length/2.0-centerY, width/2.0-centerX)) + 90.0;
		}
		if (NWAngle > 180) NWAngle -= 360;
		if (SWAngle > 180) SWAngle -= 360;
		if (NEAngle > 180) NEAngle -= 360;
		if (SEAngle > 180) SEAngle -= 360;
		
		if (NWAngle < -180) NWAngle += 360;
		if (SWAngle < -180) SWAngle += 360;
		if (NEAngle < -180) NEAngle += 360;
		if (SEAngle < -180) SEAngle += 360;
		
		double NWMag, SWMag, NEMag, SEMag, MaxMag = 0.0;
		NEMag = Math.sqrt(Math.pow(length/2.0-centerY, 2) + Math.pow(width/2.0-centerX, 2));
		MaxMag = NEMag;
		NWMag = Math.sqrt(Math.pow(length/2.0-centerY,2) + Math.pow(-width/2.0-centerX, 2));
		if (NWMag > MaxMag) MaxMag = NWMag;
		SEMag = Math.sqrt(Math.pow(-length/2.0-centerY, 2) + Math.pow(width/2.0-centerX, 2));
		if (SEMag > MaxMag) MaxMag = SEMag;
		SWMag = Math.sqrt(Math.pow(-length/2.0-centerY, 2) + Math.pow(-width/2.0-centerX,2));
		if (SWMag > MaxMag) MaxMag = SWMag;

		NEMag = NEMag / MaxMag * Math.abs(velocity);
		NWMag = NWMag / MaxMag * Math.abs(velocity);
		SEMag = SEMag / MaxMag * Math.abs(velocity);
		SWMag = SWMag / MaxMag * Math.abs(velocity);

		Robot.drivetrain.setNWWheel(NWAngle, NWMag);
		Robot.drivetrain.setNEWheel(NEAngle, NWMag);
		Robot.drivetrain.setSWWheel(SWAngle, NWMag);
		Robot.drivetrain.setSEWheel(SEAngle, NWMag);
		
	}

	
	protected boolean isFinished() {

		return false;
	}

	protected void interrupted() {
		end();
	}
}
