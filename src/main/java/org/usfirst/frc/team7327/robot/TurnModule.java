package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.Notifier;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnModule{
    private Notifier TurningPID; 
    private double error, sumError, diffError, lastError;
    private double testPIDOutput; 
    public static double PIDOutput = 0;
    private double navTo; 
    private boolean on; 
    
    public TurnModule(double kP, double kI, double kD) {
    	sumError = 0; 
    	lastError = getError(); 
        error = lastError; 
        
        
    	TurningPID = new Notifier(() ->  {
    		error = getError(); 
    		diffError = lastError - getError(); 
    		sumError += getError(); 
            testPIDOutput = kP * getError() + kI * sumError + kD * diffError;
            testPIDOutput = Math.min(testPIDOutput, .6);
            PIDOutput = Math.max(testPIDOutput, -.6); 
            lastError = error;
    	}); 
    	TurningPID.startPeriodic(0.05);
    	
    }

    public double getError(){
		//Why does subtracting the Robot.NavAngle() crash the driver station. 
		//Who cares we can just subtract Robot.NavAngle() while setting Yaw in Drive. 
    	double navFinal = boundHalfDegrees(navTo)/180; //Math.pow(boundHalfDegrees(navTo)/180, 1/3); // - Robot.NavAngle(); 
        return navFinal;
    }
	
	public static double boundHalfDegrees(double angle_degrees) {
        while (angle_degrees >= 180.0) angle_degrees -= 360.0;
        while (angle_degrees < -180.0) angle_degrees += 360.0;
        return angle_degrees;
    }

    public void setYaw(double degree){ navTo = degree; SmartDashboard.putNumber("navTo", navTo); }
    
    public boolean setOn(boolean flipOn) { 
    	on = flipOn; 
    	return on; 
    }

    public double getPIDOutput(){ return PIDOutput; }

}