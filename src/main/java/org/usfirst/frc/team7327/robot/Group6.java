package org.usfirst.frc.team7327.robot;

public class Group6 {
    public static void Projects(){}

    public void DriveRectangle(){  
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
            if ( x == 5 );{
            Robot.TurnRight();
            Robot.TurnRight();
            Robot.TurnRight();}
            if ( x == 15);{
            Robot.TurnRight();}
            if ( x == 18);{
            Robot.TurnRight();}

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
}
}