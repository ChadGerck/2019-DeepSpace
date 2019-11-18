package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {

        int x = 0;
        while(x < 10){
            Robot.MoveForward();
            x++; 
        }

        MoveFoward(3);


    }
    public static void MoveFoward(int y){


    }
}

