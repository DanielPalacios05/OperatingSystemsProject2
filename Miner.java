import kareltherobot.*;

import java.awt.Color;
import java.util.concurrent.locks.ReentrantLock;

public class Miner extends BaseConciousRobot implements Directions,Runnable {

    private ExchangePoint minerExchangePoint;

    public Miner(int street, int avenue, int beepers,Direction direction, int capacity, ReentrantLock[][] lockMap,ExchangePoint ep){
        super(new BaseRobot(street, avenue, direction, beepers, Color.BLACK, capacity, RobotState.INITIALIZING),lockMap,avenue,street);     
        minerExchangePoint = ep;
    }


    public void run(){
        lockActualPosition();
        while (true) {
        mine();
        setState(RobotState.DROPING);
        minerExchangePoint.dropToPoint(this);
        goRest();
        }
    }


    private void mine() {

        int minerDepth = 1;
        int beepersMined = 0;
        boolean hasMined = false;

        ReentrantLock mineLock = getLock(19,   19);

        mineLock.lock();

        

        if (this.getState() == RobotState.RESTING){
            move();
            turnRight();
        } 
        this.setState(RobotState.MINING);

        while (!hasMined && !facingWest()){
            if(nextToABeeper()){
                while (!isFull()){
                    pickBeeper();
                    beepersMined++;
                }
                turnLeft();
                turnLeft();
            hasMined = true;
            }else if (frontIsClear()){
                move();
                minerDepth++;
            }   
            else{
                turnLeft();
                turnLeft();
            }
        }

        // Exit Mine
        move(minerDepth);

        

        mineLock.unlock();
    
        
    }




    private void goRest() {
        turnLeft();
        move();
        turnLeft();
        move();
        turnLeft();
        setState(RobotState.RESTING);
    }
    
}
