package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {

        int x = 0;
        while( x < 80){
            MoveForward(1);
            x = x + 1; 
            if(x == 30){
            TurnRight();}
            if(x == 40){
            TurnRight();    
            }
        
        }
    }
    



    public static void MoveForward(int x){
        while( x < 84){
            Robot.MoveForward();
            x = x + 1;
        }
    }
    
    public static void TurnRight(){
        Robot.TurnRight();
    }
}

