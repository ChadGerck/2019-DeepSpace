package org.usfirst.frc.team7327.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.StatusFrame;
//import com.ctre.phoenix.motorcontrol.StatusFrame;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import org.usfirst.frc.team7327.robot.Util.DriveCommand;
import org.usfirst.frc.team7327.robot.Util.WrappedTalonSRX;
import org.usfirst.frc.team7327.robot.Util.WrappedVictorSPX;
import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class SwerveModule{
    //private AnalogInput SteeringAnalog = new AnalogInput(0);
    //private WrappedTalonSRX mDrive; 
    private WrappedTalonSRX mSteering;
    private Notifier pidLoop;           //A notifier is a thread. Basically think of a thread as something running in the background.
    //private volatile double sumError, errorChange, lastError; 
    private volatile double currentError, pidOutput;
    private boolean isReversed;
    private double setpoint;
    //private double offset;

    private double lastAngle;
    
    private CANSparkMax m_motor;

    private static final double dt = 0.05;  //this is how fast we run our PID loop.
    //private int kPositiveRotationMin = 45;  //we measured this
    //private int kPositiveRotationMax = 870;  //and this

    //private int kNegativeRotationMin = 156;  //we measured this
    //private int kNegativeRotationMax = 978;  //and this

    
    private Potentiometer steeringEncoder;
    
    /**
     * 
     * @param kSteeringID   the ID of the steering motor
     * @param kDriveID      the ID of the drive motor
     * @param SteeringEncoder the AbsoluteEncoder for SwerveDrive
     * @param isReversed    if the module is physically reversed on the robot
     * @param kP            the steering kP gain
     * @param kI            the steering kI gain
     * @param kD            the steering kD gain
     */
    public SwerveModule(int kSteeringID, int kDriveID, Potentiometer steeringEncoder, boolean isReversed, double offset, double kP, double kI, double kD){
        //mDrive = new WrappedTalonSRX(kDriveID);
        mSteering = new WrappedTalonSRX(kSteeringID);
        //mDrive.setNeutralMode(NeutralMode.Coast); 
        
        m_motor = new CANSparkMax(kDriveID, MotorType.kBrushless);
        //this.offset = offset;

        lastAngle = 0;
        
        this.steeringEncoder = steeringEncoder;

        //reset the Talons before use
        //mDrive.reset();
        mSteering.reset();

        //Configure steering Talon SRX
        //mSteering.configSelectedFeedbackSensor(FeedbackDevice.Analog, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        


        //mSteering.configOpenloopRamp(0, Constants.kTimeoutMs);      //this is what we were missing!
        //mSteering.configPeakCurrentDuration(Constants.kPeakCurrentDuration, Constants.kTimeoutMs);
        //mSteering.configPeakCurrentLimit(Constants.kPeakCurrentLimit, Constants.kTimeoutMs);
        //mSteering.configContinuousCurrentLimit(Constants.kSustainedCurrentLimit, Constants.kTimeoutMs);
        //mSteering.enableCurrentLimit(true);
        //mSteering.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 10, 0);
        
        
        //mSteering.setInverted(true);
        //mSteering.setSensorPhase(true);

        //Configure drive Talon SRX
        //mDrive.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

        /**
        sumError = 0;
        lastError = getModifiedError();
        currentError = lastError;
*/
        pidLoop = new Notifier(() -> {
            currentError = getModifiedError();  //update the current error to the most recent one
            /*
            sumError += currentError * dt;
            errorChange = (currentError-lastError)/dt;
*/
            pidOutput = kP * currentError; //+ kI * sumError + kD * errorChange; //you guys know this, or at least you better...
            mSteering.set(ControlMode.PercentOutput, pidOutput);
            //lastError = currentError;   //update the last error to be the current error
        });


        this.isReversed = isReversed;

        pidLoop.startPeriodic(dt);
    }

    /**  the velocity of the wheel, measured in ticks/100ms
     * 
     */
    /*
    public int getDriveEncoder(){
        return (mDrive.getSelectedSensorVelocity(0));
*/
    /**
     *  the angle of the wheel, where angle is an element of [-pi, pi]
     */


/*
    public double getSteeringDegrees(){
        int steeringPosition = mSteering.getSelectedSensorPosition(Constants.kPIDLoopIdx);

        if(steeringPosition >= 0){
            return normalizeEncoder(kPositiveRotationMin, kPositiveRotationMax, steeringPosition)-180;
        }
        else
            return (360-normalizeEncoder(kNegativeRotationMin, kNegativeRotationMax, steeringPosition))-180;



    }
*/
/*
    public double getSteeringDegreesCompensated(){
        return getSteeringEncoder() - offset;
    }
    */
    /**
     * 
     * @return  the closed-loop PID output, calculated by PID loop
     */
    public double getSteeringOutput(){
        return pidOutput;
    }


    /**
     * 
     * @return  the unbounded steering error, in radians
     */
    public double getError(){
        return setpoint - getSteeringEncoder();
    }

    /**
     * 
     * @return  the steering error bounded to [-pi, pi]
     */
    public double getModifiedError(){
        return (boundHalfDegrees(getError()))/180;
    }

    /**
     * 
     * @param power the power of the wheel, where power is [-1.0, 1.0]
     */
    public void setDrivePower(double power){
        if(isReversed)
            m_motor.set(-power);
        else
            m_motor.set(power); 
    }

    /**
     * 
     * @param deg   the angle to set the wheel to, in degrees
     */
    public void setSteeringDegrees(double deg){
        setpoint = boundHalfDegrees(deg);
    }

    /**
     * 
     * @return  returns the setpoint of the sttering in degrees
     */
    public double getSetpointDegrees(){
        return setpoint;
    }

    /**
     *
     * @param encPos    the encoder input to be normalized
     * @param minVal    the minimum MEASURED ABSOLUTE value of the encoder
     * @param maxVal    the maximum MEASURED ABSOLUTE value of the encoder
     * @return          the encoder input normalized to [0, 1023]
     * */
    /*
    private double normalizeEncoder(int minVal, int maxVal, int encPos){
        return ((Math.abs(encPos) % 1023) - minVal) * Math.abs((360.0/(maxVal-minVal)));
    }
*/
    public void setSteeringPower(double x){
        mSteering.set(ControlMode.PercentOutput, x);
    }

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

    public void set(DriveCommand command){
        set(command.getDegrees(), command.getSpeed());
    }

    /*
    public void configEncValues(int posMin, int posMax, int negMin, int negMax){
        kPositiveRotationMin = posMin;
        kPositiveRotationMax = posMax;

        kNegativeRotationMin = negMin;
        kNegativeRotationMax = negMax;
    }
    */

    /*
    public int getRawSteeringEncoder(){
        return mSteering.getSelectedSensorPosition(0);
    }
    */

    /*
    public int getSpeed(){
        return mDrive.getSelectedSensorVelocity(0);
    }
    */

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
