/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.Util.DriveCommand;
import org.usfirst.frc.team7327.robot.Util.ModuleLocation;

import static org.usfirst.frc.team7327.robot.Robot.kDrivetrain;
import static org.usfirst.frc.team7327.robot.Robot.oi;

public class Drive extends Command {
  
  public Drive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.kDrivetrain);
  }

  // Called just before this Command runs the first time
  int DriveSetting, ElevSetting = 0; 
  @Override
  protected void initialize() { DriveSetting = 0; ElevSetting = 0;
    //DoubleSolenoid.clearAllPCMStickyFaults(0);  
  }

  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, FLwheelRot, FRwheelRot, BLwheelRot, BRwheelRot = 0;
  int rotAngBR = 135, rotAngBL = 45, rotAngFR = -135, rotAngFL = -45;    

  double degreesL, magnitudeL, degreesR, magnitudeR, degreesL2, magnitudeL2, magnitudeR2, setDegree =  0; 
	int heightB0 = 0, heightB1 = 11000, heightB2 = 26000, heightB3 = 37000, heightH2 = 17033, heightH3 = 30973; 

  double throttle = .3, Redthrottle = 0, ballThrottle = 0; 
  double rotatethrottle = .5; 
  double speedthrottle = 1;
  double kP = 0.002; 
  double navFinal = 0; 
  double PIDOutput = 0; 
  double rotMag = 0; 
  double rightArc = 0; 

  double FLwheelMag, FRwheelMag, BLwheelMag, BRwheelMag = 0; 

  boolean simple = false; 
  boolean turnMode = true; 
  
	//DoubleSolenoid.Value Flex = DoubleSolenoid.Value.kOff; 

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

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

    if(oi.AButton(P1)){
      if(rotatethrottle == .5){ rotatethrottle = 1;} 
      else {rotatethrottle = .5;}
    } 

    if(oi.BButton(P1)){ 
      if(speedthrottle ==1) { speedthrottle = .5;} //Comp speed switch
      //else if(speedthrottle ==.5){speedthrottle = .250;} //School time speed. Comment out for competition!
      else{speedthrottle = 1;} 
    }
    //SaraSwitch: go ahead and be super fast or super slow. Your cup of tea.

    if(oi.XButton(P1)){ if(turnMode){ turnMode = false; } else { turnMode = true; } }

    double leftX = oi.getLeftXAxis();
    double leftY = oi.getLeftYAxis();
    double rightX = oi.getRightXAxis(); 
    double rightY = oi.getRightYAxis();
    double leftMag = oi.getLeftMagnitude();  
    double rightMag = oi.getRightMagnitude(); 
    if(rightMag > .7) { rightArc = Math.toDegrees(Math.atan2(rightY, rightX)) + 90; }
    if(turnMode) {
      try { Robot.kDrivetrain.turning.setYaw(rightArc - Robot.NavAngle());}
      catch (Exception e) {}
      rotMag = Robot.kDrivetrain.turning.PIDOutput;
    }
    else{ rotMag = rightX; }
    
    if(leftMag > .3){ finalAngle = Math.toDegrees(Math.atan2(leftX, leftY)) + Robot.NavAngle(); simple = false; }
    if(leftMag < .3 ) { 
      if(oi.RightTrigger(P1) > .1) { leftMag = oi.RightTrigger(P1); simple = true; }
      else if(oi.LeftTrigger(P1) > .1) { leftMag = -oi.LeftTrigger(P1); simple = true;  }
      else{ leftMag = 0; simple = false; }
     }

  if(!simple){
    double wheelXcos = Math.cos(finalAngle/57.2957795) * leftMag;
    double wheelYsin = Math.sin(finalAngle/57.2957795) * leftMag;

    double FLwheelX = wheelXcos + Math.cos(rotAngFL/57.2957795) * -rotMag;
    double FLwheelY = wheelYsin + Math.sin(rotAngFL/57.2957795) * -rotMag;
    if(leftMag > 0.3 || rightMag > 0.2) {FLwheelRot = Math.atan2(FLwheelY, FLwheelX) * 57.2957795;}
    FLwheelMag = Math.hypot(FLwheelX, FLwheelY);
    
    double FRwheelX = wheelXcos + Math.cos(rotAngFR/57.2957795) * -rotMag;
    double FRwheelY = wheelYsin + Math.sin(rotAngFR/57.2957795) * -rotMag;
    if(leftMag > 0.3 || rightMag > 0.2) {FRwheelRot = Math.atan2(FRwheelY, FRwheelX) * 57.2957795;}
    FRwheelMag = Math.hypot(FRwheelX, FRwheelY);
    
    double BLwheelX = wheelXcos + Math.cos(rotAngBL/57.2957795) * -rotMag;
    double BLwheelY = wheelYsin + Math.sin(rotAngBL/57.2957795) * -rotMag;
    if(leftMag > 0.3 || rightMag > 0.2) {BLwheelRot = Math.atan2(BLwheelY, BLwheelX) * 57.2957795;} 
    BLwheelMag = Math.hypot(BLwheelX, BLwheelY);
    
    double BRwheelX = wheelXcos + Math.cos(rotAngBR/57.2957795) * -rotMag;
    double BRwheelY = wheelYsin + Math.sin(rotAngBR/57.2957795) * -rotMag;
    if(leftMag > 0.3 || rightMag > 0.2) {BRwheelRot = Math.atan2(BRwheelY, BRwheelX) * 57.2957795;}
    BRwheelMag = Math.hypot(BRwheelX, BRwheelY);
    
    double max = FLwheelMag;

    if(FRwheelMag > max)      { max = FRwheelMag; }
    else if(BLwheelMag > max) { max = BLwheelMag; }
    else if(BRwheelMag > max) { max = BRwheelMag; }
    if(max > 1){
      FLwheelMag /= max; FRwheelMag /= max;
      BLwheelMag /= max; BRwheelMag /= max;
    }

  } else{
    FLwheelMag = leftMag; FLwheelRot = rightArc + Robot.NavAngle();
    FRwheelMag = leftMag; FRwheelRot = rightArc + Robot.NavAngle();
    BLwheelMag = leftMag; BLwheelRot = rightArc + Robot.NavAngle();
    BRwheelMag = leftMag; BRwheelRot = rightArc + Robot.NavAngle();
  }

    DriveCommand frontLeftCommand = new DriveCommand(FLwheelRot, FLwheelMag * speedthrottle);
    DriveCommand frontRightCommand = new DriveCommand(FRwheelRot, FRwheelMag * -speedthrottle);
    DriveCommand backLeftCommand = new DriveCommand(BLwheelRot, BLwheelMag * speedthrottle);
    DriveCommand backRightCommand = new DriveCommand(BRwheelRot, BRwheelMag * speedthrottle);

    kDrivetrain.setModule(ModuleLocation.FRONT_LEFT, frontLeftCommand);
    kDrivetrain.setModule(ModuleLocation.FRONT_RIGHT, frontRightCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_LEFT, backLeftCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_RIGHT, backRightCommand);


    //7327 CODE BELOW
    if(oi.StartButton(P1)) { Robot.nav.reset(); }
		if(oi.StartButton(P1)) { Robot.nav.reset(); }
		if(oi.StartButton(P2)) { Robot.kDrivetrain.ResetElevator(); }
		
		if(oi.RightBumperDown(P2)) { Redthrottle = -.6; }
		else if(oi.RightBumperDown(P1)) {Redthrottle = -.6; }
		else if(oi.LeftBumperDown(P2)) { Redthrottle = .6;}
		else if(oi.LeftBumperDown(P1)) { Redthrottle = .6;}
		else { Redthrottle = 0; }
		Robot.kDrivetrain.setRawIntake(Redthrottle);

		magnitudeR2 = Math.sqrt(Math.pow(oi.RightStickX(P2), 2) + Math.pow(oi.RightStickY(P2), 2));
		if(magnitudeR2 > .3) { ballThrottle = -.75*oi.RightStickY(P2); }
		else if(oi.RightBumperDown(P2)) { ballThrottle = -.5; }
		else if(oi.RightBumperDown(P1)) { ballThrottle = -.5; }
		else if(oi.LeftBumperDown(P1))  { ballThrottle =  .5; }
		else{ ballThrottle = 0; }
		Robot.kDrivetrain.setRawBallIn(ballThrottle); 
		
		if(oi.Dpad(P2) >= 0 || oi.Dpad(P1) >= 0 || oi.YButtonDown(P2) || oi.XButtonDown(P2)) { 
            if     (oi.DpadDown(P1) || oi.DpadDown(P2) )  { ElevSetting = 1; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.DpadRight(P1)|| oi.DpadRight(P2))  { ElevSetting = 2; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.DpadUp(P1)   || oi.DpadUp(P2)   )  { ElevSetting = 3; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.DpadLeft(P1) || oi.DpadLeft(P2) )  { ElevSetting = 4; Robot.kDrivetrain.ElevOn(true); } 
            else if(oi.YButtonDown(P2)){ ElevSetting = 5; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.XButtonDown(P2)){ ElevSetting = 6; Robot.kDrivetrain.ElevOn(true); }
        }   else{ ElevSetting = 0; Robot.kDrivetrain.ElevOn(false); }

		switch(ElevSetting) {
    case 0: 
      Robot.kDrivetrain.setRawElevator(throttle*(oi.LeftTrigger(P1) - oi.RightTrigger(P1)));
      if((oi.LeftTrigger(P1) < .1) && oi.RightTrigger(P1) < .1){ 
			  Robot.kDrivetrain.setRawElevator(throttle*(oi.LeftTrigger(P2) - oi.RightTrigger(P2)));
      }
			break; 
		case 1: Robot.kDrivetrain.setElevatorPosition(heightB0); break; 
		case 2: Robot.kDrivetrain.setElevatorPosition(heightB1); break; 
		case 3: Robot.kDrivetrain.setElevatorPosition(heightB2); break;
		case 4: Robot.kDrivetrain.setElevatorPosition(heightB3); break; 
		case 6: Robot.kDrivetrain.setElevatorPosition(heightH2); break; 
		case 7: Robot.kDrivetrain.setElevatorPosition(heightH3); break; 
		}


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
