import java.util.concurrent.Semaphore;

class Q{
	int item;
	static Semaphore semcon=new Semaphore(0);
	static Semaphore semprod=new Semaphore(1);
	
	void get() {
		try {
			semcon.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted exception caught");
		}
		
		System.out.println("Consumer consumed an item"+ item);
		semprod.release();
	}
	
	void put(int item) {
		try {
			semprod.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted exception caught");
		}
		this.item=item;
		System.out.println("Producer produced an item"+item);
		semcon.release();
	}
}

class producer implements Runnable{
	Q q;
	producer(Q q){
		this.q=q;
		new Thread(this, "Producer").start();
	}
	@Override
	public void run() {
		for(int i=0;i<10;i++) {
			q.put(i);
		}
	}
}
class consumer implements Runnable{
	Q q;
	consumer(Q q){
		this.q=q;
		new Thread(this, "Consumer").start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++) {
			q.get();
		}
	}
}
class PC {
	
public static void main(String[] args) {
	Q q=new Q();
	new consumer(q);
	new producer(q);
	
}
}