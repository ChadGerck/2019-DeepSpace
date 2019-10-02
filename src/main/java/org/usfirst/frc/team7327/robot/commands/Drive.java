package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team7327.robot.Robot;
import static org.usfirst.frc.team7327.robot.Robot.oi;

public class Drive extends Command {
  public Drive() { requires(Robot.kDrivetrain); }
  @Override protected void initialize() { }
  public static XboxController P1 = oi.Controller0, P2 = oi.Controller1;  

  @Override protected void execute() {

    
    if(oi.StartButton(P1)) { Robot.nav.reset(); }

    if(oi.LeftX(P1) > .3){
      Robot.kDrivetrain.setDrive(.2);
    }
    
		
  }
  @Override protected boolean isFinished() { return false;}
  @Override protected void end() {}
  @Override protected void interrupted() {}
}
