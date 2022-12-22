
import java.util.Scanner;

public class BankerAlgorithm {

    int need[][],allocat[][],max[][],avail[][],np,nr;

    void input(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the no of processes and no of resources");
        np=sc.nextInt();
        nr=sc.nextInt();

        need=new int[np][nr];
        allocat=new int[np][nr];
        max=new int[np][nr];
        avail=new int[1][nr];

        System.out.println("Enter the allocation matrix");
        for(int i=0;i<np;i++){
            for(int j=0;j<nr;j++){
                allocat[i][j]=sc.nextInt();
            }
        }

        System.out.println("Enter the max matrix");
        for(int i=0;i<np;i++){
            for(int j=0;j<nr;j++){
                max[i][j]=sc.nextInt();
            }
        }

        System.out.println("Enter the avail matrix");
        
            for(int j=0;j<nr;j++){
                avail[0][j]=sc.nextInt();
            }
        

        sc.close();

    }

    int[][] cal_need(){
        for(int i=0;i<np;i++){
            for(int j=0;j<nr;j++){
                need[i][j]=max[i][j]-allocat[i][j];
            }
        }
        return need;
    }

    boolean check(int i){
        for(int j=0;j<nr;j++){
            if(avail[0][j]<need[i][j]){ // availability of resources for process to execute 
                return false;
            } 
        }
        return true;
    }

    void isSafe(){
        input();
        cal_need();
        boolean done[]=new boolean[np];
        int j=0;

        while(j<np){ //until all process get exe
            boolean allocated=false;
            for(int i=0;i<np;i++){
                if(!done[i] && check(i)){
                    for(int k=0;k<nr;k++){
                        avail[0][k]=avail[0][k]-need[i][k]+max[i][k];
                       
                    }
                    System.out.println("Allocated process "+i);
                    allocated=done[i]=true;
                    j++;
                }
                if(!allocated) break;
            }
            if(j==np){
                System.out.println("\n safely allocated");
            }else{
                System.out.println("All processes can not be allocated safely");
            }
        }
    }
    public static void main(String[] args) {
        new BankerAlgorithm().isSafe();
    }
}
