package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.usfirst.frc.team7327.robot.ElevatorModule;
import org.usfirst.frc.team7327.robot.commands.Drive;
import org.usfirst.frc.team7327.robot.SwerveModule;
import org.usfirst.frc.team7327.robot.TurnModule;

public class DriveTrain extends Subsystem {
  public TurnModule turning; 
  public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, 80.8); 
  public static Potentiometer abeNE = new AnalogPotentiometer(3, 360, 165.15);
  public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, 152.2); 
  public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, 294.3); 

  double kSwerveP = 4.8; 
  private SwerveModule moduleFrontLeft = new SwerveModule(0,1, abeNW, kSwerveP);
  private SwerveModule moduleFrontRight= new SwerveModule(2,3, abeNE, kSwerveP);
  private SwerveModule moduleBackLeft  = new SwerveModule(4,5, abeSW, kSwerveP);
  private SwerveModule moduleBackRight = new SwerveModule(6,7, abeSE, kSwerveP);

  public static ElevatorModule Elevator;
  public static VictorSPX BallVictor, Intake;
  
  public DriveTrain(){
    Elevator = new ElevatorModule(8); 
	  Intake = new VictorSPX(9); 
    BallVictor = new VictorSPX(10); 
    turning = new TurnModule(); 
  }
  @Override public void initDefaultCommand() { setDefaultCommand(new Drive()); }
  public void setModule(String loc, double degrees, double power){
    switch(loc){
      case "FRONT_LEFT" :moduleFrontLeft .set(degrees, power);break;
      case "FRONT_RIGHT":moduleFrontRight.set(degrees, power);break;
      case "BACK_LEFT"  :moduleBackLeft  .set(degrees, power);break;
      case "BACK_RIGHT" :moduleBackRight .set(degrees, power);break;
    }
  }
	public double getAbeNW(){ return abeNW.get(); }
	public double getAbeNE(){ return abeNE.get(); }
	public double getAbeSW(){ return abeSW.get(); }
	public double getAbeSE(){ return abeSE.get(); }
  public void setAllAngle(double degrees){
    moduleFrontLeft.setSteeringDegrees(degrees); moduleFrontRight.setSteeringDegrees(degrees);
    moduleBackLeft.setSteeringDegrees(degrees);  moduleBackRight.setSteeringDegrees(degrees);
  }
  public void setAllPower(double power){
    moduleFrontLeft.setDrivePower(power); moduleFrontRight.setDrivePower(power);
    moduleBackLeft.setDrivePower(power);  moduleBackRight.setDrivePower(power);
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
