import java.awt.Color;
import java.util.concurrent.locks.ReentrantLock;

import kareltherobot.Directions.Direction;

public class Extractor extends BaseConciousRobot implements Runnable {

    private int pos;


      public Extractor(int street, int avenue, int beepers,Direction direction, int capacity, ReentrantLock[][] lockMap,int pos){
        super(new BaseRobot(street, avenue, direction, beepers, Color.RED, capacity, RobotState.INITIALIZING),lockMap,avenue,street);     
        this.pos = pos;
    }

    public void run(){
        if(getState() == RobotState.INITIALIZING){
            initialize();
        }
    }

    public void initialize(){
        lockActualPosition();
        goForward();
        turnRight();
        move();
        turnLeft();
        goForward();
        turnLeft();
        move();
        turnLeft();
        
        move(5-pos);

        turnLeft();
        turnLeft();



    }
}

