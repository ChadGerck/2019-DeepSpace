package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team7327.robot.TurnModule;
import org.usfirst.frc.team7327.robot.commands.Drive;

public class DriveTrain extends Subsystem {
  public  static TurnModule turning; 
  public static TalonSRX drive1; 
  public static TalonSRX drive2; 
  public static TalonSRX drive3; 
  public static TalonSRX drive4; 
  public DriveTrain(){
    drive1 = new TalonSRX(1); 
    drive2 = new TalonSRX(2);
    drive3 = new TalonSRX(3);
    drive4 = new TalonSRX(4);
    turning = new TurnModule();
  }
  @Override public void initDefaultCommand() { setDefaultCommand(new Drive()); }
  public void setDrive(double speed){
    drive1.set(ControlMode.PercentOutput, -speed*0.6);
    drive2.set(ControlMode.PercentOutput, speed*0.6);
    drive3.set(ControlMode.PercentOutput, -speed);
    drive4.set(ControlMode.PercentOutput, speed*0.6);
    
  }
  public void setLeft(double speed){
    
    drive2.set(ControlMode.PercentOutput, speed*0.6);
    
    drive4.set(ControlMode.PercentOutput, speed*0.6);
  }
  public void setRight(double speed){
    drive1.set(ControlMode.PercentOutput, -speed*0.6);
    
    drive3.set(ControlMode.PercentOutput, -speed);
    
  }
  public void setSouthWest(double speed){
    drive1.set(ControlMode.PercentOutput, -speed*0.6);
  }
  public void setNorthWest(double speed){
    drive3.set(ControlMode.PercentOutput, -speed);
  }
  public void setSouthEast(double speed){
    drive2.set(ControlMode.PercentOutput, speed*0.6);
  }
  public void setNorthEast(double speed){
    drive4.set(ControlMode.PercentOutput, speed*0.6);
  }

  public void updateDashboard(){}
}