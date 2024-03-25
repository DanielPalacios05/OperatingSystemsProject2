import java.util.concurrent.locks.ReentrantLock;
import java.awt.Color;

import kareltherobot.Directions;

public class Train extends BaseConciousRobot implements Runnable,Directions{

    private ExchangePoint minerExchangePoint;
    private ExchangePoint extractorExchangePoint;
    public Train(int street, int avenue, int beepers,Direction direction, int capacity, ReentrantLock[][] lockMap,ExchangePoint mep,ExchangePoint eep){
        super(new BaseRobot(street, avenue, direction, beepers, Color.BLUE, capacity, RobotState.INITIALIZING),lockMap,avenue,street);
        this.minerExchangePoint = mep;     
        this.extractorExchangePoint = eep;
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


        while(true){

        if(this.getPosY() == 10 && this.getPosX() == 11 ){
            minerExchangePoint.collectFromPoint(this);
            turnRight();
            releaseActualLock();
            goForward();
            turnRight();
            goForward();
            turnLeft();
            goForward();
            turnRight();
            move();
            extractorExchangePoint.dropToPoint(this);
            turnLeft();
            turnLeft();
            releaseActualLock();
            goForward();
            turnLeft();
            
            move(6);

            goForward();
        

        } 
        else if(this.getPosY() == 10 && this.getPosX() == 7 && this.facingNorth()){
            turnRight();
            move();
        }else{
            move();
        }


    }


    }

}
