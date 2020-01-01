package org.usfirst.frc.team7327.robot;
import edu.wpi.first.wpilibj.Notifier;

public class TurnModule{
    private Notifier TurningPID; 
    private double error, diffError, lastError, navTo; 
    public static double PIDOutput = 0;
    static final double kP = 1, kD = .1; //l
    public TurnModule() {
    	lastError = getError(); //l
    	TurningPID = new Notifier(() ->  {
    		error = getError(); 
    		diffError = lastError - error; //l
            PIDOutput = kP * error + kD * diffError; //l
            PIDOutput = Math.min(PIDOutput, .5);
            PIDOutput = Math.max(PIDOutput, -.5); 
            lastError = error; //l
    	}); 
    	TurningPID.startPeriodic(0.05);
    }
    public double getError(){double navFinal = boundHalfDegrees(navTo)/180; return navFinal;}	
	public static double boundHalfDegrees(double angle){while(angle>=180)angle-=360; while(angle<-180)angle+=360; return angle;}
    public void setYaw(double degree){navTo = degree;}
    public double getPIDOutput(){return PIDOutput;}
}

