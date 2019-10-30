package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {
        
        int x = 0;
        while( x < 20){
            Robot.MoveForward();
            x++; 
        } 
        /*

        if(x == 10){
            Robot.TurnRight(); 
        }

        

        int x = 0;
        while( x < 15){
            Robot.MoveForward();
            x = x + 1;
        }Robot.TurnRight();
        x = 0;
        while( x < 5){
            Robot.MoveForward();
            x = x + 1;
        }
        */


        int x = 0;
        while( x < 20){
            if(x == 15){
                Robot.TurnRight();
            }
            x = x + 1;
        }if(x == 5){
            Robot.MoveForward();
        }
        






        if(x == 15 ){
            Robot.TurnRight();
            
        }
            







        /*
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.TurnRight();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        */

    }
}

