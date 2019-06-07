package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class OI{
    
    private static final double DEADZONE_LIMIT = 0.2;
    
    public final XboxController Controller0 = new XboxController(0);
    public final XboxController Controller1 = new XboxController(1);

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
        return (Math.abs(Controller0.getRawAxis(4)) > 0.3 ? Controller0.getRawAxis(4) : 0);
    }

    public double getRightYAxis(){
        return (Math.abs(Controller0.getRawAxis(5)) > 0.1 ? Controller0.getRawAxis(5) : 0);
    }

    public double getLeftMagnitude(){
        return Math.hypot(Controller0.getRawAxis(1), Controller0.getRawAxis(0));
    }
    public double getRightMagnitude(){
        return Math.hypot(Controller0.getRawAxis(4), Controller0.getRawAxis(5));
    }

    public double LeftStickX  (XboxController Controller){ double raw = Controller.getRawAxis(0); return Math.abs(raw) < DEADZONE_LIMIT ? 0.0 : raw; }
    public double LeftStickY  (XboxController Controller){ double raw = Controller.getRawAxis(1); return Math.abs(raw) < DEADZONE_LIMIT ? 0.0 : raw; }
    public double RightStickX (XboxController Controller){ double raw = Controller.getRawAxis(4); return Math.abs(raw) < DEADZONE_LIMIT ? 0.0 : raw; }
    public double RightStickY (XboxController Controller){ double raw = Controller.getRawAxis(5); return Math.abs(raw) < DEADZONE_LIMIT ? 0.0 : raw; }
    public double LeftTrigger (XboxController Controller){ double raw = Controller.getRawAxis(2); return Math.abs(raw) < DEADZONE_LIMIT ? 0.0 : raw; }
    public double RightTrigger(XboxController Controller){ double raw = Controller.getRawAxis(3); return Math.abs(raw) < DEADZONE_LIMIT ? 0.0 : raw; }
    
    public boolean LeftBumper     (XboxController Controller){ return Controller.getBumperPressed(Hand.kLeft); }
    public boolean LeftBumperDown (XboxController Controller){ return Controller.getBumper(Hand.kLeft); }
    public boolean RightBumper    (XboxController Controller){ return Controller.getBumperPressed(Hand.kRight); }
    public boolean RightBumperDown(XboxController Controller){ return Controller.getBumper(Hand.kRight); }
    public boolean AButton        (XboxController Controller){ return Controller.getAButtonPressed(); }
    public boolean AButtonDown    (XboxController Controller){ return Controller.getAButton(); }
    public boolean BButton        (XboxController Controller){ return Controller.getBButtonPressed(); }
    public boolean BButtonDown    (XboxController Controller){ return Controller.getBButton(); }
    public boolean XButton        (XboxController Controller){ return Controller.getXButtonPressed(); }
    public boolean XButtonDown    (XboxController Controller){ return Controller.getXButton(); }
    public boolean YButton        (XboxController Controller){ return Controller.getYButtonPressed(); }
    public boolean YButtonDown    (XboxController Controller){ return Controller.getYButton(); }
    public boolean StartButton    (XboxController Controller){ return Controller.getStartButtonPressed(); }
    public boolean BackButton     (XboxController Controller){ return Controller.getRawButtonPressed(7); }
    public boolean LSClick        (XboxController Controller){ return Controller.getStickButtonPressed(Hand.kLeft); }
    public boolean RSClick        (XboxController Controller){ return Controller.getStickButtonPressed(Hand.kRight); }
    public double  Dpad           (XboxController Controller){ return Controller.getPOV(); }
    
    public boolean DpadUp(XboxController Controller){
        double POV = Controller.getPOV(); 
        if((POV >= 0 && POV < 45) || (POV >= 315 && POV < 360)) { return true; }
        else { return false; }
    }
    public boolean DpadRight(XboxController Controller){
        double POV = Controller.getPOV(); 
        if(POV >= 45 && POV < 135) { return true; }
        else { return false; }
    }
    public boolean DpadDown(XboxController Controller){
        double POV = Controller.getPOV(); 
        if(POV >= 135 && POV < 225) { return true; }
        else { return false; }
    }
    public boolean DpadLeft(XboxController Controller){
        double POV = Controller.getPOV(); 
        if(POV >= 225 && POV < 315) { return true; }
        else { return false; }
    }
    /*
    public boolean LeftStickPressed(XboxController controller){
        return Controller.getRawButtonPressed(9); 
    }
    */

}
