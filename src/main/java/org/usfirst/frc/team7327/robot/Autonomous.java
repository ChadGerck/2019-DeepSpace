package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {
        DrivinginSquare();
        NewLane();
                                                             
    }
    public static void Leftyturn(){
     
       
            Robot.TurnRight();
            Robot.TurnRight();
            Robot.TurnRight();

        
    }



    public static void MoveForward(int y){
          
        int x = 0;
        while( x < y){
            Robot .MoveForward();
            x = x + 1;
        }
    }
    public static void DrivinginSquare(){
        MoveForward(26);
        Robot.TurnRight();
        MoveForward(9);
        Robot.TurnRight();
        MoveForward(26);
        Robot.TurnRight();
        MoveForward(9);
    }
 

    public static void NewLane(){
        MoveForward(26);
        Robot.TurnLeft();
        MoveForward(9);
        Robot.TurnLeft();
        MoveForward(10);
        Robot.TurnLeft();
        MoveForward(16);
        Robot.TurnLeft();
        MoveForward(10);
    }

}


       /*
    int x = 0; 
    while( x < 10 ){
        Robot.MoveForward();
        x = x + 1; 
    }




       */

    


