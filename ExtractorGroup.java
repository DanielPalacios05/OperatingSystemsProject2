import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ExtractorGroup  implements Runnable{
    private ArrayList<Extractor> extractors;
    private ExchangePoint extractorExchangePoint;
    private ArrayList<Thread> robotThreads;
    private ReentrantLock entryLock;
    public ExtractorGroup(ExchangePoint exchangePoint,ReentrantLock entryLock){
        extractors = new ArrayList<>();
        extractorExchangePoint = exchangePoint;
        robotThreads = new ArrayList<>();
        this.entryLock = entryLock;
    }

    public void addExtractor(Extractor extractor){
        extractors.add(extractor);
        robotThreads.add(null);
    }



    public void run(){


               
            try {
                do {
                entryLock.lock();
                initialize(); 
                System.out.println("Esperando ultimo extractor");
                robotThreads.get(0).join();
                System.out.println("Esperando confirmacion de beepers");
                entryLock.unlock();
            } while (extractorExchangePoint.areAnyBeepers());
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public void initialize(){
        for (int i = 0; i < robotThreads.size(); i++) {
            robotThreads.set(i, new Thread(extractors.get(i)));
            robotThreads.get(i).start();
        }
    }





    


    
}