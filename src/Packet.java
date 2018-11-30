import java.util.ArrayList;

public class Packet {

//********************************         VARIABLES          ****************************************************************************
    private double startTime = 0;
    public double serviceTime;
    private double serviceTimeCalc = 0;
    static double serviceTimeMax = 0;
    static double serviceTimeAverage = 0;
    static double serviceTimeTotal = 0;
    private double turnaroundTime = 0;
    static double turnaroundTimeMax = 0;
    static double turnaroundTimeAverage = 0;
    private double waitTime = 0;
    static double waitTimeMax = 0;
    static double waitTimeAverage = 0;
    protected static int PID = 1; //Packet ID


//********************************         LISTS          ****************************************************************************
    static ArrayList<Double> STLIST;
    static ArrayList<Double> TTLIST;
    static ArrayList<Double> WTLIST;

    static {
        STLIST = new ArrayList<>();
        TTLIST = new ArrayList<>();
        WTLIST = new ArrayList<>();
    }

//********************************         CONSTRUCTOR          ****************************************************************************
    public Packet (int time) {
        serviceTime = time;
        startTime();
    }

//********************************         CALCULATORY METHODS          ********************************************************************

    protected void startTime() {
        startTime =
                System.currentTimeMillis();
    }
    protected void endTime() {
        turnaroundTime =
                (System.currentTimeMillis() - startTime);
    }
    protected void waitTime() {
        waitTime =
                (turnaroundTime - serviceTimeCalc);
    }
    void beginST() {
        serviceTimeCalc =
                System.currentTimeMillis();
    }
    private void markST() {
        serviceTimeCalc =
                (System.currentTimeMillis() - serviceTimeCalc);
    }

    void wtAvg() {
        for (double num : WTLIST) {
            waitTimeAverage += num;
        }
        waitTimeAverage = (waitTimeAverage / WTLIST.size());
    }
    void ttAvg() {
        for(double num: TTLIST) {
            turnaroundTimeAverage += num;
        }
        turnaroundTimeAverage = (turnaroundTimeAverage / TTLIST.size());
    }
    void stAvg() {
        for(double num: STLIST) {
            serviceTimeAverage += num;
        }
        serviceTimeAverage = (serviceTimeAverage / STLIST.size());
    }

    //Replaces Max TT with new Max
    private void ttMax() {
        if (turnaroundTime > turnaroundTimeMax) {
            turnaroundTimeMax = turnaroundTime;
        }
    }
    //Replaces Max WT with new Max
    private void wtMax() {
        if (waitTime > waitTimeMax) {
            waitTimeMax = waitTime;
        }
    }
    //Replace Max ST with new Max
    private void stMax() {
        if (serviceTimeCalc > serviceTimeMax) {
            serviceTimeMax = serviceTimeCalc;
        }
    }

    @Override
    public String toString() {
        markST();
        if(turnaroundTime == 0) {
            endTime();
            waitTime();
        }
        add2list();
        ttMax();
        wtMax();
        stMax();
        return turnaroundTime + " ms.," + " and the WAIT-TIME is " + waitTime;
    }

    private void add2list() {
        WTLIST.add(waitTime);
        TTLIST.add(turnaroundTime);
        STLIST.add(serviceTimeCalc);
        serviceTimeTotal += serviceTimeCalc;
    }
}
