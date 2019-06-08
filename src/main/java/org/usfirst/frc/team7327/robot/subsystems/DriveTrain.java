package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team7327.robot.Constants;
import org.usfirst.frc.team7327.robot.Util.DriveCommand;
import org.usfirst.frc.team7327.robot.Util.ModuleLocation;
import org.usfirst.frc.team7327.robot.commands.Drive;
import org.usfirst.frc.team7327.robot.SwerveModule;
import org.usfirst.frc.team7327.robot.TurnModule;

public class DriveTrain extends Subsystem {

  public TurnModule turning; 

  
  static final double tkP = .5;  //.4 cement , .6 carpet
  static final double tkI = .0;
  static final double tkD = 1; //.04 cement , .05 carpet

  //Harbi
  public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -139); 
  public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, -26); 
  public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, -243);
  
  //Discovery
  //public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -99.2); 
  //public static Potentiometer abeNE = new AnalogPotentiometer(3, 360, -194.85);
  //public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, -27.8); 
  //public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, 45.7); 

  private SwerveModule moduleFrontLeft = new SwerveModule(Constants.kFrontLeftSteerID, Constants.kFrontLeftDriveID, abeNW, true, Constants.kFrontLeftOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleBackLeft = new SwerveModule(Constants.kBackLeftSteerID, Constants.kBackLeftDriveID, abeSW,  true, Constants.kBackLeftOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleBackRight = new SwerveModule(Constants.kBackRightSteerID, Constants.kBackRightDriveID, abeSE, false, Constants.kBackRightOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  
  private DoubleSolenoid Pincher; 
  private DoubleSolenoid Extendor;
  public TalonSRX Elevatorm1, Elevatorm2;
  public DriveTrain(){

    turning = new TurnModule(tkP, tkI, tkD); 

    Pincher = new DoubleSolenoid(0, 1); 
    Extendor = new DoubleSolenoid(2, 3);
    Elevatorm1 = new TalonSRX(17);
    Elevatorm2 = new TalonSRX(18);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Drive());
  }

  public void setPincher(DoubleSolenoid.Value value){
    Pincher.set(value);
  }
  
  public Void setExtendor(DoubleSolenoid.Value value){
  Extendor.set(value);
    return null;
}
  public SwerveModule getModule(ModuleLocation location){
    switch(location){
      case FRONT_LEFT:
        return moduleFrontLeft;
      case BACK_LEFT:
        return moduleBackLeft;
      case BACK_RIGHT:
        return moduleBackRight;
      default:
        return null;
    }
  }

  public void setModule(ModuleLocation loc, double degrees, double power){
    switch(loc){
      case FRONT_LEFT:
        moduleFrontLeft.set(degrees, power);
        break;
      case BACK_LEFT:
        moduleBackLeft.set(degrees, power);
        break;
      case BACK_RIGHT:
        moduleBackRight.set(degrees, power);
        break;
    }
  }

	public double getAbeNW(){ return abeNW.get(); }
	public double getAbeSW(){ return abeSW.get(); }
	public double getAbeSE(){ return abeSE.get(); }

  public void setModule(ModuleLocation loc, DriveCommand command){
    setModule(loc, command.getDegrees(), command.getSpeed());
  }

  public void setAllAngle(double degrees){
    moduleFrontLeft.setSteeringDegrees(degrees);
    moduleBackLeft.setSteeringDegrees(degrees);
    moduleBackRight.setSteeringDegrees(degrees);
  }

  public void setAllPower(double power){
    moduleFrontLeft.setDrivePower(power);
    moduleBackLeft.setDrivePower(power);
    moduleBackRight.setDrivePower(power);
  }
  public void Elevatorms(double power){
    Elevatorm1.

  }
  public void updateDashboard(){
    //SmartDashboard.putNumber("Front Left Error", moduleFrontLeft.getError());
    // SmartDashboard.putNumber("Front Right Error", moduleFrontRight.getError());
    // SmartDashboard.putNumber("Back Left Error", moduleBackLeft.getError());
    // SmartDashboard.putNumber("Back Right Error", moduleBackRight.getError());

    // SmartDashboard.putNumber("Front Left Setpoint", moduleFrontLeft.getSetpointDegrees());
    // SmartDashboard.putNumber("Front Right Setpoint", moduleFrontRight.getSetpointDegrees());
    // SmartDashboard.putNumber("Back Left Setpoint", moduleBackLeft.getSetpointDegrees());
    // SmartDashboard.putNumber("Back Right Setpoint", moduleBackRight.getSetpointDegrees());
  }

  public double getGyro(){
    return 0;
  }
}
