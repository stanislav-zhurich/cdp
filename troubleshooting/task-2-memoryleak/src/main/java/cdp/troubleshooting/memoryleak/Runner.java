package cdp.troubleshooting.memoryleak;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Runner {

	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
		
		Executor executor = Executors.newFixedThreadPool(10);
		for(int i = 0; i< 10000000; i ++){
			executor.execute(() -> {
				CustomClassLoader classLoader = new CustomClassLoader(Thread.currentThread().getContextClassLoader());
				try {
					Class<?> clazz = classLoader.loadClass(ReferenceHolder.class.getName());
					clazz.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		
		Thread.sleep(100000);
	}

}
