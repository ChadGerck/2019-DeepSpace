package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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

  
  static final double tkP = .4;  //.4 cement , .6 carpet
  static final double tkI = .000001;
  static final double tkD = .04; //.04 cement , .05 carpet

  //Harbi
	public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -235.2); 
	public static Potentiometer abeNE = new AnalogPotentiometer(3, 360, -234.85);
	public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, -335.3); 
  public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, -7.7);
  
  
	//Discovery
	//public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -93.2); 
	//public static Potentiometer abeNE = new AnalogPotentiometer(3, 360, -201.85);
	//public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, -240.8); 
	//public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, -238.7); 

  private SwerveModule moduleFrontLeft = new SwerveModule(Constants.kFrontLeftSteerID, Constants.kFrontLeftDriveID, abeNW, true, Constants.kFrontLeftOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleFrontRight = new SwerveModule(Constants.kFrontRightSteerID, Constants.kFrontRightDriveID, abeNE,  false, Constants.kFrontRightOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleBackLeft = new SwerveModule(Constants.kBackLeftSteerID, Constants.kBackLeftDriveID, abeSW,  true, Constants.kBackLeftOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  private SwerveModule moduleBackRight = new SwerveModule(Constants.kBackRightSteerID, Constants.kBackRightDriveID, abeSE, false, Constants.kBackRightOffset, Constants.kSwerveP, Constants.kSwerveI, Constants.kSwerveD);
  
  
	//private DoubleSolenoid Bicep; 

  public static ElevatorModule Elevator;
  
  public static VictorSPX BallVictor;
	//public static VictorSPX Intake;
  public static TalonSRX Intake; 
  
  static final double ekP = .0004;
	static final double ekI = 0; 
	static final double ekD = 0; 
  
  public DriveTrain(){

    Elevator = new ElevatorModule(8, ekP, ekI, ekD); 
		//Discovery
		//Intake = new VictorSPX(9); 
		//Harbi
		Intake = new TalonSRX(9); 
    BallVictor = new VictorSPX(10); 
    
    turning = new TurnModule(tkP, tkI, tkD);

		//Bicep = new DoubleSolenoid(1,2);

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
