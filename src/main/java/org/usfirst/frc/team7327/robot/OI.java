package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.XboxController;

import org.usfirst.frc.team7327.robot.Constants;

public class OI{
    
    private static final double DEADZONE_LIMIT = 0.2;
    
    public final XboxController Controller0 = new XboxController(0);

    public OI(){
    }
    /*
    public double getLeftAngle(){
        return Controller0.getDirectionDegrees();
    }
    */

    public double getLeftJoystickAngle(){
        return Math.toDegrees(Math.atan2(Controller0.getRawAxis(0), -Controller0.getRawAxis(1)));
    }

    public double getLeftXAxis(){
        return Math.abs(Controller0.getRawAxis(0)) > 0.1 ? Controller0.getRawAxis(0) : 0;
    }

    public double getLeftYAxis(){
        /*
        double val = ((-stick.getRawAxis(1)) + Constants.kLeftYOffset) * (1.0/(1.0-Constants.kLeftYOffset));
        val = val > 1.0 ? 1.0 : val;
        val = val < -1.0 ? -1.0 : val;
        val = Math.abs(val) < .05 ? 0 : val;
        return val;*/
        return Math.abs(Controller0.getRawAxis(1)) > 0.1 ? Controller0.getRawAxis(1) : 0;
    }

    public double getRightXAxis(){
        return (Math.abs(Controller0.getRawAxis(4)) > 0.1 ? Controller0.getRawAxis(4) : 0) * 0.25;
    }

    public double getLeftMagnitude(){
        return Math.hypot(Controller0.getRawAxis(1), Controller0.getRawAxis(0));
    }
}
