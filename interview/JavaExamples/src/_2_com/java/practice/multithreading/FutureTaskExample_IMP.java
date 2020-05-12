package _2_com.java.practice.multithreading;


/*
 * FutureTask is the base implementation class of Future interface and we can use it with Executors for asynchronous processing. Most of the time 
 * we don�t need to use FutureTask class but it comes real handy if we want to override some of the methods of Future interface and want to keep most of the base 
 * implementation. We can just extend this class and override the methods according to our requirements.
 */
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.Callable;

public class FutureTaskExample_IMP {
	
	public static class MyCallable implements Callable<String> {

		private long waitTime;
		
		public MyCallable(int timeInMillis){
			this.waitTime=timeInMillis;
		}
		@Override
		public String call() throws Exception {
			Thread.sleep(waitTime);
	        //return the thread name executing this callable task
	        return Thread.currentThread().getName();
		}

	}

	public static void main(String[] args) {
		MyCallable callable1 = new MyCallable(1000);
		MyCallable callable2 = new MyCallable(2000);

		FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
		FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(futureTask1);
		executor.execute(futureTask2);
		
		while (true) {
			try {
				if(futureTask1.isDone() && futureTask2.isDone()){
					System.out.println("Done");
					//shut down executor service
					executor.shutdown();
					return;
				}
				
				if(!futureTask1.isDone()){
				//wait indefinitely for future task to complete
				System.out.println("FutureTask1 output="+futureTask1.get());
				}
				
				System.out.println("Waiting for FutureTask2 to complete");
				String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
				if(s !=null){
					System.out.println("FutureTask2 output="+s);
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}catch(TimeoutException e){
				//do nothing
			}
		}
		
	}

}
