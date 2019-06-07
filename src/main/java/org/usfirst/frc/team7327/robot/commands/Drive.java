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
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  protected void initialize() { DriveSetting = 0; ElevSetting = 0; }

  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, FLwheelRot, FRwheelRot, BLwheelRot, BRwheelRot = 0;
  int rotAngBR = 135, rotAngBL = 45, rotAngFR = -135, rotAngFL = -45;    

  double degreesL, magnitudeL, degreesR, magnitudeR, degreesL2, magnitudeL2, magnitudeR2, setDegree =  0; 
	int heightB0 = 0, heightB1 = 11000, heightB2 = 26000, heightB3 = 37000, heightH2 = 18033, heightH3 = 30973; 

  double throttle = .3, Redthrottle = 0, ballThrottle = 0; 
  double speedthrottle = 1;
  double kP = 0.002; 
  double navFinal = 0; 
  double PIDOutput = 0; 
  double rotMag = 0;
  double rightArc = 0; 

  double FLwheelMag, FRwheelMag, BLwheelMag, BRwheelMag = 0; 

  boolean Climb = false;
  boolean simple = false; 
  boolean turnMode = true; 

  double wheelXcos, wheelYsin, FLwheelX, FLwheelY, FRwheelX, FRwheelY, BLwheelX, BLwheelY, BRwheelX, BRwheelY, max = 0;

  double leftX, leftY, rightX, rightY, leftMag, rightMag, directMag = 0; 

  boolean fixRotation = false;

  double SteerP = -0.025;
  double SteerD = 0.4; 
  double SteerP2 = -0.05;
  double SteerD2 = 0.6; 
  double diffError = 0; 
  double lastError, lastError2 = 0; 
  double steering_adjust = 0.0;

  final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
  final double DESIRED_TARGET_AREA = 13.0;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  
    /*
    if(oi.LeftTrigger(P1) > .1) { Robot.server.setSource(Robot.camera2); }
    else{ Robot.server.setSource(Robot.camera1);}
    */

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
		NetworkTableEntry tv = table.getEntry("tv");
		NetworkTableEntry tx = table.getEntry("tx");
		NetworkTableEntry ty = table.getEntry("ty");
		NetworkTableEntry ta = table.getEntry("ta");

    double velocity = 0;
		double target = tv.getDouble(0.0); 
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    
   double heading_error= -x;
    if(leftMag >= 3 && Robot.oi.BButtonDown(P1)) {
      if(heading_error > -4 && heading_error < 4) {
       // double heading_error = -x;
        diffError = lastError2 - heading_error; 
        steering_adjust = SteerP2*heading_error + SteerD2*diffError;
        lastError2 = heading_error; 
      }
      directMag = .5;
    
      }
    if(Robot.oi.AButtonDown(P1)) {
			//double heading_error = -x;
      diffError = lastError - heading_error; 
			steering_adjust = SteerP*heading_error + SteerD*diffError;
      lastError = heading_error; 
    }
    else if(Robot.oi.BButtonDown(P1)) {
			//double heading_error = -x;
      diffError = lastError2 - heading_error; 
			steering_adjust = SteerP2*heading_error + SteerD2*diffError;
      lastError2 = heading_error; 
    }
    else if(Robot.oi.XButtonDown(P1)) {
      if(target == 0){ velocity = 0; }
      else { velocity = (DESIRED_TARGET_AREA - area) * DRIVE_K; }
    }
    else if(Robot.oi.YButtonDown(P1)){
      diffError = lastError2 - heading_error; 
			steering_adjust = SteerP2*heading_error + SteerD2*diffError;
      lastError2 = heading_error; 
    }

    /*
    if(oi.BButton(P1)){ 
      if(speedthrottle ==1) { speedthrottle = .5;} //Comp speed switch
      //else if(speedthrottle ==.5){speedthrottle = .250;} //School time speed. Comment out for competition!
      else{speedthrottle = 1;} 
    }
    */

    //if(oi.XButton(P1)){ if(turnMode){ turnMode = false; } else { turnMode = true; } }

    leftX = oi.getLeftXAxis();
    leftY = oi.getLeftYAxis();
    rightX = oi.getRightXAxis(); 
    rightY = oi.getRightYAxis();
    leftMag = oi.getLeftMagnitude();  
    rightMag = oi.getRightMagnitude(); 
    if(rightMag > .7 ){
      if(rightMag > .7) { rightArc = Math.toDegrees(Math.atan2(rightY, rightX)) + 90; }
      //oi.AButtonDown(P1)) { rightArc = 315; } //Left Close
      //else if(oi.XButtonDown(P1)) { rightArc = 225; } //Left Far
      //else if(oi.YButtonDown(P1)) { rightArc = 135; } //Right Far
      //else if(oi.BButtonDown(P1)) { rightArc = 45; } //Right Close
      try { Robot.kDrivetrain.turning.setYaw(rightArc - Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.kDrivetrain.turning.getPIDOutput();
    }
    else if(oi.AButtonDown(P1)){
      rotMag = steering_adjust;  
    }
    else{
      rotMag = 0; 
    }

    /*
    leftX, steeringAdjust
    finalAngle = leftX 
    */
    
    if( oi.YButtonDown(P1)){
      finalAngle = 0; directMag = steering_adjust;
    }
    else if(leftMag >= .3){ finalAngle = Math.toDegrees(Math.atan2(leftX, leftY)) + Robot.NavAngle();
      directMag = leftMag; 
    }
    else if(oi.RightTrigger(P1) > .1) {finalAngle = 0; directMag = -.5*oi.RightTrigger(P1); }
    else if(oi.LeftTrigger(P1) > .1) {finalAngle = 180; directMag = -oi.LeftTrigger(P1); }
    else if(oi.RightBumperDown(P1)) { finalAngle = 90; directMag = .5; }
    else if(oi.LeftBumperDown(P1)) { finalAngle = 270; directMag = .5; }
    else if(oi.BButtonDown(P1)) { finalAngle = 0; directMag = steering_adjust; }
    else if(oi.XButtonDown(P1)) { finalAngle = 90; directMag = velocity; }
    else { directMag = 0; }

    if(oi.LeftBumperDown(P1) || oi.RightBumperDown(P1) || oi.RightTrigger(P1) > .1 || oi.LeftTrigger(P1) > .1 || leftMag > 0.3 || rightMag > 0.2 || oi.AButtonDown(P1) || oi.BButtonDown(P1) || oi.YButtonDown(P1) || oi.XButtonDown(P1)) {
      fixRotation = true; }
    else{fixRotation = false;}
    wheelXcos = Math.cos(finalAngle/57.2957795) * directMag;
    wheelYsin = Math.sin(finalAngle/57.2957795) * directMag;

    FLwheelX = wheelXcos + Math.cos(rotAngFL/57.2957795) * -rotMag;
    FLwheelY = wheelYsin + Math.sin(rotAngFL/57.2957795) * -rotMag;
    if (fixRotation){ FLwheelRot = Math.atan2(FLwheelY, FLwheelX) * 57.2957795;}
    FLwheelMag = Math.hypot(FLwheelX, FLwheelY);
    
    FRwheelX = wheelXcos + Math.cos(rotAngFR/57.2957795) * -rotMag;
    FRwheelY = wheelYsin + Math.sin(rotAngFR/57.2957795) * -rotMag;
    if(fixRotation) { FRwheelRot = Math.atan2(FRwheelY, FRwheelX) * 57.2957795;}
    FRwheelMag = Math.hypot(FRwheelX, FRwheelY);
    
    BLwheelX = wheelXcos + Math.cos(rotAngBL/57.2957795) * -rotMag;
    BLwheelY = wheelYsin + Math.sin(rotAngBL/57.2957795) * -rotMag;
    if(fixRotation) { BLwheelRot = Math.atan2(BLwheelY, BLwheelX) * 57.2957795;} 
    BLwheelMag = Math.hypot(BLwheelX, BLwheelY);
    
    BRwheelX = wheelXcos + Math.cos(rotAngBR/57.2957795) * -rotMag;
    BRwheelY = wheelYsin + Math.sin(rotAngBR/57.2957795) * -rotMag;
    if(fixRotation) { BRwheelRot = Math.atan2(BRwheelY, BRwheelX) * 57.2957795;}
    BRwheelMag = Math.hypot(BRwheelX, BRwheelY);
    

    SmartDashboard.putNumber("rotMag: ", rotMag); 
    max = FLwheelMag;

    if(FRwheelMag > max)      { max = FRwheelMag; }
    else if(BLwheelMag > max) { max = BLwheelMag; }
    else if(BRwheelMag > max) { max = BRwheelMag; }
    if(max > 1){
      FLwheelMag /= max; FRwheelMag /= max;
      BLwheelMag /= max; BRwheelMag /= max;
    }

    DriveCommand frontLeftCommand = new DriveCommand(FLwheelRot, FLwheelMag * speedthrottle);
    DriveCommand frontRightCommand = new DriveCommand(FRwheelRot, FRwheelMag * speedthrottle);
    DriveCommand backLeftCommand = new DriveCommand(BLwheelRot, BLwheelMag * speedthrottle);
    DriveCommand backRightCommand = new DriveCommand(BRwheelRot, BRwheelMag * speedthrottle);

    kDrivetrain.setModule(ModuleLocation.FRONT_LEFT, frontLeftCommand);
    kDrivetrain.setModule(ModuleLocation.FRONT_RIGHT, frontRightCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_LEFT, backLeftCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_RIGHT, backRightCommand);

		if(oi.StartButton(P1)) { Robot.nav.reset(); }
		if(oi.StartButton(P2)) { Robot.kDrivetrain.ResetElevator(); }
		
		if(oi.RightBumperDown(P2)) { Redthrottle = -.6; }
		else if(oi.LeftBumperDown(P2)) { Redthrottle = .6;}
		else { Redthrottle = 0; }
		Robot.kDrivetrain.setRawIntake(Redthrottle);

		magnitudeR2 = Math.sqrt(Math.pow(oi.RightStickX(P2), 2) + Math.pow(oi.RightStickY(P2), 2));
		if(magnitudeR2 > .3) { ballThrottle = .75*oi.RightStickY(P2); }
		else if(oi.RightBumperDown(P2)) { ballThrottle = .5; }
		else{ ballThrottle = 0; }
		Robot.kDrivetrain.setRawBallIn(ballThrottle); 
		
		if(oi.Dpad(P2) >= 0 || oi.Dpad(P1) >= 0 || oi.YButtonDown(P2) || oi.XButtonDown(P2)) { 
            if     (oi.DpadDown(P2) || oi.DpadDown(P2) )  { ElevSetting = 1; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.DpadRight(P2) || oi.DpadRight(P2))  { ElevSetting = 2; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.DpadUp(P2) || oi.DpadUp(P2)   )  { ElevSetting = 3; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.DpadLeft(P2) || oi.DpadLeft(P2) )  { ElevSetting = 4; Robot.kDrivetrain.ElevOn(true); } 
            else if(oi.YButtonDown(P2)){ ElevSetting = 7; Robot.kDrivetrain.ElevOn(true); }
            else if(oi.XButtonDown(P2)){ ElevSetting = 6; Robot.kDrivetrain.ElevOn(true); }
    } else{ ElevSetting = 0; Robot.kDrivetrain.ElevOn(false); }

		switch(ElevSetting) {
    case 0: 
      Robot.kDrivetrain.setRawElevator(throttle*(-oi.LeftTrigger(P2) + oi.RightTrigger(P2)));
			break; 
		case 1: Robot.kDrivetrain.setElevatorPosition(heightB0); break; 
		case 2: Robot.kDrivetrain.setElevatorPosition(heightB1); break; 
		case 3: Robot.kDrivetrain.setElevatorPosition(heightB2); break;
		case 4: Robot.kDrivetrain.setElevatorPosition(heightB3); break; 
		case 6: if(!Climb) Robot.kDrivetrain.setElevatorPosition(heightH2); break; 
		case 7: if(!Climb) Robot.kDrivetrain.setElevatorPosition(heightH3); break; 
    }

    if(oi.LSClick(P2)) { Climb = true; }
    else if(oi.RSClick(P2)) { Climb = false; }

    if(Climb) {
      if(oi.AButtonDown(P2)){ Robot.kDrivetrain.ClimbN(.4); }
                else if(oi.BButtonDown(P2)){ Robot.kDrivetrain.ClimbN(-.4); } 
                else{Robot.kDrivetrain.ClimbN(0);}      
            if(oi.XButtonDown(P2)){ Robot.kDrivetrain.ClimbS(.4); }
                else if(oi.YButtonDown(P2)){ Robot.kDrivetrain.ClimbS(-.4); }
                else {Robot.kDrivetrain.ClimbS(0);}
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
