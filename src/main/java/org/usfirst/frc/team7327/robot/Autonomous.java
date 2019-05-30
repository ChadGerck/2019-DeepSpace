package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.Robot; 

public class Autonomous {
    public static void Auto() {
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
        //50 move forwards
        Robot.TurnRight();
        //1 turn right
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
        //25 move forwards
        
        //backwards
        Robot.MoveForward();
        Robot.TurnRight();
        Robot.TurnRight();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();
        Robot.MoveForward();

        //chad ex
        int k = 0; 
        while(k < 10){
            Robot.MoveForward();
            k++; 
        }

        if(k == 10){
            Robot.MoveForward();
        }

        //test with whiles
        
        int b = 0;
        while(b < 100){
            Robot.MoveForward();
            b++;
                  
            if(b == 25){
            Robot.TurnRight();
            }

            if(b == 50){
            Robot.TurnRight();
            }

            if(b == 75){
            Robot.TurnRight();
            }

            if(b == 100){
            Robot.TurnRight();
            }


            MoveForward(50);
            TurnRight(1);
            MoveForward(25);
        }

    }

    public static void MoveForward(double amount){
        //of the amount 
        int s = 0; 
        while(s < amount){
            Robot.MoveForward();
            s++;
        }
    }
        
    public static void TurnRight(double amount){
        int s = 0;
        if(s == amount){
            Robot.TurnRight();
        }
    }



    }






    }
}