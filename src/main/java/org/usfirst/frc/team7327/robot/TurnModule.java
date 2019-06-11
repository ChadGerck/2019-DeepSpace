package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.Notifier;

public class TurnModule{
    private Notifier TurningPID; 
    private double error, sumError, diffError, lastError, testPIDOutput, navTo; 
    public static double PIDOutput = 0;
    static final double kP = 2, kI = .0, kD = .1;
    public TurnModule() {
    	sumError = 0; 
    	lastError = getError(); 
        error = lastError; 
    	TurningPID = new Notifier(() ->  {
    		error = getError(); 
    		diffError = lastError - getError(); 
    		sumError += getError(); 
            testPIDOutput = kP * getError() + kI * sumError + kD * diffError;
            testPIDOutput = Math.min(testPIDOutput, .5);
            PIDOutput = Math.max(testPIDOutput, -.5); 
            lastError = error;
    	}); 
    	TurningPID.startPeriodic(0.05);
    }
    public double getError(){ double navFinal = boundHalfDegrees(navTo)/180; return navFinal; }	
	public static double boundHalfDegrees(double angle_degrees) {
        while (angle_degrees >= 180.0) angle_degrees -= 360.0;
        while (angle_degrees < -180.0) angle_degrees += 360.0;
        return angle_degrees;
    }
    public void setYaw(double degree){ navTo = degree; }
    public double getPIDOutput(){ return PIDOutput; }
}