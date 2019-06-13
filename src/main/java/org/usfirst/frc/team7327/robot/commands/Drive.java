package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.SwerveMath;
import static org.usfirst.frc.team7327.robot.Robot.oi;

public class Drive extends Command {
  public Drive() { requires(Robot.kDrivetrain); }
  @Override protected void initialize() { }
  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, Redthrottle, ballThrottle, rotMag, rightArc; 
  double directMag, steering_adjust, x; 
  boolean fixRotation;   
  double SteerP = -0.05, SteerD = 0.6, lastError = 0;
  DoubleSolenoid.Value Pincher, Extender; 

  @Override protected void execute() {
    if(oi.AButton(P1)){ Pincher = Value.kForward; }
    else if(oi.BButton(P1)){ Pincher = Value.kReverse; }
    else{ Pincher = Value.kOff;  }
    Robot.kDrivetrain.setPincher(Pincher);

    if(oi.XButton(P1)){ Extender = Value.kForward; }
    else if(oi.YButton(P1)){ Extender = Value.kReverse; }
    else{ Extender = Value.kOff;  }
    Robot.kDrivetrain.setExtender(Extender);

    if(oi.RightTrigger(P1) > .1){ Robot.kDrivetrain.setElevator(-.6); }
    else if(oi.LeftTrigger(P1) > .1){ Robot.kDrivetrain.setElevator(.6); }
    else{Robot.kDrivetrain.setElevator(0);}

    /*
    if(Robot.oi.AButtonDown(P1)){
      x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
			steering_adjust = SteerP*-x + SteerD*(lastError+x);
      lastError = -x; 
    }
    */
    if(oi.RightMag(P1) > .7){ try {Robot.kDrivetrain.turning.setYaw(oi.RightArc(P1) - Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.kDrivetrain.turning.getPIDOutput();
    } else{ rotMag = 0; }

    //if( oi.AButtonDown(P1)){ finalAngle = Math.toDegrees(Math.atan2(oi.LeftX(P1),steering_adjust)); directMag = (Math.abs(steering_adjust) + Math.abs(oi.LeftX(P1)))/2; }
    if(oi.LeftMag(P1) >= .3){ finalAngle = Math.toDegrees(Math.atan2(oi.LeftX(P1), oi.LeftY(P1))) + Robot.NavAngle(); directMag = oi.LeftMag(P1); }
    //else if(oi.RightTrigger(P1) > .1) {finalAngle = 0; directMag = -.5*oi.RightTrigger(P1); } else if(oi.LeftTrigger(P1) > .1) {finalAngle = 180; directMag = -oi.LeftTrigger(P1); }
    //else if(oi.RightBumperDown(P1)) { finalAngle = 90; directMag = .5; } else if(oi.LeftBumperDown(P1)) { finalAngle = 270; directMag = .5; }
    else { directMag = 0; }

    if(oi.LeftMag(P1) > 0.3 || oi.RightMag(P1) > 0.2) {
      fixRotation = true; 
    } else{fixRotation = false;}
    SwerveMath.ComputeSwerve(finalAngle, directMag, rotMag, fixRotation); 

		if(oi.StartButton(P1)) { Robot.nav.reset(); } 
    
  }
  @Override protected boolean isFinished() { return false;}
  @Override protected void end() {}
  @Override protected void interrupted() {}
}
