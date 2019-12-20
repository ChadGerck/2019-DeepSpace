package org.usfirst.frc.team7327.robot;


import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {


        
        int x;
        x = 0; 
        while( x < 62){
            Robot.MoveForward();
            x = x + 1;
            if(x == 20){
                Robot.TurnRight();
            }
             if(x == 31){
                 Robot.TurnRight();
             }   
            if(x == 51){
                Robot.TurnRight();
            }
            if(x == 62){
                Robot.TurnRight();
            }
        }
        

        /*
        int x;
        x = 0; 
        while( x < 20){
            Robot.MoveForward();
            x = x + 1; 
         }
        Robot.TurnRight();
        while( x < 11){
            Robot.MoveForward();
           x = x + 1;
         }
        Robot.TurnRight();
        while( x < 20){
            Robot.MoveForward();
            x = x + 1;
         } 
        Robot.TurnRight();
        while( x < 11){
            Robot.MoveForward();
            x = x + 1;
         }
        Robot.TurnRight();
         */

        
    }
}

