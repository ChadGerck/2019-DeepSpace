package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {
        
        int x = 0; 
        while( x < 26){
            Robot.MoveForward(); 
            x = x + 1; 
        }
        Robot.TurnRight(); 

        x = 0;
        while (x < 9){
            Robot.MoveForward();
            x= x + 1;
        }
        Robot.TurnRight();

        x = 0;
        while (x<26){
            Robot.MoveForward();
            x = x + 1;
        } 
        Robot.TurnRight();

        x = 0;
        while( x < 8){
            Robot.MoveForward();
            x = x + 1;

        }
       Robot.TurnRight();



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
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.TurnRight();
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
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.TurnRight();
        */
    }
}


       

    


