package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {

    public static void Auto() {
        int x; 
        x = 0;
        while(x < 13){
            Robot.MoveForward();
            x = x + 1; 
        }
        Robot.TurnRight();
        x = 0;
        while(x < 17){
            Robot.MoveForward();
            x = x + 1;
        }
        Robot.TurnRight();
        x = 0; 
        while(x < 12){
            Robot.MoveForward();
            x = x + 1;
        }
        Robot.TurnRight();
        x = 0;
        while(x < 17){
            Robot.MoveForward();
            x = x + 1;
        }

    }
}