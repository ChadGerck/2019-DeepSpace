package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {


       DriveRectangle();
    }
    
    public static void DriveRectangle(){  
        MoveForward(30);
        TurnRight(1);
        MoveForward(10);
        TurnRight(1);
        MoveForward(30);
        TurnRight(1);
        MoveForward(10);
        TurnRight(1);
    }


    public static void MoveForward(int y) {
        int x = 0;
        while( x < y){
            Robot.MoveForward();
            x = x + 1;
        }
    }
    
    public static void TurnRight(int z){
        int a = 0;
        while( a < z){
            Robot.TurnRight();
            a = a + 1;
        }
    }
}

