package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnModule{
    private Notifier TurningPID; 
    private double error, sumError, diffError, lastError;
    public static double PIDOutput = 0;
    private double testPIDOutput; 
    private double navTo; 
    private boolean on; 
    
    public TurnModule(double kP, double kI, double kD) {
    	sumError = 0; 
    	lastError = getError(); 
    	error = lastError; 
    	TurningPID = new Notifier(() ->  {
    		SmartDashboard.putNumber("navTo: ", navTo);
    		SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle());
    		error = getError(); 
    		diffError = lastError - getError(); 
    		sumError += getError(); 
            testPIDOutput = kP * getError() + kI * sumError + kD * diffError; 
            testPIDOutput = Math.min(1, testPIDOutput); 
            testPIDOutput = Math.max(-1, testPIDOutput); 
            PIDOutput = testPIDOutput; 
            lastError = error;
    	}); 
    	TurningPID.startPeriodic(0.01);
    	
    }

    public double getError(){
    	double navFinal = navTo - Robot.NavAngle(); 
    	if(navFinal <= 0 ) navFinal += 360; 
        return Math.sin(Math.toRadians(navFinal));
    }
    
    public void setYaw(double degree){ navTo = degree; }
    
    public boolean setOn(boolean flipOn) { 
    	on = flipOn; 
    	return on; 
    }

    public double getPIDOutput(){ return PIDOutput; }

}