package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.usfirst.frc.team7327.robot.Constants;
import org.usfirst.frc.team7327.robot.ElevatorModule;
import org.usfirst.frc.team7327.robot.Util.DriveCommand;
import org.usfirst.frc.team7327.robot.Util.ModuleLocation;
import org.usfirst.frc.team7327.robot.commands.Drive;
import org.usfirst.frc.team7327.robot.SwerveModule;
import org.usfirst.frc.team7327.robot.TurnModule;

public class DriveTrain extends Subsystem {

  public TurnModule turning; 

  
  static final double tkP = 2;  
  static final double tkI = .0;
  static final double tkD = .1;

  public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -99.2); 
  public static Potentiometer abeNE = new AnalogPotentiometer(3, 360, -197.85);
  public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, -27.8); 
  public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, -65.7); 

  private SwerveModule moduleFrontLeft = new SwerveModule(Constants.kFrontLeftSteerID, Constants.kFrontLeftDriveID , abeNW, true, Constants.kFrontLeftOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleFrontRight =new SwerveModule(Constants.kFrontRightSteerID,Constants.kFrontRightDriveID, abeNE, false,Constants.kFrontRightOffset,Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleBackLeft  = new SwerveModule(Constants.kBackLeftSteerID,  Constants.kBackLeftDriveID  , abeSW, true, Constants.kBackLeftOffset,  Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleBackRight = new SwerveModule(Constants.kBackRightSteerID, Constants.kBackRightDriveID , abeSE, false,Constants.kBackRightOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);

  public static ElevatorModule Elevator;
  
  public static VictorSPX BallVictor, Intake;
  
  static final double ekP = .0004, ekI = 0, ekD = 0; 
  
  public DriveTrain(){

    Elevator = new ElevatorModule(8, ekP, ekI, ekD); 
	  Intake = new VictorSPX(9); 
    BallVictor = new VictorSPX(10); 
    
    turning = new TurnModule(tkP, tkI, tkD); 
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Drive());
  }
  
  public SwerveModule getModule(ModuleLocation location){
    switch(location){
      case FRONT_LEFT:
        return moduleFrontLeft;
      case FRONT_RIGHT:
        return moduleFrontRight;
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
      case FRONT_RIGHT:
        moduleFrontRight.set(degrees, power);
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
	public double getAbeNE(){ return abeNE.get(); }
	public double getAbeSW(){ return abeSW.get(); }
	public double getAbeSE(){ return abeSE.get(); }

  public void setModule(ModuleLocation loc, DriveCommand command){
    setModule(loc, command.getDegrees(), command.getSpeed());
  }

  public void setAllAngle(double degrees){
    moduleFrontLeft.setSteeringDegrees(degrees);
    moduleFrontRight.setSteeringDegrees(degrees);
    moduleBackLeft.setSteeringDegrees(degrees);
    moduleBackRight.setSteeringDegrees(degrees);
  }

  public void setAllPower(double power){
    moduleFrontLeft.setDrivePower(power);
    moduleFrontRight.setDrivePower(power);
    moduleBackLeft.setDrivePower(power);
    moduleBackRight.setDrivePower(power);
  }

  public void setRawElevator(double speed){ Elevator.setRawElev(speed); }
	public void setElevatorPosition(double position){ Elevator.setPosition(position); }
	public void ElevOn(boolean On) { Elevator.setOn(On); }
	public void ResetElevator() { Elevator.ElevatorReset(); }
	public void ConfigElevator() { Elevator.configFeedbackSensor(); }
	public void SetElevatorStatus() { Elevator.setTalonStatus(); }
	public double getLiftVelocity() { return Elevator.getLiftVelocity(); }
	public double getLiftPosition() { return Elevator.getLiftPosition(); }
	public void setRawBallIn(double speed){ BallVictor.set(ControlMode.PercentOutput, speed); }
	public void setRawIntake(double intakevalue) { Intake.set(ControlMode.PercentOutput, intakevalue);	} 

  public void updateDashboard(){}
}
