package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {
        Robot.MoveForward(1.5);
        Robot.TurnRight();
        Robot.MoveForward(0.5); 
    }
}

