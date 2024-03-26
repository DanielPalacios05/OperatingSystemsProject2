import java.util.concurrent.locks.ReentrantLock;
import java.awt.Color;

import kareltherobot.Directions;

public class Train extends BaseConciousRobot implements Runnable,Directions{

    private ExchangePoint minerExchangePoint;
    private ExchangePoint extractorExchangePoint;
    private ReentrantLock trainQueueLock; 
    private int pos;
    public Train(int street, int avenue, int beepers,Direction direction, int capacity, ReentrantLock[][] lockMap,ExchangePoint mep,ExchangePoint eep, int pos){
        super(new BaseRobot(street, avenue, direction, beepers, Color.BLUE, capacity, RobotState.INITIALIZING),lockMap,avenue,street);
        this.minerExchangePoint = mep;     
        this.extractorExchangePoint = eep;
        trainQueueLock = this.getLock(26, 26);
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
        goForward();
        turnLeft();
        goForward();
        turnRight();
        move(4);



        while(true){

        if(this.getPosY() == 10 && this.getPosX() == 11 ){
            boolean beepersExist = minerExchangePoint.collectFromPoint(this);

            if(!beepersExist){
                move();
            }
            turnRight();
            releaseActualLock();
            goForward();
            turnRight();
            goForward();
            turnLeft();
            move(4);
            trainQueueLock.lock();
            move();
            turnRight();
            move();
            if (beepersExist){

                extractorExchangePoint.dropToPoint(this);
                turnLeft();
                turnLeft();
                releaseActualLock();
                move(2);
                trainQueueLock.unlock();
                goForward();
                turnLeft();
                
                move(6);
                goForward();
            }else{
                trainQueueLock.unlock();
                ReentrantLock entryLock = getLock(22, 22);

                entryLock.lock();
                goForward();
                turnRight();
                goForward();
                turnRight();
                move();
                turnLeft();
                move(2);
                turnRight();
                move();
                turnLeft();
                move(10-pos);
                entryLock.unlock();
                break;
            }
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
