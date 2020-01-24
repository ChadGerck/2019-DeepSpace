package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7327.robot.Robot;
import static org.usfirst.frc.team7327.robot.Robot.oi;

public class Drive extends Command {
  public Drive() { requires(Robot.kDrivetrain); }
  @Override protected void initialize() { }
  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  
  public static double rotMag; 
  public static double rightArc; 
  public static double turning;

  @Override protected void execute() {

    
    if(oi.StartButton(P1)) { Robot.nav.reset(); }


    SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle());

      
     
    Robot.kDrivetrain.setSouthWest(oi.LeftY(P1)*1 + (oi.RightX(P1)*1 ) - (oi.LeftX(P1)*1) + (rotMag)); 
    Robot.kDrivetrain.setNorthWest(oi.LeftY(P1)*1 + (oi.RightX(P1)*1 ) + (oi.LeftX(P1)*1) + (rotMag)); 
    Robot.kDrivetrain.setSouthEast(oi.LeftY(P1)*1 - (oi.RightX(P1)*1 ) + (oi.LeftX(P1)*1) + (rotMag)); 
    Robot.kDrivetrain.setNorthEast(oi.LeftY(P1)*1 - (oi.RightX(P1)*1 ) - (oi.LeftX(P1)*1) + (rotMag)); 
    //rotMag 
    
<<<<<<< Updated upstream
  //smegmaif( oi.RightMag(P1) > .7 ) { rightArc = oi.RightArc(P1); 
=======
  if( oi.RightMag(P1) > .7 ) { rightArc = oi.RightArc(P1); 
>>>>>>> Stashed changes
    try { Robot.kDrivetrain.turning.setYaw(rightArc - Robot.NavAngle());} catch (Exception e) {}
    rotMag = Robot.kDrivetrain.turning.getPIDOutput();}
   else {rotMag = 0;}  

    



  }
  @Override protected boolean isFinished() { return true;}
  @Override protected void end() {}
  @Override protected void interrupted() {}
}

