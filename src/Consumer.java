public class Consumer implements Runnable {

    public static int processCounter = 0;

    public Consumer(Buffer b) {
        buffer = b;
    }

    public void run() {
        Packet packet;

        while (true) {
            packet = (Packet) buffer.remove();
            packet.beginST();
            SleepUtilities.nap((int)packet.serviceTime);
            processCounter++;
            System.out.println(
                    "\n***********************************PACKET PROCESSED***********************************\n" +
                            "The TURNAROUND-TIME is: " + packet + " ms.");
        }
    }
    private Buffer buffer;
}


