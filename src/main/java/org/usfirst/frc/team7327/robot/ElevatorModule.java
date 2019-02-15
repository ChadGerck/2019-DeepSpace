package org.usfirst.frc.team7327.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;

public class ElevatorModule{

    private TalonSRX mLift; 
    private Notifier SteeringPID;
    private double error, sumError, diffError, lastError;
    private double setPoint;
    private double PIDOutput;
    public ElevatorModule(int kDriveID, double kP, double kI, double kD) {
        mLift = new TalonSRX(kDriveID);
        sumError = 0;
        lastError = getError();
        error = lastError;
        SteeringPID = new Notifier(() -> {
        	error = getError();
            diffError = lastError - getError();
            sumError += getError();
            PIDOutput = kP * getError() + kI * sumError + kD * diffError;
            mLift.set(ControlMode.PercentOutput, -PIDOutput);
            
            lastError = error;
        });
        SteeringPID.startPeriodic(0.01);
        mLift.enableVoltageCompensation(true);
		mLift.setNeutralMode(NeutralMode.Brake);
    }

    public double getError(){
        return setPoint - getLiftPosition();
    }

    public void setPosition(double pow){
        mLift.set(ControlMode.PercentOutput, pow);
    }

    
	public double getLiftVelocity() { return mLift.getSelectedSensorVelocity(0); }
	public double getLiftPosition() { return mLift.getSelectedSensorPosition(0); }
    
    
	public void setTalonStatus()       { mLift.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);       }
	public void configFeedbackSensor() { mLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative); }

  
    public double getSteeringSetpoint() { return setPoint; }

}