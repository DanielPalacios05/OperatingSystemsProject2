import java.util.ArrayList;

public class ExtractorGroup  implements Runnable{
    private ArrayList<Extractor> extractors;
    private Extractor leader;

    public ExtractorGroup(){
        extractors = new ArrayList<>();
    }

    public void addExtractor(Extractor extractor){
        extractors.add(extractor);
    }



    public void run(){
        initialize();
    }

    public void initialize(){
        for (int i = 0; i < extractors.size(); i++) {
            Thread ExtractorThread = new Thread(extractors.get(i));
            ExtractorThread.start();
        }

        
    }

    public void goForward() {

        while (leader.frontIsClear()) {
            for (Extractor extractor : extractors) {
                extractor.move();
            }
        }
    }




    


    
}