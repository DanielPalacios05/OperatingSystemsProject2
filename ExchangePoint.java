import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ExchangePoint {
    private ReentrantLock exchangePointLock;
    private Condition canCollect;
    private int beepersInPlace;

    public ExchangePoint(ReentrantLock lock, int beepersInPlace){
        exchangePointLock = lock;
        canCollect = exchangePointLock.newCondition();
        this.beepersInPlace = beepersInPlace;
    }

    public void collectFromPoint(BaseConciousRobot collectingRobot) throws InterruptedException{

        exchangePointLock.lock();

        canCollect.await();

        collectingRobot.move();
    
        while (beepersInPlace > 0 && !collectingRobot.isFull()) {
            collectingRobot.pickBeeper();
            beepersInPlace--;
        }

        System.out.println(beepersInPlace);



        exchangePointLock.unlock();
    }

    //Miner mined until 120
    public void dropToPoint(BaseConciousRobot droppingRobot){


            int beepersToDrop = droppingRobot.getActualBeepers();
            
            droppingRobot.dropBeepers();
            beepersInPlace += beepersToDrop;

            if (beepersInPlace >= 120){
                canCollect.signalAll();
            }


        


    }

    public int getBeepersInPlace() {
       return this.beepersInPlace;
    }


}
