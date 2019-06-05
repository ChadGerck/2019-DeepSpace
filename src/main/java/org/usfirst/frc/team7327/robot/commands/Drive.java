/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

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


	
  DoubleSolenoid.Value Pincher = DoubleSolenoid.Value.kOff; 
  DoubleSolenoid.Value Extendor = DoubleSolenoid.Value.kOff; 
  // Called just before this Command runs the first time
  int DriveSetting, ElevSetting = 0; 
  @Override
  protected void initialize() { DriveSetting = 0; ElevSetting = 0; }

  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, FLwheelRot, BLwheelRot, BRwheelRot = 0;
  int rotAngBR = 135, rotAngBL = 45, rotAngFL = -45;    

  double degreesL, magnitudeL, degreesR, magnitudeR, degreesL2, magnitudeL2, magnitudeR2, setDegree =  0; 
	int heightB0 = 0, heightB1 = 11000, heightB2 = 26000, heightB3 = 37000, heightH2 = 18033, heightH3 = 30973; 

  double throttle = .3, Redthrottle = 0, ballThrottle = 0; 
  double speedthrottle = 1;
  double kP = 0.002; 
  double navFinal = 0; 
  double PIDOutput = 0; 
  double rotMag = 0;
  double rightArc = 0; 

  double FLwheelMag, BLwheelMag, BRwheelMag = 0; 

  boolean simple = false; 
  boolean turnMode = true; 

  double wheelXcos, wheelYsin, FLwheelX, FLwheelY, BLwheelX, BLwheelY, BRwheelX, BRwheelY, max = 0;

  double leftX, leftY, rightX, rightY, leftMag, rightMag, directMag = 0; 

  boolean fixRotation = false;

  final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
  final double DESIRED_TARGET_AREA = 13.0;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if(oi.AButton(P1)){ Pincher = DoubleSolenoid.Value.kForward; }
    else if(oi.BButton(P1)){ Pincher = DoubleSolenoid.Value.kReverse; }
    else { Pincher = DoubleSolenoid.Value.kOff; }
    Robot.kDrivetrain.setPincher(Pincher);

    if(oi.XButton(P1)){Extendor = DoubleSolenoid.Value.kForward; }
    else if(oi.YButton(P1)){Extendor = DoubleSolenoid.Value.kReverse; }
    else { Extendor = DoubleSolenoid.Value.kOff; }
    Robot.kDrivetrain.setExtendor(Extendor);
  
    /*
    if(oi.BButton(P1)){ 
      if(speedthrottle ==1) { speedthrottle = .5;} //Comp speed switch
      //else if(speedthrottle ==.5){speedthrottle = .250;} //School time speed. Comment out for competition!
      else{speedthrottle = 1;} 
    }
    */

    leftX = oi.getLeftXAxis();
    leftY = oi.getLeftYAxis();
    rightX = oi.getRightXAxis(); 
    rightY = oi.getRightYAxis();
    leftMag = oi.getLeftMagnitude();  
    //rightMag = oi.getRightMagnitude(); 
    //if(rightMag > .2 || oi.YButtonDown(P1)){
      //if(rightMag > .7) { rightArc = Math.toDegrees(Math.atan2(rightY, rightX)) + 90; }
      //oi.AButtonDown(P1)) { rightArc = 315; } //Left Close
      //else if(oi.XButtonDown(P1)) { rightArc = 225; } //Left Far
      //else if(oi.YButtonDown(P1)) { rightArc = 135; } //Right Far
      //else if(oi.BButtonDown(P1)) { rightArc = 45; } //Right Close
      //try { Robot.kDrivetrain.turning.setYaw(rightArc - Robot.NavAngle());} catch (Exception e) {}
      //rotMag = Robot.kDrivetrain.turning.getPIDOutput();
    //}
    //else{
      //rotMag = 0; 
    //}
    
    if(leftMag >= .3){ finalAngle = Math.toDegrees(Math.atan2(leftX, leftY)) + Robot.NavAngle();}


    DriveCommand frontLeftCommand = new DriveCommand(finalAngle, leftMag);
    //DriveCommand backLeftCommand = new DriveCommand(BLwheelRot, BLwheelMag * speedthrottle);
    //DriveCommand backRightCommand = new DriveCommand(BRwheelRot, BRwheelMag * speedthrottle);

    kDrivetrain.setModule(ModuleLocation.FRONT_LEFT, frontLeftCommand);
    //kDrivetrain.setModule(ModuleLocation.BACK_LEFT, backLeftCommand);
    //kDrivetrain.setModule(ModuleLocation.BACK_RIGHT, backRightCommand);

		if(oi.StartButton(P1)) { Robot.nav.reset(); }
		
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
