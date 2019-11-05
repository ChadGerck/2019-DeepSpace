package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {

        int x = 0;
        while( x < 80){
            MoveForward();
            x = x + 1; 
            if(x == 30){
            TurnRight();}
            if(x == 40){
            TurnRight();    
            }
            
        }
    }

    public static void MoveForward(){
        Robot.MoveForward(); 
    }
    public static void TurnRight(){
        Robot.TurnRight();
    }
}



