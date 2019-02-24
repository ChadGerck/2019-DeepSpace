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
    double navLX = oi.getLeftMagnitude()*Math.cos(Robot.NavAngle());
    double navLY = oi.getLeftMagnitude()*Math.sin(Robot.NavAngle());
    double navRX = oi.getRightMagnitude()*Math.cos(Robot.NavAngle());
    double navRY = oi.getRightMagnitude()*Math.sin(Robot.NavAngle());

    double heading = Math.toRadians(Robot.kDrivetrain.getGyro());

    /*
    double temp = leftY*Math.cos(heading) + leftX*Math.sin(heading);
    leftX = -leftY*Math.sin(heading) + leftX*Math.cos(heading);
    leftY = temp;
    */



    double A = (leftX - navLX - rightX + navRX) * (Constants.kWheelbase/Constants.kTrackwidth);
    double B = (leftX - navLX + rightX - navRX) * (Constants.kWheelbase/Constants.kTrackwidth);
    double C = (leftY - navLY - rightY + navRY) * (Constants.kTrackwidth/Constants.kWheelbase);
    double D = (leftY - navLY + rightY - navRY) * (Constants.kTrackwidth/Constants.kWheelbase);

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

    DriveCommand frontLeftCommand = new DriveCommand((Math.atan2(B, C) * (180.0/Math.PI))+Robot.NavAngle(), frontLeftSpeed);
    DriveCommand frontRightCommand = new DriveCommand((Math.atan2(B, D) * (180.0/Math.PI))+Robot.NavAngle(), frontRightSpeed);
    DriveCommand backLeftCommand = new DriveCommand((Math.atan2(A, C) * (180.0/Math.PI) )+Robot.NavAngle(), backLeftSpeed);
    DriveCommand backRightCommand = new DriveCommand((Math.atan2(A, D) * (180.0/Math.PI))+Robot.NavAngle(), backRightSpeed);

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
