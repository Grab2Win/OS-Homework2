public class SleepUtilities
{
	public static void nap() {
		nap(NAP_TIME);
	}

	public static void nap(int duration) {
		int sleeptime = (int) (duration); //* Math.random() );
		try { Thread.sleep(sleeptime); }
		catch (InterruptedException e) {
			System.out.println("uh oh, bad things are happening.");
		}
	}

	private static final int NAP_TIME = 5;
}
