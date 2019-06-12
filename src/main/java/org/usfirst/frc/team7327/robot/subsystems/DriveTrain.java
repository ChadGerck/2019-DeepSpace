package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.usfirst.frc.team7327.robot.ElevatorModule;
import org.usfirst.frc.team7327.robot.commands.Drive;
import org.usfirst.frc.team7327.robot.SwerveModule;
import org.usfirst.frc.team7327.robot.TurnModule;

public class DriveTrain extends Subsystem {
  public TurnModule turning; 
  public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -139),  
                              abeSW = new AnalogPotentiometer(1, 360, -22), abeSE = new AnalogPotentiometer(2, 360, -48); 
  double kSwerveP = 4.8; 
  private SwerveModule moduleNW= new SwerveModule(0,1, abeNW, kSwerveP), 
                       moduleSW= new SwerveModule(4,5, abeSW, kSwerveP), moduleSE= new SwerveModule(6,7, abeSE, kSwerveP);
  //public static ElevatorModule Elevator;
  private DoubleSolenoid Pincher, Extender; 
  public static VictorSPX BallVictor, Intake;
  public TalonSRX Elevatorm1, Elevatorm2; 
  public DriveTrain(){
    turning = new TurnModule(); 
    Pincher = new DoubleSolenoid(0,1); Extender = new DoubleSolenoid(2,3);
    Elevatorm1 = new TalonSRX(17); Elevatorm2 = new TalonSRX(18); 
  }
  @Override public void initDefaultCommand() { setDefaultCommand(new Drive()); }
  public void setModule(String loc,double degrees,double power){
    switch(loc){case "NW":moduleNW.set(degrees,power);break; 
                case "SW":moduleSW.set(degrees,power);break; case "SE":moduleSE.set(degrees,power);break;
    }
  }public double getAbeNW(){ return abeNW.get(); } 
	public  double getAbeSW(){ return abeSW.get(); }
  public  double getAbeSE(){ return abeSE.get(); }
  public void setPincher(DoubleSolenoid.Value value){Pincher.set(value);}
  public void setExtendor(DoubleSolenoid.Value value){Extender.set(value);}
  public void setElevator(double power){ Elevatorm1.set(ControlMode.PercentOutput, power); Elevatorm2.set(ControlMode.PercentOutput, -power);}
  public void setAllAngle(double degrees){ moduleNW.setSteeringDegrees(degrees);
    moduleSW.setSteeringDegrees(degrees); moduleSE.setSteeringDegrees(degrees);
  }public void setAllPower(double power){ moduleNW.setDrivePower(power);
    moduleSW.setDrivePower(power); moduleSE.setDrivePower(power);
  }public void setRawBallIn(double speed){ BallVictor.set(ControlMode.PercentOutput, speed); }
	public void setRawIntake(double intakevalue) { Intake.set(ControlMode.PercentOutput, intakevalue);	} 
  public void updateDashboard(){}
}
