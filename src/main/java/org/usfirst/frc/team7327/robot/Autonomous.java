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
        Leftyturn();
        MoveForward(9);
        Leftyturn();
        MoveForward(10);
        Leftyturn();
        MoveForward(16);
        Leftyturn();
        MoveForward(10);
    }





public class Drivinginsquare2 {
    public static void Drivinginsquare(){


    int x = 0;
    while (x < 71){
                Robot.MoveForward();
            if (x = 26){
                Robot.TurnRight();
            }
            if (x = 9){
                Robot.TurnRight();

            }
            if (x = 10){
                Robot.TurnRight();

            }
            if (x = 16){
                Robot.TurnRight();

            }
            if (x = 10){
                Robot.TurnRight();

            }
            
            x = x + 1; 
      }



       /*
    int x = 0; 
    while( x < 10 ){
        Robot.MoveForward();
        x = x + 1; 
    }




       */

    


