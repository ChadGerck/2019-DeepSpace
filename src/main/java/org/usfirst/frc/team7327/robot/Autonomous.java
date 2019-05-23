package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot;

public class Autonomous {
    public static void Auto() {

        
        int k = 0;
        while (k < 80) {
            Robot.MoveForward();
            k++;
            if (k == 50) { Robot.TurnRight(); }
        }
    }
}
// Finished task above
