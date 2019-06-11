package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.SwerveMath;

import static org.usfirst.frc.team7327.robot.Robot.oi;
import org.usfirst.frc.team7327.robot.ElevatorPositions;

public class Drive extends Command {
  public Drive() { requires(Robot.kDrivetrain); }

  @Override protected void initialize() { }

  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, Redthrottle, ballThrottle, rotMag, rightArc, leftX, leftY, rightX, rightY; 
  double leftMag, rightMag, directMag, diffError, steering_adjust, x, heading_error; 
  boolean fixRotation;   
  double SteerP = -0.025, SteerD = 0.4, SteerP2 = -0.05, SteerD2 = 0.6, lastError = 0, lastError2 = 0;
  final double DRIVE_K = 0.26, DESIRED_TARGET_AREA = 13.0;

  @Override
  protected void execute() {
    x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

    if(Robot.oi.AButtonDown(P1)){
      diffError = lastError2 - heading_error; 
			steering_adjust = SteerP2*heading_error + SteerD2*diffError;
      lastError2 = heading_error; 
    }

    leftX  = oi.LeftStickX(P1);   leftY = oi.LeftStickY(P1);
    rightX = oi.RightStickX(P1); rightY = oi.RightStickY(P1);
    leftMag = oi.getLeftMagnitude(P1); rightMag = oi.getRightMagnitude(P1); 
    if(rightMag > .7){ rightArc = Math.toDegrees(Math.atan2(rightY, rightX)) + 90;
      try { Robot.kDrivetrain.turning.setYaw(rightArc - Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.kDrivetrain.turning.getPIDOutput();
    }
    else{ rotMag = 0; }

    if( oi.AButtonDown(P1)){ finalAngle = Math.atan2(steering_adjust,leftX); directMag = steering_adjust + leftX; }
    else if(leftMag >= .3){ finalAngle = Math.toDegrees(Math.atan2(leftX, leftY)) + Robot.NavAngle(); directMag = leftMag; }
    else if(oi.RightTrigger(P1) > .1) {finalAngle = 0; directMag = -.5*oi.RightTrigger(P1); }
    else if(oi.LeftTrigger(P1) > .1) {finalAngle = 180; directMag = -oi.LeftTrigger(P1); }
    else if(oi.RightBumperDown(P1)) { finalAngle = 90; directMag = .5; }
    else if(oi.LeftBumperDown(P1)) { finalAngle = 270; directMag = .5; }
    else { directMag = 0; }

    if(oi.LeftBumperDown(P1) || oi.RightBumperDown(P1) || oi.RightTrigger(P1) > .1 || oi.LeftTrigger(P1) > .1 || leftMag > 0.3 || rightMag > 0.2 || oi.AButtonDown(P1) || oi.BButtonDown(P1) || oi.YButtonDown(P1) || oi.XButtonDown(P1)) {
      fixRotation = true; }
    else{fixRotation = false;}

    SwerveMath.ComputeSwerve(finalAngle, directMag, rotMag, fixRotation); 

		if(oi.StartButton(P1)) { Robot.nav.reset(); }
		if(oi.StartButton(P2)) { Robot.kDrivetrain.ResetElevator(); }
		
		if(oi.RightBumperDown(P2)) { Redthrottle = -.6; }
		else if(oi.LeftBumperDown(P2)) { Redthrottle = .6;}
		else { Redthrottle = 0; }
		Robot.kDrivetrain.setRawIntake(Redthrottle);

		if(oi.getRightMagnitude(P2) > .3) { ballThrottle = .75*oi.RightStickY(P2); }
		else if(oi.RightBumperDown(P2)) { ballThrottle = .5; }
		else{ ballThrottle = 0; }
    Robot.kDrivetrain.setRawBallIn(ballThrottle); 
    
    ElevatorPositions.MoveElevators();
  }
  @Override protected boolean isFinished() { return false;}
  @Override protected void end() {}
  @Override protected void interrupted() {}
}
