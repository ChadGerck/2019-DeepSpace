package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team7327.robot.Robot;
import static org.usfirst.frc.team7327.robot.Robot.oi;

import org.usfirst.frc.team7327.robot.ElevatorModule;
import org.usfirst.frc.team7327.robot.ElevatorPositions;

public class Drive extends Command {
  public Drive() { requires(Robot.kDrivetrain); }

  int DriveSetting, ElevSetting = 0; 
  @Override
  protected void initialize() { DriveSetting = 0; ElevSetting = 0; }

  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, FLwheelRot, FRwheelRot, BLwheelRot, BRwheelRot = 0;
  int rotAngBR = 135, rotAngBL = 45, rotAngFR = -135, rotAngFL = -45;    

  double degreesL, magnitudeL, degreesR, magnitudeR, degreesL2, magnitudeL2, magnitudeR2, setDegree =  0; 

  double Redthrottle = 0, ballThrottle = 0; 
  double kP = 0.002; 
  double navFinal, PIDOutput, rotMag, rightArc = 0; 

  double FLwheelMag, FRwheelMag, BLwheelMag, BRwheelMag = 0; 

  boolean simple = false, turnMode = true, fixRotation = false; 

  double wheelXcos, wheelYsin, FLwheelX, FLwheelY, FRwheelX, FRwheelY, BLwheelX, BLwheelY, BRwheelX, BRwheelY, max = 0;

  double leftX, leftY, rightX, rightY, leftMag, rightMag, directMag = 0; 

  double SteerP = -0.025, SteerD = 0.4; 
  double SteerP2 = -0.05, SteerD2 = 0.6; 
  double diffError, lastError, lastError2, steering_adjust = 0.0;

  final double DRIVE_K = 0.26;                   
  final double DESIRED_TARGET_AREA = 13.0;

  NetworkTable table; 
  NetworkTableEntry tv, tx, ty, ta; 

  double velocity, target, x, y, area, heading_error; 

  
  public static final ElevatorPositions elevPosition = new ElevatorPositions(); 

  @Override
  protected void execute() {

    table = NetworkTableInstance.getDefault().getTable("limelight");
		tv = table.getEntry("tv"); tx = table.getEntry("tx");
		ty = table.getEntry("ty"); ta = table.getEntry("ta");

    velocity = 0; target = tv.getDouble(0.0); 
		x = tx.getDouble(0.0); y = ty.getDouble(0.0);
		area = ta.getDouble(0.0);

    if(Robot.oi.AButtonDown(P1)) {
      heading_error = -x; diffError = lastError - heading_error; 
			steering_adjust = SteerP*heading_error + SteerD*diffError;
      lastError = heading_error; 
    }
    else if(Robot.oi.BButtonDown(P1)) {
			heading_error = -x; diffError = lastError2 - heading_error; 
			steering_adjust = SteerP2*heading_error + SteerD2*diffError;
      lastError2 = heading_error; 
    }
    else if(Robot.oi.XButtonDown(P1)) {
      if(target == 0){ velocity = 0; }
      else { velocity = (DESIRED_TARGET_AREA - area) * DRIVE_K; }
    }

    leftX = oi.getLeftXAxis(); leftY = oi.getLeftYAxis();
    rightX = oi.getRightXAxis(); rightY = oi.getRightYAxis();
    leftMag = oi.getLeftMagnitude(); rightMag = oi.getRightMagnitude(); 
    if(rightMag > .7){ rightArc = Math.toDegrees(Math.atan2(rightY, rightX)) + 90;
      try { Robot.kDrivetrain.turning.setYaw(rightArc - Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.kDrivetrain.turning.getPIDOutput();
    }
    else if(oi.AButtonDown(P1)){ rotMag = steering_adjust; }
    else{ rotMag = 0; }
    
    if(leftMag >= .3){ finalAngle = Math.toDegrees(Math.atan2(leftX, leftY)) + Robot.NavAngle(); directMag = leftMag; }
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

    Robot.swerveMath.SwerveMath(finalAngle, directMag, rotMag, fixRotation); 

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
    
    elevPosition.ElevatorPositions();
		
  }
  @Override
  protected boolean isFinished() { return false;}
  @Override
  protected void end() {}
  @Override
  protected void interrupted() {}
}
