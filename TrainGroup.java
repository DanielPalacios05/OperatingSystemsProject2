import java.util.ArrayList;

public class TrainGroup  implements Runnable{
    private ArrayList<Train> trains;
    private Train leader;

    public TrainGroup(){
        trains = new ArrayList<>();
    }

    public void addTrain(Train train){
        trains.add(train);
    }



    public void run(){
        initialize();
    }

    public void initialize(){
        for (Train train : trains) {
            Thread trainThread = new Thread(train);
            trainThread.start();
        }
    }

    public void goForward() {

        while (leader.frontIsClear()) {
            for (Train train : trains) {
                train.move();
            }
        }
    }




    


    
}
