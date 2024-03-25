import java.awt.Color;
import java.util.concurrent.locks.ReentrantLock;

import kareltherobot.Directions.Direction;

public class Extractor extends BaseConciousRobot implements Runnable {

    private int pos;
    private ExchangePoint extractorExchangePoint;


    private int[] warehouse;


      public Extractor(int street, int avenue, int beepers,Direction direction, int capacity, ReentrantLock[][] lockMap,int pos, ExchangePoint ep,int[] wr){
        super(new BaseRobot(street, avenue, direction, beepers, Color.RED, capacity, RobotState.INITIALIZING),lockMap,avenue,street);     
        this.extractorExchangePoint = ep;
        this.pos = pos;
        this.warehouse = wr;
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
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        turnLeft();
        turnLeft();

        boolean hasCollected = false;
        while (!hasCollected) {
        if (getPosY() == 1 && getPosX() == 1){
            extractorExchangePoint.collectFromPoint(this);

            hasCollected = true;
            releaseActualLock();
            turnRight();
            move();
            turnRight();
            goForward();
            turnRight();
            move();
            turnLeft();
            move();
            turnRight();
            move();
            turnRight();
            dropInWarehouse();

            turnLeft();
            move();
            turnLeft();

            move(getPosX());
            turnRight();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            move(2+pos);
            turnRight();
            move();
            turnRight();

            releaseActualLock();

        }else{
            if(frontIsClear()){
                move();
            }
            }
        }  
    }

    public void dropInWarehouse() {

        move();
        turnLeft();
        for (int i = 0; i < warehouse.length; i++) {
            
            if(warehouse[i] < 3000){

                while (anyBeepersInBeeperBag() && warehouse[i] < 3000) {
                    putBeeper();
                    warehouse[i]--;
                }
            }else if(!anyBeepersInBeeperBag()){
                break;
            }
            else{
                move();
            }

        }
        
    }
}

