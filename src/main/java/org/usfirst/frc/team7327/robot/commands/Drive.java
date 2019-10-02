package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import org.usfirst.frc.team7327.robot.Robot;
import static org.usfirst.frc.team7327.robot.Robot.oi;

public class Drive extends Command {
  public Drive() { requires(Robot.kDrivetrain); }
  @Override protected void initialize() { }
  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  double finalAngle, Redthrottle, ballThrottle, rotMag, rightArc; 
  double directMag, steering_adjust, x; 
  boolean fixRotation;   
  double SteerP = -0.025, SteerD = 0, lastError = 0;
  boolean rocketAngle = true; 

  private ShuffleboardTab tab = Shuffleboard.getTab("RocketAngle");
  private NetworkTableEntry angleR = tab.add("RocketAngle", rocketAngle).getEntry();
    

  DoubleSolenoid.Value Pincher, Extendor, pullout = Value.kOff; 

  @Override protected void execute() {

    
		if(oi.StartButton(P1)) { Robot.nav.reset(); }
		
  }
  @Override protected boolean isFinished() { return false;}
  @Override protected void end() {}
  @Override protected void interrupted() {}
}
