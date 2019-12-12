package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {
        int x = 0; 
        while(x < 210){
            Robot.MoveForward();
            x = x+1; 
        if(x == 210){
            Robot.TurnRight();
        if(x == 100){
            Robot.TurnRight();
        if(x == 210){
                Robot.TurnRight();
        if(x == 100){
                Robot.TurnRight();
        }

        /*
        int x = 0;
        while(x < 210){
            Robot.MoveForward();
            x = x+1; 
        } 
        Robot.TurnRight();

        x = 0;
        while(x < 100){
            Robot.MoveForward();
            x = x+1; 
        } 
        x = 0;
        while(x < 210){
            Robot.MoveForward();
            x = x+1; 
        } 
        Robot.TurnRight();
        x = 0;
        while(x < 100){
            Robot.MoveForward();
            x = x+1; 
        } 
        */
       
    }
}
