package org.usfirst.frc.team7327.robot;


import org.usfirst.frc.team7327.robot.Robot; 
public class Autonomous {
    public static void Auto() {
        int x = 0; 
        while( x < 11 ){
            Robot.MoveForward();
            x = x + 1; 
        }
        Robot.TurnRight();
            Robot.MoveForward();
        x = 0;
        while(x < 19){ 
            Robot.TurnRight();
            Robot.MoveForward();
        x = 0;
        while(x < 11 ){
            }
        
        }
    }
}

