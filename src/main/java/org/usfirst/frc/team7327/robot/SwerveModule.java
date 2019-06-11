package org.usfirst.frc.team7327.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import org.usfirst.frc.team7327.robot.Util.WrappedTalonSRX;
import org.usfirst.frc.team7327.robot.Util.WrappedVictorSPX;

public class SwerveModule{
    private WrappedTalonSRX mDrive; 
    private WrappedVictorSPX mSteering;
    private Notifier pidLoop;          
    private volatile double currentError, pidOutput;
    private double setpoint, lastAngle;
    private static final double dt = 0.05;  
    private Potentiometer steeringEncoder;
    /**
     * @param kSteeringID   the ID of the steering motor
     * @param kDriveID      the ID of the drive motor
     * @param SteeringEncoder the AbsoluteEncoder for SwerveDrive
     * @param kP            the steering kP gain
     * @param kI            the steering kI gain
     * @param kD            the steering kD gain
     */
    public SwerveModule(int kSteeringID, int kDriveID, Potentiometer steeringEncoder, double kP){
        mDrive = new WrappedTalonSRX(kDriveID);
        mSteering = new WrappedVictorSPX(kSteeringID);
        mDrive.setNeutralMode(NeutralMode.Coast); 
        lastAngle = 0;
        this.steeringEncoder = steeringEncoder;
        mDrive.reset(); mSteering.reset();
        pidLoop = new Notifier(() -> {
            currentError = getModifiedError();  
            pidOutput = kP * currentError;
            mSteering.set(ControlMode.PercentOutput, pidOutput);
        });
        pidLoop.startPeriodic(dt);
    }
    public double getError(){ return setpoint - getSteeringEncoder(); }
    public double getModifiedError(){ return (boundHalfDegrees(getError()))/180; }
    public void setDrivePower(double power){ mDrive.set(ControlMode.PercentOutput, power); }
    public void setSteeringDegrees(double deg){ setpoint = boundHalfDegrees(deg); }
    public double getSetpointDegrees(){ return setpoint; }
    public void set(double degrees, double power){
        double supplement = degrees > 0 ? degrees - 180 : 180 + degrees;
        if(Math.abs(supplement-lastAngle) <= 90){
            setSteeringDegrees(supplement);
            setDrivePower(-power);
            lastAngle = supplement;
        }
        else {
            setSteeringDegrees(degrees);
            setDrivePower(power);
            lastAngle = degrees;
        }
    }
    public static double boundHalfDegrees(double angle_degrees) {
        while (angle_degrees >= 180.0) angle_degrees -= 360.0;
        while (angle_degrees < -180.0) angle_degrees += 360.0;
        return angle_degrees;
    }
    public double getSteeringEncoder(){
        double angle = steeringEncoder.get(); 
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360; 
		return angle; 
    }
}
