package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {

        int x = 0; 
        while(x < 80){
            Robot.MoveForward();
            if(x == 30){
                Robot.TurnRight();
            }
            if(x == 40){ 
                Robot.TurnRight();
            }
            if(x == 70){
                Robot.TurnRight();
            }
            if(x == 79){
                Robot.TurnRight();
            }
            x = x + 1; 
        }
        
    }
}


