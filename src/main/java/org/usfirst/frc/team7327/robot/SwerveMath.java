package org.usfirst.frc.team7327.robot;

import static org.usfirst.frc.team7327.robot.Robot.kDrivetrain;

public class SwerveMath {
    static double wheelXcos, wheelYsin, FLwheelX, FLwheelY, FRwheelX, FRwheelY, BLwheelX, BLwheelY, BRwheelX, BRwheelY, max, 
    FLwheelMag, FRwheelMag, BLwheelMag, BRwheelMag, FLwheelRot, FRwheelRot, BLwheelRot, BRwheelRot, leftX, leftY, rightX, rightY, leftMag, rightMag;

    public static void ComputeSwerve(double finalAngle,double directMag, double rotMag, Boolean fixRotation) {

        wheelXcos = Math.cos(finalAngle / 57.2957795) * directMag;
        wheelYsin = Math.sin(finalAngle / 57.2957795) * directMag;

        FLwheelX = wheelXcos + (.70710678 * -rotMag);
        FLwheelY = wheelYsin + (.70710678 *  rotMag);
        if (fixRotation) { FLwheelRot = Math.atan2(FLwheelY, FLwheelX) * 57.2957795; }
        FLwheelMag = Math.hypot(FLwheelX, FLwheelY);

        FRwheelX = wheelXcos + (.70710678 * rotMag);
        FRwheelY = wheelYsin + (.70710678 * rotMag);
        if (fixRotation) { FRwheelRot = Math.atan2(FRwheelY, FRwheelX) * 57.2957795; }
        FRwheelMag = Math.hypot(FRwheelX, FRwheelY);

        BLwheelX = wheelXcos + (.70710678 * -rotMag);
        BLwheelY = wheelYsin + (.70710678 * -rotMag);
        if (fixRotation) { BLwheelRot = Math.atan2(BLwheelY, BLwheelX) * 57.2957795; }
        BLwheelMag = Math.hypot(BLwheelX, BLwheelY);

        BRwheelX = wheelXcos + (.70710678 * rotMag);
        BRwheelY = wheelYsin + (.70710678 * -rotMag);
        if (fixRotation) { BRwheelRot = Math.atan2(BRwheelY, BRwheelX) * 57.2957795; }
        BRwheelMag = Math.hypot(BRwheelX, BRwheelY);

        max = FLwheelMag;
        if (FRwheelMag > max) { max = FRwheelMag; } 
        else if (BLwheelMag > max) { max = BLwheelMag; } else if (BRwheelMag > max) { max = BRwheelMag; }
        if (max > 1) { FLwheelMag /= max; FRwheelMag /= max; BLwheelMag /= max; BRwheelMag /= max; }

        kDrivetrain.setModule("FRONT_LEFT" , FLwheelRot, FLwheelMag);
        kDrivetrain.setModule("FRONT_RIGHT", FRwheelRot, FRwheelMag);
        kDrivetrain.setModule("BACK_LEFT"  , BLwheelRot, BLwheelMag);
        kDrivetrain.setModule("BACK_RIGHT" , BRwheelRot, BRwheelMag);
    }
}