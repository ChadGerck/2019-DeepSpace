package org.usfirst.frc.team7327.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
  public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, 10.8), abeNE = new AnalogPotentiometer(3, 360, 315.2), 
                              abeSW = new AnalogPotentiometer(1, 360, 142.2), abeSE = new AnalogPotentiometer(2, 360, 120.3); 
  double kSwerveP = 4.8; 
  private SwerveModule moduleNW= new SwerveModule(8,9, abeNW, kSwerveP, false);//moduleNE= new SwerveModule(2,3, abeNE, kSwerveP, false),
                       //moduleSW= new SwerveModule(4,5, abeSW, kSwerveP, false), moduleSE= new SwerveModule(6,7, abeSE, kSwerveP, false);
  
  public static ElevatorModule Elevator;
  public static VictorSPX BallVictor, Intake;
  public static DoubleSolenoid Pincher, Extendor, pullout; 
  public DriveTrain(){
    Elevator  = new ElevatorModule(8); Intake = new VictorSPX(9); 
    BallVictor= new VictorSPX(10);    turning = new TurnModule(); 
    Pincher = new DoubleSolenoid(0,3, 4); Extendor = new DoubleSolenoid(0,2, 5);
    pullout = new DoubleSolenoid(1,0,7); 
  }
  @Override public void initDefaultCommand() { setDefaultCommand(new Drive()); }
  public void setModule(String loc,double degrees,double power){
    switch(loc){case "NW":moduleNW.set(degrees,power);break; //case "NE":moduleNE.set(degrees,power);break;
                //case "SW":moduleSW.set(degrees,power);break; case "SE":moduleSE.set(degrees,power);break;
                //case "NW2":moduleNW2.set(degrees,power);break;
    }
  }public SwerveModule getModuleNW(){ return moduleNW;}
  //public  SwerveModule getModuleNE(){ return moduleNE; }
	//public  SwerveModule getModuleSW(){ return moduleSW;}
  //public  SwerveModule getModuleSE(){ return moduleSE; }
  //public  SwerveModule2 getModuleNW2(){ return moduleNW2; }
  public void setPincher(DoubleSolenoid.Value value){ Pincher.set(value); }
  public void setExtendor(DoubleSolenoid.Value value){ Extendor.set(value); }
  public void setPullout(DoubleSolenoid.Value value){ pullout.set(value); }
  
  public void setAllAngle(double degrees){
    moduleNW.setSteeringDegrees(degrees); //moduleNE.setSteeringDegrees(degrees);
    //moduleSW.setSteeringDegrees(degrees); moduleSE.setSteeringDegrees(degrees);
    //moduleNW2.setSteeringDegrees(degrees);
  }public void setAllPower(double power){
    moduleNW.setDrivePower(power); //moduleNE.setDrivePower(power);
    //moduleSW.setDrivePower(power); moduleSE.setDrivePower(power);
    //moduleNW2.setDrivePower(power);
  }public void setRawElevator(double speed){ Elevator.setRawElev(speed); }
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
