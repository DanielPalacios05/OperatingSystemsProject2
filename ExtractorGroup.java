import java.util.ArrayList;

public class ExtractorGroup  implements Runnable{
    private ArrayList<Extractor> extractors;
    private ExchangePoint extractorExchangePoint;
    private ArrayList<Thread> robotThreads;

    public ExtractorGroup(ExchangePoint exchangePoint){
        extractors = new ArrayList<>();
        extractorExchangePoint = exchangePoint;
        robotThreads = new ArrayList<>();
    }

    public void addExtractor(Extractor extractor){
        extractors.add(extractor);
        robotThreads.add(null);
    }



    public void run(){
               
            try {
                do {
                    
                initialize(); 
                System.out.println("Esperando ultimo extractor");
                robotThreads.get(0).join();
                System.out.println("Esperadno confimrmacion de beepers");
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