package org.usfirst.frc.team7327.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team7327.robot.subsystems.DriveTrain;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;

public class Robot extends TimedRobot {
  public static final DriveTrain kDrivetrain = new DriveTrain();
  public static final OI oi = new OI();
  public static AHRS nav; 
  public boolean flag = true; 
  Compressor c0 = new Compressor(0);
  
  private static final String 
  kAuto = "Auto", kAuto2 = "Auto2", kAuto3 = "Auto3", kAuto4 = "Auto4", 
  kP11 = "A1.1", kP12 = "A1.2", kP13 = "A1.3", kP14 = "A1.4", 
  kP21 = "A2.1", kP22 = "A2.2", kP23 = "A2.3", kP24 = "A2.4",
  kP31 = "A3.1", kP32 = "A3.2", kP33 = "A3.3", kP34 = "A3.4",
  kP41 = "A4.1", kP42 = "A4.2", kP43 = "A4.3", kP44 = "A4.4";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final SendableChooser<String> m_chosen = new SendableChooser<>();
  @Override public void robotInit() { nav = new AHRS(I2C.Port.kMXP); 
    CameraServer.getInstance().startAutomaticCapture();
    c0.setClosedLoopControl(true); 
    
    m_chooser.setDefaultOption("Auto", kAuto); m_chooser.addOption("Auto2", kAuto2);
    m_chooser.addOption("Auto3", kAuto3); m_chooser.addOption("Auto4", kAuto4);
    SmartDashboard.putData("Auto choices", m_chooser);
  }
  @Override public void robotPeriodic() { 
    kDrivetrain.updateDashboard(); 
    m_autoSelected = m_chooser.getSelected();
    switch(m_autoSelected){
      case "Auto": 
        m_chosen.addOption("P1.1", kP11); m_chosen.addOption("P1.2", kP12);
        m_chosen.addOption("P1.3", kP13); m_chosen.addOption("P1.4", kP14);
      case "Auto2": 
        m_chosen.addOption("P1.1", kP21); m_chosen.addOption("P1.2", kP22);
        m_chosen.addOption("P1.3", kP23); m_chosen.addOption("P1.4", kP24);
      case "Auto3":
        m_chosen.addOption("P1.1", kP31); m_chosen.addOption("P1.2", kP32);
        m_chosen.addOption("P1.3", kP33); m_chosen.addOption("P1.4", kP34);
      case "Auto4":
        m_chosen.addOption("P1.1", kP41); m_chosen.addOption("P1.2", kP42);
        m_chosen.addOption("P1.3", kP43); m_chosen.addOption("P1.4", kP44);
    }
    SmartDashboard.putData("Auto chosen", m_chosen);
    SmartDashboard.putString("the chosen one: ", m_chosen.getSelected()); 
  }
  @Override public void teleopInit() { kDrivetrain.SetElevatorStatus(); kDrivetrain.ConfigElevator(); }
  @Override public void autonomousInit() { nav.reset(); 
    
  }
  @Override public void autonomousPeriodic() { Scheduler.getInstance().run(); }
  @Override public void teleopPeriodic() { Scheduler.getInstance().run();
    if(oi.LSClick(oi.Controller1)){
      if(flag){ c0.setClosedLoopControl(false); flag = false; }
      else{ c0.setClosedLoopControl(true); flag = true; }
    }
  }
  @Override public void testPeriodic() {}
  public static double NavAngle() {return NavAngle(0);}
  public static double NavAngle(double add){double angle = Robot.nav.getAngle()+add;
    while(angle>360)angle-=360;while(angle<0)angle+=360;return angle; 
	}
}
