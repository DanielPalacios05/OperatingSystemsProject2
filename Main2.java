import java.util.concurrent.locks.ReentrantLock;

import kareltherobot.Directions;
import kareltherobot.World;

public class Main2 implements Directions{

    public static void main(String[] args) {

        World.setTrace(false);

        World.readWorld("Mina.kwld");
        World.setVisible(true);
        World.showSpeedControl(true);



        ReentrantLock[][] lockMap = new ReentrantLock[30][30];
        
        
        for (int i = 0; i < lockMap.length; i++) {
            for (int j = 0; j < lockMap.length; j++) {
                lockMap[i][j] = new ReentrantLock();
            }
        }
        TrainGroup trainGroup = new TrainGroup();
        ExtractorGroup extractorGroup = new ExtractorGroup();
        int numTrains = 9;
        int numExtractors = 5;

        ExchangePoint ep = new ExchangePoint(lockMap[10][12], 0);
        ExchangePoint extractorExchangePoint = new ExchangePoint(lockMap[0][1], 0);




        for (int i = 0; i < numTrains; i++) {
            Train newTrain = new Train(11+i, 2, 0, South, 120, lockMap,ep,extractorExchangePoint);
            
            trainGroup.addTrain(newTrain);


        }

        for (int i = 0; i < numExtractors; i++) {
            Extractor newExtractor = new Extractor(12+numTrains+i, 2, 0, South, 120, lockMap,i);
            
            extractorGroup.addExtractor(newExtractor);
            
        }

       
        
        Thread tr2 = new Thread(trainGroup);
        tr2.start();

        Thread tr3 = new Thread(extractorGroup);
        tr3.start();




    }
    
}
