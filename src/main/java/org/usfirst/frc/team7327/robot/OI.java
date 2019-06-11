package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class OI{
    private static final double DEADZONE_LIMIT = 0.2;
    public final XboxController Controller0 = new XboxController(0), Controller1 = new XboxController(1);

    public OI(){}
    public double getLeftMagnitude(XboxController Controller){ return Math.hypot(Controller.getRawAxis(1), Controller.getRawAxis(0)); }
    public double getRightMagnitude(XboxController Controller){ return Math.hypot(Controller.getRawAxis(4), Controller.getRawAxis(5)); }

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
}
