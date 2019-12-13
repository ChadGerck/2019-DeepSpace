package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {
        MoveForward(26);
            Robot.TurnRight();
    }   MoveForward(35);
            Robot.TurnRight();
        MoveForward(31);

    public static void MoveForward(int y){
          
        int x = 0;
        while( x < y){
            Robot .MoveForward();
            x = x + 1;
        }

    }
}


       /*
    int x = 0; 
    while( x < 10 ){
        Robot.MoveForward();
        x = x + 1; 
    }




       */

    


