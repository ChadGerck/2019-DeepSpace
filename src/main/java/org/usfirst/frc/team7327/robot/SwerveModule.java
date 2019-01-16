package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class SwerveModule{
    private Talon mDrive, mSteer;
    private Notifier SteeringPID;
    private double error, sumError, diffError, lastError;
    private double setPoint;
    private double PIDOutput;
    private Potentiometer steeringEncoder;
    private boolean isFlipped;
    public SwerveModule(int kDriveID, int kSteerID, Potentiometer steeringEncoder, double kP, double kI, double kD,
            boolean isFlipped) {
        mDrive = new Talon(kDriveID);
        mSteer = new Talon(kSteerID);
        this.steeringEncoder = steeringEncoder;
        sumError = 0;
        lastError = getError();
        error = lastError;
        SteeringPID = new Notifier(() -> {
        	error = getError();
            diffError = lastError - getError();
            sumError += getError();
            PIDOutput = kP * getError() + kI * sumError + kD * diffError;
            mSteer.set(PIDOutput);
            lastError = error;
        });
        SteeringPID.startPeriodic(0.01);
        this.isFlipped = isFlipped;
        //mDrive.enableVoltageCompensation(true);
        //mSteer.enableVoltageCompensation(true);
    }

    public double getError(){
        return Math.sin(Math.toRadians(setPoint - getSteeringEncoder()));
    }

    public double getSteeringEncoder(){
        double angle = steeringEncoder.get();
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360; 
		return angle; 
    }

    public void setDrive(double pow){
    	if(isFlipped) mDrive.set(-pow);
    	else          mDrive.set(pow);
    }

    public void setSteeringDeg(double deg) { setPoint = deg; }
    
  
    public double getSteeringSetpoint() { return setPoint; }

}