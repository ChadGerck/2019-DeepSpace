package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.SwerveMath;
import static org.usfirst.frc.team7327.robot.Robot.oi;
import org.usfirst.frc.team7327.robot.ElevatorPositions;

public class Drive extends Command {
  public Drive() { requires(Robot.kDrivetrain); }
  @Override protected void initialize() { }
  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, Redthrottle, ballThrottle, rotMag, rightArc; 
  double directMag, steering_adjust, x; 
  boolean fixRotation;   
  double SteerP = -0.05, SteerD = 0, lastError = 0;

  DoubleSolenoid.Value Pincher = Value.kOff; 
  DoubleSolenoid.Value Extendor = Value.kOff; 

  @Override protected void execute() {
  
    
    if(Robot.oi.AButtonDown(P1)){
      x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      if(x >= -5 && x <= 5){ steering_adjust = 0; }
      else{ steering_adjust = SteerP*-x + SteerD*(lastError+x); }
      lastError = -x; 
      
    }
    
    if(oi.RightMag(P1) > .7){ try {Robot.kDrivetrain.turning.setYaw(oi.RightArc(P1) - Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.kDrivetrain.turning.getPIDOutput();
    } else{ rotMag = 0; }

    if( oi.AButtonDown(P1)){ finalAngle = Math.toDegrees(Math.atan2(oi.LeftY(P1),steering_adjust))-90; directMag = (Math.abs(steering_adjust) + Math.abs(oi.LeftY(P1)))/2; }
    else if(oi.LeftMag(P1) >= .2){ finalAngle = Math.toDegrees(Math.atan2(oi.LeftX(P1), oi.LeftY(P1))) + Robot.NavAngle(); directMag = .5*oi.LeftMag(P1); }
    else if(oi.RightTrigger(P1) > .1) {finalAngle = 0; directMag = -.125*oi.RightTrigger(P1); } else if(oi.LeftTrigger(P1) > .1) {finalAngle = 180; directMag = .125 * -oi.LeftTrigger(P1); }
    else if(oi.RightBumperDown(P1)) { finalAngle = 90; directMag = .125; } else if(oi.LeftBumperDown(P1)) { finalAngle = 270; directMag = .125; }
    else { directMag = 0; }

    if(oi.LeftBumperDown(P1) || oi.RightBumperDown(P1) || oi.RightTrigger(P1) > .1 || oi.LeftTrigger(P1) > .1 || oi.LeftMag(P1) >= 0.2 || oi.RightMag(P1) > 0.2 || oi.AButtonDown(P1)) {
      fixRotation = true; 
    } else{fixRotation = false;}
    SwerveMath.ComputeSwerve(finalAngle, directMag, rotMag, fixRotation); 

		if(oi.StartButton(P1)) { Robot.nav.reset(); } if(oi.StartButton(P2)) { Robot.kDrivetrain.ResetElevator(); }
		
		if(oi.RightBumperDown(P2)) { Redthrottle = .6; } else if(oi.LeftBumperDown(P2)) { Redthrottle = -.6;}
		else { Redthrottle = 0; } Robot.kDrivetrain.setRawIntake(Redthrottle);

		if(oi.RightMag(P2) > .3) { ballThrottle = .75*oi.RightY(P2); } else if(oi.RightBumperDown(P2)) { ballThrottle = .5; }
		else{ ballThrottle = 0; } Robot.kDrivetrain.setRawBallIn(ballThrottle); 
    
    ElevatorPositions.MoveElevators();


    if(oi.AButton(P2)){ Pincher = DoubleSolenoid.Value.kForward; }
    else if(oi.BButton(P2)){ Pincher = DoubleSolenoid.Value.kReverse; }
    else { Pincher = DoubleSolenoid.Value.kOff; }
    Robot.kDrivetrain.setPincher(Pincher);

    if(oi.XButton(P2)){Extendor = DoubleSolenoid.Value.kForward; }
    else if(oi.YButton(P2)){Extendor = DoubleSolenoid.Value.kReverse; }
    else { Extendor = DoubleSolenoid.Value.kOff; }
    Robot.kDrivetrain.setExtendor(Extendor);
  }
  @Override protected boolean isFinished() { return false;}
  @Override protected void end() {}
  @Override protected void interrupted() {}
}
