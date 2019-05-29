package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Util.DriveCommand;
import org.usfirst.frc.team7327.robot.Util.ModuleLocation;

import static org.usfirst.frc.team7327.robot.Robot.kDrivetrain;

public class SwerveMath {

    double wheelXcos, wheelYsin, FLwheelX, FLwheelY, FRwheelX, FRwheelY, BLwheelX, BLwheelY, BRwheelX, BRwheelY,
            max = 0;

    double FLwheelMag, FRwheelMag, BLwheelMag, BRwheelMag = 0;

    double finalAngle, FLwheelRot, FRwheelRot, BLwheelRot, BRwheelRot = 0;
    int rotAngBR = 135, rotAngBL = 45, rotAngFR = -135, rotAngFL = -45;

    double leftX, leftY, rightX, rightY, leftMag, rightMag, directMag = 0;

    public SwerveMath(double finalAngle, double directMag, double rotMag, boolean fixRotation) {

        wheelXcos = Math.cos(finalAngle / 57.2957795) * directMag;
        wheelYsin = Math.sin(finalAngle / 57.2957795) * directMag;

        FLwheelX = wheelXcos + Math.cos(rotAngFL / 57.2957795) * -rotMag;
        FLwheelY = wheelYsin + Math.sin(rotAngFL / 57.2957795) * -rotMag;
        if (fixRotation) { FLwheelRot = Math.atan2(FLwheelY, FLwheelX) * 57.2957795; }
        FLwheelMag = Math.hypot(FLwheelX, FLwheelY);

        FRwheelX = wheelXcos + Math.cos(rotAngFR / 57.2957795) * -rotMag;
        FRwheelY = wheelYsin + Math.sin(rotAngFR / 57.2957795) * -rotMag;
        if (fixRotation) {
            FRwheelRot = Math.atan2(FRwheelY, FRwheelX) * 57.2957795;
        }
        FRwheelMag = Math.hypot(FRwheelX, FRwheelY);

        BLwheelX = wheelXcos + Math.cos(rotAngBL / 57.2957795) * -rotMag;
        BLwheelY = wheelYsin + Math.sin(rotAngBL / 57.2957795) * -rotMag;
        if (fixRotation) {
            BLwheelRot = Math.atan2(BLwheelY, BLwheelX) * 57.2957795;
        }
        BLwheelMag = Math.hypot(BLwheelX, BLwheelY);

        BRwheelX = wheelXcos + Math.cos(rotAngBR / 57.2957795) * -rotMag;
        BRwheelY = wheelYsin + Math.sin(rotAngBR / 57.2957795) * -rotMag;
        if (fixRotation) {
            BRwheelRot = Math.atan2(BRwheelY, BRwheelX) * 57.2957795;
        }
        BRwheelMag = Math.hypot(BRwheelX, BRwheelY);
        max = FLwheelMag;

        if (FRwheelMag > max) {
            max = FRwheelMag;
        } else if (BLwheelMag > max) {
            max = BLwheelMag;
        } else if (BRwheelMag > max) {
            max = BRwheelMag;
        }
        if (max > 1) {
            FLwheelMag /= max;
            FRwheelMag /= max;
            BLwheelMag /= max;
            BRwheelMag /= max;
        }

        DriveCommand frontLeftCommand = new DriveCommand(FLwheelRot, FLwheelMag);
        DriveCommand frontRightCommand = new DriveCommand(FRwheelRot, FRwheelMag);
        DriveCommand backLeftCommand = new DriveCommand(BLwheelRot, BLwheelMag);
        DriveCommand backRightCommand = new DriveCommand(BRwheelRot, BRwheelMag);

        kDrivetrain.setModule(ModuleLocation.FRONT_LEFT, frontLeftCommand);
        kDrivetrain.setModule(ModuleLocation.FRONT_RIGHT, frontRightCommand);
        kDrivetrain.setModule(ModuleLocation.BACK_LEFT, backLeftCommand);
        kDrivetrain.setModule(ModuleLocation.BACK_RIGHT, backRightCommand);
    }

}