package cdp.troubleshooting.memoryleak;

public class ReferenceHolder {
	
	//static byte[] chunk = new byte[1000000];
	
	static final ThreadLocal<ReferenceHolder> threadLocal = new ThreadLocal<>();

	public ReferenceHolder() {
		threadLocal.set(this);
	}
}
