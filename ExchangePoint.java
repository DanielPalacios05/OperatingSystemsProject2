import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ExchangePoint {
    private ReentrantLock exchangePointLock;
    private ReentrantLock queueLock;
    private Condition canCollect;
    private Condition canEnter;
    private int beepersInPlace;

    public ExchangePoint(ReentrantLock lock, int beepersInPlace){
        exchangePointLock = lock;
        queueLock = new ReentrantLock();
        canEnter = queueLock.newCondition();
        canCollect = exchangePointLock.newCondition();
        this.beepersInPlace = beepersInPlace;
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


    public void  collectFromPoint(BaseConciousRobot collectingRobot) {

        
        exchangePointLock.lock();

        try {
            if (beepersInPlace < 120){
                canCollect.await();
            }
            collectingRobot.move();

            while (beepersInPlace > 0 && !collectingRobot.isFull()) {
                collectingRobot.pickBeeper();
                beepersInPlace--;
            }
            System.out.println(beepersInPlace);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'finish'");
    }


}
