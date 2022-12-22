

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
public class DinningPhilosopher{

   public static void main(String[] args) {
    Philosopher philosophers[]=new Philosopher[5];
    Object sticks[]=new Object[philosophers.length];

    for(int i=0;i<sticks.length;i++){
        sticks[i]=new Object();
    }

    for(int i=0;i<philosophers.length;i++){
        Object leftStick=sticks[i];
        Object rightStick=sticks[(i+1)%sticks.length];

        if(i==philosophers.length-1){
            philosophers[i]=new Philosopher(rightStick, leftStick);

        }else{
            philosophers[i]=new Philosopher(leftStick, rightStick);
        }

        Thread t=new Thread(philosophers[i], "Philosopher "+(i+1));
        t.start();
   }

    }
}