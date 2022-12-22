

class Philosopher implements Runnable{

    Object leftStick;
    Object rightStick;

    Philosopher(Object left, Object right){
        leftStick=left;
        rightStick=right;
    }

     void doAction(String action) throws InterruptedException
     {
        System.out.println(
            Thread.currentThread().getName()+" "+action
        );
        Thread.sleep((int)(Math.random()*100));
     }

    @Override
    public void run() {
        try {
            while(true){
                doAction(System.nanoTime()+" : Thinking");

                synchronized (leftStick){
                    doAction(System.nanoTime()+" picked up left stick");

                    synchronized(rightStick){
                        doAction(System.nanoTime()+" Picked up right stick and eating");

                        doAction(System.nanoTime()+" PutDown right stick");
                    }

                    doAction(System.nanoTime()+" PutDown left stick and thinking back");
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
public class DP {

    public static void main(String[] args) throws Exception {

        final Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            if (i == philosophers.length - 1) {
                
                // The last philosopher picks up the right fork first
                philosophers[i] = new Philosopher(rightFork, leftFork); 
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }
            
            Thread t 
              = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
}

