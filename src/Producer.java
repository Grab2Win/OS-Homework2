public class Producer implements Runnable {

//********************************         VARIABLES          ****************************************************************************
    private int process = 10;      //network
    private int sleep = 5;         //firewall
    //public int countP = 1;

//********************************         CONSTRUCTORS          ****************************************************************************
    public Producer(Buffer buff) {
        buffer = buff;
    }

    public Producer(Buffer buff, int pTime, int sTime) {
        buffer = buff;
        process = pTime;
        sleep = sTime;
    }

//********************************         RUN()          ****************************************************************************
    public void run() {
        Packet packet;
        while (true) {
            packet = new Packet(process);
            //Packet.PID++;// = countP;
            //countP++;
            buffer.add2buff(packet);
            Packet.PID++;
            SleepUtilities.nap(sleep);
        }
    }
    private Buffer buffer;
}
