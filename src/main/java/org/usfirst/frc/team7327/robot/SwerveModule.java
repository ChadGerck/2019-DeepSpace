package org.usfirst.frc.team7327.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class SwerveModule{
    private CANSparkMax m_motor;
    private VictorSPX mSteering;
    private Notifier pidLoop;          
    private volatile double currentError, pidOutput;
    private double setpoint, lastAngle;
    private static final double dt = 0.05;  
    private Potentiometer steeringEncoder;
    private Boolean isFlipped; 
    /**
     * @param kSteeringID   the ID of the steering motor
     * @param kDriveID      the ID of the drive motor
     * @param SteeringEncoder the AbsoluteEncoder for SwerveDrive
     * @param kP            the steering kP gain
     */
    public SwerveModule(int kSteeringID, int kDriveID, Potentiometer steeringEncoder, double kP, boolean isFlipped){
        m_motor = new CANSparkMax(kDriveID, MotorType.kBrushless);
        mSteering = new VictorSPX(kSteeringID);
        lastAngle = 0;
        this.steeringEncoder = steeringEncoder;
        pidLoop = new Notifier(() -> {
            currentError = getModifiedError();  
            pidOutput = kP*0.1 * currentError;
            mSteering.set(ControlMode.PercentOutput, pidOutput);
        });
        pidLoop.startPeriodic(dt);
        this.isFlipped = isFlipped; 
    }
    public double getError(){return setpoint - getSteeringEncoder();}
    public double getModifiedError(){return (boundHalfDegrees(getError()))/180;}
    public void setDrivePower(double power){
    	if(isFlipped) m_motor.set(-power);
        else          m_motor.set(power);
    }
    public void setSteeringDegrees(double deg){setpoint = boundHalfDegrees(deg);}
    public double getSetpointDegrees(){return setpoint;}
    public void set(double degrees, double power){
        double supplement = degrees > 0 ? degrees-180 : 180+degrees;
        if(Math.abs(supplement-lastAngle) <= 90){
            setSteeringDegrees(supplement); setDrivePower(-power);
            lastAngle = supplement;
        }else {setSteeringDegrees(degrees); setDrivePower(power); 
            lastAngle = degrees;
        }
    }
    public static double boundHalfDegrees(double angle){while(angle>=180)angle-=360;while(angle<-180)angle+=360; return angle;}
    public double getSteeringEncoder(){
        double angle=steeringEncoder.get();while(angle>360)angle-=360;while(angle<0)angle+=360;return angle; 
    }
}
