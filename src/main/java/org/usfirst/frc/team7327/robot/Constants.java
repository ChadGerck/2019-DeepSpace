package org.usfirst.frc.team7327.robot;

public class Constants{
    //Motors
    public static final int kFrontLeftSteerID  = 0, kFrontLeftDriveID  = 1, 
                            kFrontRightSteerID = 2, kFrontRightDriveID = 3, 
                            kBackLeftSteerID   = 4, kBackLeftDriveID   = 5, 
                            kBackRightSteerID  = 6, kBackRightDriveID  = 7;
    //Module-Specific
    public static final double kFrontLeftOffset = 31, kFrontRightOffset = -46, kBackLeftOffset = -98, kBackRightOffset = 153;
    //PID
    public static final double kSwerveP = 4.8, kSwerveI = 0.0, kSwerveD = 0.0;
    public static final double tkP = 2, tkI = .0, tkD = .1;
    public static final double ekP = .0004, ekI = 0, ekD = 0; 

}

