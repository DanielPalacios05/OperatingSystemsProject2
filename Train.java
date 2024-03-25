import java.util.concurrent.locks.ReentrantLock;
import java.awt.Color;

import kareltherobot.Directions;

public class Train extends BaseConciousRobot implements Runnable,Directions{

    private ExchangePoint exchangePoint;
    
    public Train(int street, int avenue, int beepers,Direction direction, int capacity, ReentrantLock[][] lockMap,ExchangePoint ep){
        super(new BaseRobot(street, avenue, direction, beepers, Color.BLUE, capacity, RobotState.INITIALIZING),lockMap,avenue,street);
        this.exchangePoint = ep;     
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
        goForward();
        turnLeft();
        goForward();
        turnRight();
        move(4);

        try {
            exchangePoint.collectFromPoint(this);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        turnRight();
        goForward();



    }
}
