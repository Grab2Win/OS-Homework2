import java.util.concurrent.Semaphore;

public class BoundedBuffer implements Buffer {

//****************************************         VARIABLES/OBJECTS          ****************************************************************************
    private int count;
    private int in;
    private int out;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;
    private Object[] buffer;
    private static final int BUFFER_SIZE = 5;
    static double packets_dropped;
    static double totalPackets;

//****************************************         CONSTRUCTORS          ****************************************************************************
    public BoundedBuffer() {
        count = 0;
        in = 0;
        out = 0;
        buffer = new Object[BUFFER_SIZE];
        mutex = new Semaphore(1);
        empty = new Semaphore(BUFFER_SIZE);
        full = new Semaphore(0);
    }


    public BoundedBuffer(int bufferSize) {
        count = 0;
        in = 0;
        out = 0;
        buffer = new Object[bufferSize];
        mutex = new Semaphore(1);
        empty = new Semaphore(bufferSize);
        full = new Semaphore(0);
    }

//****************************************         PRODUCER METHOD          ****************************************************************************
    public void add2buff(Object item) {
        try {
            mutex.acquire();
            totalPackets++;
            if(count == BUFFER_SIZE) {
                packets_dropped++;
                System.out.println("Packet " + Packet.PID + " has been dropped due to a full buffer.");
                mutex.release();
                return;
            }
            empty.acquire();
        } catch (Exception e) {
            System.out.println("Exception found");
        }

        // add an item to the buffer
        ++count;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;
        if (count == BUFFER_SIZE) {
            System.out.println("    PACKET " + Packet.PID + " created.");
            System.out.println("    Buffer is full.");
        } else {
            System.out.println("    PACKET " + Packet.PID + " created.");
            System.out.println("    Buffer Size = " + count + ".");
        }
        mutex.release();
        full.release();
    }

//****************************************         CONSUMER METHOD          ****************************************************************************
    public Object remove() {
        try {
            full.acquire();
            mutex.acquire();
        } catch (Exception ignored) {
        }

        // Subtract from Buffer
        --count;
        Object item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;
        mutex.release();
        empty.release();
        return item;
    }
}
