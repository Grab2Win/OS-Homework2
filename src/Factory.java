import java.util.concurrent.TimeUnit;

public class Factory {

    public static void main(String args[]) {

        //INSTANCES
        Buffer gen = new BoundedBuffer();
        Packet packet = new Packet(5); //average ST
        double Rtime;

//****************************************         THREADS          ****************************************************************************
        // ptime = serviceTime      sTime = how long it takes to generate packets.
        Thread pGen;            // Packet Generator    (Client)
        pGen = new Thread(new Producer(gen,12, 10));
        Thread pGen1;            // Packet Generator    (Client)
        pGen1 = new Thread(new Producer(gen,12, 10));
        Thread pGen2;            // Packet Generator    (Client)
        pGen2 = new Thread(new Producer(gen,12, 10));
        Thread pGen3;            // Packet Generator    (Client)
        pGen3 = new Thread(new Producer(gen,12, 10));

        Thread pProcessor;      // Packet Processor    (Firewall)
        pProcessor = new Thread(new Consumer(gen));
        //Thread pProcessor1;      //second processor
        //pProcessor1 = new Thread(new Consumer(gen));

//**************************************         PROCESS START          ****************************************************************************
        pGen.start();
        pGen1.start();
        pGen2.start();
        pGen3.start();

        pProcessor.start();
        //pProcessor1.start(); //second processor
        Rtime = System.currentTimeMillis();
        System.out.println("Packets generating, please standby....\n");

        try {
            TimeUnit.MINUTES.sleep(2);   //RUNTIME
        } catch (InterruptedException cod) {
            cod.printStackTrace();
            System.out.println("Mission Failed, we'll get 'em next time!");
        }

//**************************************         PROCESS END          ****************************************************************************
        Rtime = System.currentTimeMillis() - Rtime;
        pGen.stop();
        pGen1.stop();
        pGen2.stop();
        pGen3.stop();

        pProcessor.stop();
        //pProcessor1.stop();

        packet.wtAvg();
        packet.ttAvg();
        packet.stAvg();
        //Thread.currentThread ().getThreadGroup ().interrupt();

//********************************          CALCULATION OUTPUTS          ****************************************************************************

        System.out.println("\nRuntime Exceeded.");
        System.out.println("Generating Statistics........\n");
        System.out.println("Packets created: " + Packet.PID);
        //System.out.println("Sam's way: " + BoundedBuffer.totalPackets);
        System.out.println("Packets dropped: " + (int)BoundedBuffer.packets_dropped);
        System.out.println("AVERAGE WAIT TIME: " + Packet.waitTimeAverage + " ms.");
        System.out.println("MAXIMUM WAIT TIME: " + Packet.waitTimeMax + " ms.");
        System.out.println("AVERAGE TURNAROUND TIME: " + Packet.turnaroundTimeAverage + " ms.");
        System.out.println("MAXIMUM TURNAROUND TIME: " + Packet.turnaroundTimeMax + " ms.");
        System.out.println("AVERAGE SERVICE TIME: " + Packet.serviceTimeAverage + " ms.");
        System.out.println("MAXIMUM SERVICE TIME: " + Packet.serviceTimeMax + " ms.");
        System.out.println("PERCENTAGE PACKAGES DROPPED: " + ((BoundedBuffer.packets_dropped / Packet.PID) * 100) + "%");
        System.out.println("PERCENTAGE PROCESSOR USAGE: " + (Packet.serviceTimeTotal / Rtime) * 100 + "%");
        System.out.println("Turnaround Time list: " + Packet.TTLIST);
        System.out.println("Service Time list: " + Packet.STLIST);
        System.out.println("Wait Time list: " + Packet.WTLIST);
    }
}