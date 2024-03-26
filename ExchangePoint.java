import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ExchangePoint {
    private ReentrantLock exchangePointLock;
    private ReentrantLock queueLock;
    private Condition canCollect;
    private Condition canEnter;
    private int beepersInPlace;
    private boolean isAvailable;

    public ExchangePoint(ReentrantLock lock, int beepersInPlace){
        exchangePointLock = lock;
        queueLock = new ReentrantLock();
        canEnter = queueLock.newCondition();
        canCollect = exchangePointLock.newCondition();
        this.beepersInPlace = beepersInPlace;
        isAvailable = true;
    }

    public boolean areAnyBeepers(){

        if (beepersInPlace != 0){
            return true;
        }
        queueLock.lock();

        try {
            canEnter.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            queueLock.unlock();
        }
        return beepersInPlace != 0;
      
    }


    public boolean collectFromPoint(BaseConciousRobot collectingRobot) {

        
        if (isAvailable){
        try {
            exchangePointLock.lock();
            if (beepersInPlace < 120){
                canCollect.await();
            }
            collectingRobot.move();

            while (beepersInPlace > 0 && !collectingRobot.isFull()) {
                collectingRobot.pickBeeper();
                beepersInPlace--;
            }
            if (collectingRobot.getActualBeepers() == 0){
                isAvailable = false;
                System.out.println("Exchange not available");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }else{
        return false;
    }



    }

    //Miner mined until 120
    public void dropToPoint(BaseConciousRobot droppingRobot){


            int beepersToDrop = droppingRobot.getActualBeepers();
            
            droppingRobot.dropBeepers();
            beepersInPlace += beepersToDrop;

            if (beepersInPlace >= 120 || beepersToDrop < droppingRobot.getCapacity() ){
                canCollect.signalAll();
                queueLock.lock();
                canEnter.signalAll();
                queueLock.unlock();
            }
    }

    public int getBeepersInPlace() {
       return this.beepersInPlace;
    }

    public void finish() {
    isAvailable = false;
    }



}
