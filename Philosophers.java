import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Philosophers {
    static int Philosopher=5;

    static Philosopher philosophers[]=new Philosopher[Philosopher];
    static Chopstick chopsticks[]=new Chopstick[Philosopher];
    
    static class Chopstick{
        Semaphore mutex=new Semaphore(1);
        void grab(){
            try {
                mutex.acquire();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        void release(){
            mutex.release();
        }
        boolean isFree(){
            return mutex.availablePermits()>0;
        }
    }

    static class Philosopher extends Thread{
        int number;
        Chopstick leftChopstick;
        Chopstick righChopstick;

        Philosopher(int number,Chopstick lefChopstick,Chopstick righChopstick){
            this.number=number;
            this.leftChopstick=lefChopstick;
            this.righChopstick=righChopstick;
        }

        public void run(){
            while(true){
                leftChopstick.grab();
                System.out.println("Philosopher "+(number+1)+" grabs the left chopstick");
                righChopstick.grab();
                System.out.println("Philosopher "+(number+1)+" grabs the right chopstick");
                eat();
                leftChopstick.release();
                System.out.println("Philosopher "+(number+1)+" release the left chopstick");
                righChopstick.release();
                System.out.println("Philosopher "+(number+1)+" release the right chopstick");
            }
        }

        void eat(){
            try {
                int sleepTime=ThreadLocalRandom.current().nextInt(0,1000);
                System.out.println("Philosopher "+(number+1)+" eats for"+sleepTime+" ms");
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
    public static void main(String[] args) {
        for(int i=0;i<Philosopher;i++){
            chopsticks[i]=new Chopstick();
        }

        for(int i=0;i<Philosopher;i++){
        philosophers[i]=new Philosopher(i, chopsticks[i],chopsticks[(i+1)%Philosopher]);
        philosophers[i].start();

        }

        while(true){
            try {
                Thread.sleep(2000);
                boolean deadLock=true;
                for(Chopstick cs: chopsticks){
                    if(cs.isFree()){
                        deadLock=false;
                    }
                }

                if(deadLock){
                    Thread.sleep(2000);
                    System.out.println("Everyone Eats");
                    break;
                }
            } catch (Exception e) {
               e.printStackTrace(System.out);
            }
        }
        System.out.println("Exit the program ");
        System.exit(0);
    }
}
