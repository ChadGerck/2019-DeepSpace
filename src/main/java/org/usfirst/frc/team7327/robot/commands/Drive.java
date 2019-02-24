/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7327.robot.Constants;
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
  @Override
  protected void initialize() {
  }

  public static XboxController P1 = Robot.oi.Controller0, P2 = Robot.oi.Controller1; 
  double Rotation = 0; 
  double testRotation; 
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double leftX = oi.getLeftXAxis();
    double leftY = oi.getLeftYAxis();
    double rightX = oi.getRightXAxis();
    double rightY = oi.getRightYAxis(); 
    //double navX = Math.cos(Math.toRadians(Robot.NavAngle()));
    //double navY = Math.sin(Math.toRadians(Robot.NavAngle()));
    //double thetaL = (Math.toDegrees(Math.atan2(-leftX, leftY))+180)-Robot.NavAngle();
    //if( thetaL < 0){ thetaL += 360; } 
    //double Lx = -oi.getLeftMagnitude()*Math.cos(thetaL);
    //double Ly = -oi.getLeftMagnitude()*Math.sin(thetaL); 
    //double thetaR = (Math.toDegrees(Math.atan2(-rightX, rightY))+180)-Robot.NavAngle();
    //if( thetaR < 0){ thetaR += 360; } 
    //double Rx = -oi.getLeftMagnitude()*Math.cos(thetaR);
    //double Ry = -oi.getLeftMagnitude()*Math.sin(thetaR); 
    double stickAngle = -Math.toDegrees(Math.atan2(-leftX, leftY))+90; 
    if (stickAngle > 180) {
			stickAngle -= 360;
		}


    //SmartDashboard.putNumber("navX: ", navX); 
    //SmartDashboard.putNumber("navY: ", navY);
    SmartDashboard.putNumber("NAVANGLEGYRO: ", Robot.NavAngle());
    SmartDashboard.putNumber("LX: ", -leftX); 
    SmartDashboard.putNumber("LY: ", leftY); 
    SmartDashboard.putNumber("to degrees: arctan2(Lx, Ly): ", Math.toDegrees(Math.atan2(-leftX, leftY))+180); 
    //SmartDashboard.putNumber("thetaL: ", thetaL); 
    SmartDashboard.putNumber("RX: ", rightX); 
    SmartDashboard.putNumber("RY: ", rightY); 
    SmartDashboard.putNumber("to degrees: arctan2(Rx, Ry): ", Math.toDegrees(Math.atan2(rightX, rightY))); 

    double heading = Math.toRadians(Robot.kDrivetrain.getGyro());

    /*
    double temp = leftY*Math.cos(heading) + leftX*Math.sin(heading);
    leftX = -leftY*Math.sin(heading) + leftX*Math.cos(heading);
    leftY = temp;
    */



    double A = Lx - Rx * (Constants.kWheelbase/Constants.kTrackwidth);           //leftX - rightX * (Constants.kWheelbase/Constants.kTrackwidth);
    double B = Lx + Rx * (Constants.kWheelbase/Constants.kTrackwidth);            //leftX + rightX * (Constants.kWheelbase/Constants.kTrackwidth);
    double C = Ly - Rx * (Constants.kWheelbase/Constants.kTrackwidth);            //leftY - rightX * (Constants.kTrackwidth/Constants.kWheelbase);
    double D = Ly + Rx * (Constants.kWheelbase/Constants.kTrackwidth);           //leftY + rightX * (Constants.kTrackwidth/Constants.kWheelbase);

    double frontLeftSpeed = Math.hypot(B, C);
    double frontRightSpeed = Math.hypot(B, D);
    double backLeftSpeed = Math.hypot(A, C);
    double backRightSpeed = Math.hypot(A, D);
    
    double max = frontLeftSpeed;

    if(frontRightSpeed > max)
      max = frontRightSpeed;
    else if(backLeftSpeed > max)
      max = backLeftSpeed;
    else if(backRightSpeed > max)
      max = backRightSpeed;
    if(max > 1){
      frontLeftSpeed /= max;
      frontRightSpeed /= max;
      backLeftSpeed /= max;
      backRightSpeed /= max;
    }

    DriveCommand frontLeftCommand = new DriveCommand((Math.atan2(B, C) * (180.0/Math.PI)), frontLeftSpeed);
    DriveCommand frontRightCommand = new DriveCommand((Math.atan2(B, D) * (180.0/Math.PI)), frontRightSpeed);
    DriveCommand backLeftCommand = new DriveCommand((Math.atan2(A, C) * (180.0/Math.PI) ), backLeftSpeed);
    DriveCommand backRightCommand = new DriveCommand((Math.atan2(A, D) * (180.0/Math.PI)), backRightSpeed);

    kDrivetrain.setModule(ModuleLocation.FRONT_LEFT, frontLeftCommand);
    kDrivetrain.setModule(ModuleLocation.FRONT_RIGHT, frontRightCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_LEFT, backLeftCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_RIGHT, backRightCommand);


    //7327 CODE BELOW
    SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
    if(Robot.oi.StartButton(P1)) { Robot.nav.reset(); }


















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
