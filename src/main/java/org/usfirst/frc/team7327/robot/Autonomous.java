package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {


        DriveSquare();

    }
    public static void DriveSquare(){
        MoveForward(30);
        TurnRight();
        MoveForward(10);
        TurnRight();
        MoveForward(30);
        TurnRight();
        MoveForward(10);
    }
    public static void MoveForward(int y){

        int x = 0;
        while (x < y){
            Robot.MoveForward();
            x = x + 1;
        }
        
    }
    public static void TurnRight(){
        Robot.TurnRight();
    }
}



