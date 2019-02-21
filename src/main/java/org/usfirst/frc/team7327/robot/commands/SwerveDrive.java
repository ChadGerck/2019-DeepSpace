package org.usfirst.frc.team7327.robot.commands;

import org.usfirst.frc.team7327.robot.OI;
import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.TurnModule;
import org.usfirst.frc.team7327.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SwerveDrive extends Command {
	private static DriveTrain drive;
	private static OI oi; 
	private static TurnModule turning; 
	public SwerveDrive() { requires(drive); }
	
	private static XboxController P1 = oi.Controller0, P2 = oi.Controller1; 
	int DriveSetting, ElevSetting = 0; 
	protected void initialize() { DriveSetting = 0; ElevSetting = 0; DoubleSolenoid.clearAllPCMStickyFaults(0); }

	double degreesL, magnitudeL, degreesR, magnitudeR, magnitudeR2, setDegree =  0; 
	int heightB0 = 0, heightB1 = 11000, heightB2 = 26000, heightB3 = 37000, heightH2 = 17033, heightH3 = 30973; 
	//int heightB1 = 19893; 

	double throttle = .3, throottle = 0, ballThrottle = 0; 

	DoubleSolenoid.Value Flex = DoubleSolenoid.Value.kOff; 

	protected void execute(){
		
		NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
		NetworkTableEntry tx = table.getEntry("tx"), ty = table.getEntry("ty"), ta = table.getEntry("ta");
		double x = tx.getDouble(0.0), y = ty.getDouble(0.0), area = ta.getDouble(0.0);
		
		SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
		SmartDashboard.putNumber("LimelightX", x); SmartDashboard.putNumber("LimelightY", y); SmartDashboard.putNumber("LimelightArea", area);
		SmartDashboard.putNumber("NWab", drive.getAbeNW()); SmartDashboard.putNumber("NEab", drive.getAbeNE()); 
		SmartDashboard.putNumber("SWab", drive.getAbeSW()); SmartDashboard.putNumber("SEab", drive.getAbeSE()); 
		SmartDashboard.putNumber("vel", drive.getLiftVelocity()); SmartDashboard.putNumber("Position: ", drive.getLiftPosition()); 

		if(oi.StartButton(P1)) { Robot.nav.reset(); }
		if(oi.StartButton(P2)) { drive.ResetElevator(); }

		if     (oi.BButton(P2)){ Flex = DoubleSolenoid.Value.kForward; } //Flex
		else if(oi.AButton(P2)){ Flex = DoubleSolenoid.Value.kReverse; } //Release
		else                   { Flex = DoubleSolenoid.Value.kOff;     }
		drive.setRawBicep(Flex); 
		
		if     (oi.RightBumperDown(P1)|| oi.RightBumperDown(P2)) { throottle =  .6; }
		else if(oi.LeftBumperDown(P1) || oi.RightBumperDown(P2) ){ throottle = -.6; }
		else { throottle =   0; }
		drive.setRawIntake(throottle);

		magnitudeR2 = Math.sqrt(Math.pow(oi.RightStickX(P2), 2) + Math.pow(oi.RightStickY(P2), 2));
		if(magnitudeR2 > .3) { ballThrottle = .75*-oi.RightStickY(P2); }
		else if(oi.RightBumperDown(P2) || oi.RightBumperDown(P1)) { ballThrottle = -.5; }
		else if(oi.LeftBumperDown(P1))  { ballThrottle =  .5; }
		else { ballThrottle =   0; }
		drive.setRawBallIn(ballThrottle); 
		
		if(oi.Dpad(P2) >= 0 || oi.Dpad(P1) >= 0 || oi.YButtonDown(P2) || oi.XButtonDown(P2)) { 
			if     (oi.DpadDown(P1) || oi.DpadDown(P2) )  { ElevSetting = 1; drive.ElevOn(true); }
			else if(oi.DpadRight(P1)|| oi.DpadRight(P2))  { ElevSetting = 2; drive.ElevOn(true); }
			else if(oi.DpadUp(P1)   || oi.DpadUp(P2)   )  { ElevSetting = 3; drive.ElevOn(true); }
			else if(oi.DpadLeft(P1) || oi.DpadLeft(P2) )  { ElevSetting = 4; drive.ElevOn(true); } 
			else if(oi.YButtonDown(P2)){ ElevSetting = 5; drive.ElevOn(true); }
			else if(oi.XButtonDown(P2)){ ElevSetting = 6; drive.ElevOn(true); }
		}   else{ ElevSetting = 0; drive.ElevOn(false); }

		switch(ElevSetting) {
		case 0: drive.setRawElevator(throttle*(oi.LeftTrigger(P2) - oi.RightTrigger(P2))); break; 
		case 1: drive.setElevatorPosition(heightB0); break; 
		case 2: drive.setElevatorPosition(heightB1); break; 
		case 3: drive.setElevatorPosition(heightB2); break;
		case 4: drive.setElevatorPosition(heightB3); break; 
		case 5: drive.setElevatorPosition(heightH2); break; 
		case 6: drive.setElevatorPosition(heightH3); break; 
		}

		degreesL = Math.toDegrees(Math.atan2(oi.LeftStickY(P1),  oi.LeftStickX(P1))) + 90;
		magnitudeL = Math.sqrt(Math.pow(oi.LeftStickX(P1), 2) + Math.pow(oi.LeftStickY(P1), 2));
		degreesR = Math.toDegrees(Math.atan2(oi.RightStickY(P1),  oi.RightStickX(P1))) + 90;
		magnitudeR = Math.sqrt(Math.pow(oi.RightStickX(P1), 2) + Math.pow(oi.RightStickY(P1), 2));
		if(magnitudeL > .5) setDegree = 180-degreesL;
		if     (oi.LSClick(P1)){ DriveSetting = 2; turning.setOn(false); } 
		else if(oi.RSClick(P1)){ DriveSetting = 3; turning.setOn(false); }
		
		switch(DriveSetting) {
		case 0:  
			drive.setAllDegrees(setDegree+Robot.NavAngle());
			drive.setAllSpeed(-oi.RightTrigger(P1)+oi.LeftTrigger(P1));
			if(magnitudeR > .5)  { DriveSetting = 1; turning.setOn(true); }
			break; 
		case 1: 
			drive.setEachDegree(225, 315, 135, 45);
			turning.setYaw(degreesR);
			if(magnitudeR <= .5){ DriveSetting = 0; turning.setOn(false); }
			break; 
		case 2: 
			drive.setAllDegrees(Robot.NavAngle());
			drive.setAllSpeed(-oi.RightTrigger(P1)+oi.LeftTrigger(P1));
			if(magnitudeL > .5){ DriveSetting = 0; } else if(magnitudeR > .5){ DriveSetting = 1; turning.setOn(true); }
			break;
		case 3: 
			drive.setAllDegrees(Robot.NavAngle());
			drive.setAllSpeed(-oi.RightTrigger(P1)+oi.LeftTrigger(P1));
			if(magnitudeL > .5){ DriveSetting = 0; } else if(magnitudeR > .5){ DriveSetting = 1; turning.setOn(true); }
			break;
		}
	}
	
	protected boolean isFinished() { return false; }
	protected void interrupted() { end(); }
}