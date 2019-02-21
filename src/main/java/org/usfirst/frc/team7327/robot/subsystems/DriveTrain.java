package org.usfirst.frc.team7327.robot.subsystems;

import org.usfirst.frc.team7327.robot.ElevatorModule;
import org.usfirst.frc.team7327.robot.SwerveModule;
import org.usfirst.frc.team7327.robot.TurnModule;
import org.usfirst.frc.team7327.robot.commands.SwerveDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;


public class DriveTrain extends Subsystem {
	
	private SwerveModule moduleNE, moduleNW, moduleSE, moduleSW;
	private DoubleSolenoid Bicep; 
	private static ElevatorModule Elevator;
	private static VictorSPX BallVictor;
	//public static VictorSPX Intake;
	private static TalonSRX Intake; 
	public TurnModule turning; 
	
	static final double kP = 2.5, kI = 0, kD = 0;
	static final double tkP = .4, tkI = .000001, tkD = .04; //tKP .4 cement , .6 carpet tkD .04 cement , .05 carpet
	static final double ekP = .0004, ekI = 0, ekD = 0; 
	
	//larger negative degree rotates counter-clockwise
	//Harbi
	public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -235.2); 
	public static Potentiometer abeNE = new AnalogPotentiometer(3, 360, -207.85);
	public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, -235.3); 
	public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, -9.7);
	//Discovery
	//public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, -94.2); 
	//public static Potentiometer abeNE = new AnalogPotentiometer(3, 360, -201.85);
	//public static Potentiometer abeSW = new AnalogPotentiometer(1, 360, -240.3); 
	//public static Potentiometer abeSE = new AnalogPotentiometer(2, 360, -233.7); 
	
	public DriveTrain() {
		
		moduleNW = new SwerveModule(1, 0, abeNW, kP, kI, kD,false);
		moduleNE = new SwerveModule(3, 2, abeNE, kP, kI, kD,true);
		moduleSW = new SwerveModule(5, 4, abeSW, kP, kI, kD,false);
		moduleSE = new SwerveModule(7, 6, abeSE, kP, kI, kD,true);

		turning = new TurnModule(tkP, tkI, tkD);
 
		Elevator = new ElevatorModule(8, ekP, ekI, ekD); 
		//Discovery
		//Intake = new VictorSPX(9); 
		//Harbi
		Intake = new TalonSRX(9); 
		BallVictor = new VictorSPX(10); 

		Bicep = new DoubleSolenoid(1,2);

	}

	public void setRawBicep(DoubleSolenoid.Value Flex){ Bicep.set(Flex); }

	public double getAbeNW(){ return abeNW.get(); }
	public double getAbeNE(){ return abeNE.get(); }
	public double getAbeSW(){ return abeSW.get(); }
	public double getAbeSE(){ return abeSE.get(); }
	
	public void setAllSpeed(double speed) {
		moduleNE.setDrive(speed); moduleNW.setDrive(speed);
		moduleSE.setDrive(speed); moduleSW.setDrive(speed);
	} 
	public void setEachSpeed(double sNW, double sNE, double sSW, double sSE) {
		moduleNE.setDrive(sNE); moduleNW.setDrive(sNW);
		moduleSE.setDrive(sSE); moduleSW.setDrive(sSW);
	}
	public void setAllDegrees(double deg) {
		moduleNE.setSteeringDeg(deg); moduleNW.setSteeringDeg(deg);
		moduleSE.setSteeringDeg(deg); moduleSW.setSteeringDeg(deg);
	}
	public void setEachDegree(double NE, double NW, double SE, double SW ) {
		moduleNE.setSteeringDeg(NE); moduleNW.setSteeringDeg(NW);
		moduleSE.setSteeringDeg(SE); moduleSW.setSteeringDeg(SW);
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

	@Override
	protected void initDefaultCommand() { setDefaultCommand(new SwerveDrive()); }

}
