import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Priority {
    public static List<Process> sortProcessesBasedOnArrivalTime(List<Process> P, int n , int counter) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (P.get(j).getArrivalTime() < P.get(i).getArrivalTime()) {
                    Process temp = P.get(i);
                    P.set(i, P.get(j));
                    P.set(j, temp);
                }
            }
        }
        List<Process>x=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(P.get(i).getArrivalTime()<=counter&&P.get(i).getBurstTime()>0){
                x.add(P.get(i));
            }
        }
        return x;
    }
    public static void sortProcessesBasedPriority(List<Process> P, int n ) {//,int counter //check arri
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((P.get(j).getPriority()< P.get(i).getPriority())) {
                    Process temp = P.get(i);
                    P.set(i, P.get(j));
                    P.set(j, temp);
                }
            }
        }
    }
    public static void increasePriorityOfProcess(List<Process> P ) {//,int counter //check arri
        for (Process process : P) {
            process.setPriority(process.getPriority() - 1);
        }
    }
    //    public Static void SFj
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List <Process> processes = new ArrayList<Process>();
        System.out.println("   --welcome-- to Priority Scheduling code--   ");
        System.out.println("enter the number of processes and context switching");
        int numberOfProcesses = input.nextInt();
        int contextSwitchingTime=input.nextInt();
        Process contextSwitching=new Process();
        contextSwitching.setName("contextSwitching");
        contextSwitching.setBurstTime(contextSwitchingTime);
        contextSwitching.setArrivalTime(0);
        System.out.println("enter for each process its name , arrival time , burst time and priority");
        for (int i = 0; i < numberOfProcesses; i++) {
            Process s = new Process();
            s.setName(input.next());
            s.setArrivalTime(input.nextInt());
            s.setBurstTime(input.nextInt());
            s.setPriority(input.nextInt());
            processes.add(s);
        }
        int totalNumberOfBurstTime=0;
        for (Process p : processes){
            totalNumberOfBurstTime+=p.getBurstTime();
        }
        Stack<Process> P = new Stack<Process>();
        Stack<Integer> C = new Stack<Integer>();
        List<Process>readyProcesses=new ArrayList<>();
        readyProcesses=sortProcessesBasedOnArrivalTime(processes,numberOfProcesses,0);
        sortProcessesBasedPriority(readyProcesses,readyProcesses.size());
        Process noProcessInTheMemory=new Process();
        noProcessInTheMemory.setName("no process in the memory");
        noProcessInTheMemory.setWaitingTime(0);
        P.add(null);
        C.add(null);
        int counter=0;
        boolean status;
        while (totalNumberOfBurstTime!=0){
            readyProcesses=sortProcessesBasedOnArrivalTime(processes,numberOfProcesses,counter);
            sortProcessesBasedPriority(readyProcesses,readyProcesses.size());
            status=false;
            for (int i=0;i<readyProcesses.size();) {
//                System.out.println(readyProcesses.get(i).getName()+"   -");
//                for (Process p : readyProcesses){
//                    System.out.println(p.getName()+" "+p.getPriority());
//                }
//                System.out.println("--------------------------------------------------------------------");
                status = true;
                P.add(readyProcesses.get(i));
                readyProcesses.get(i).setBurstTime(0);
                counter+=readyProcesses.get(i).getSaveBurstTime();
                C.add(counter);
                if(readyProcesses.get(i).getBurstTime()==0){
                    readyProcesses.get(i).setTurnAroundTime(counter-readyProcesses.get(i).getArrivalTime());
                    readyProcesses.get(i).setWaitingTime(readyProcesses.get(i).getTurnAroundTime()-readyProcesses.get(i).getSaveBurstTime());
                }
//                starvation
                increasePriorityOfProcess(readyProcesses);
                readyProcesses=sortProcessesBasedOnArrivalTime(processes,numberOfProcesses,counter);
                sortProcessesBasedPriority(readyProcesses,readyProcesses.size());
                totalNumberOfBurstTime = 0;
                for (Process p : processes) {
                    totalNumberOfBurstTime += p.getBurstTime();
                }
                if(totalNumberOfBurstTime!=0) {
                    P.add(contextSwitching);
                    counter += contextSwitchingTime;
                    C.add(counter);
                }
            }
            if (!status) {
                if (P.lastElement() != noProcessInTheMemory) {
                    P.add(noProcessInTheMemory);
                    counter++;
                    C.add(counter);
                } else {
                    counter++;
                    C.set(C.size() - 1,C.get(C.size() - 1) + 1);
                }
                readyProcesses=sortProcessesBasedOnArrivalTime(processes,numberOfProcesses,counter);
                sortProcessesBasedPriority(readyProcesses,readyProcesses.size());
            }
            totalNumberOfBurstTime = 0;
            for (Process p : processes) {
                totalNumberOfBurstTime += p.getBurstTime();
            }
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Processes execution order |");
        for (int i = 1; i < P.size(); i++) {
            System.out.println(P.get(i).getName()+" "+C.get(i));
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("table of processes |Name ,WaitingTime ,TurnaroundTime ,BurstTime");
        double avgWaitingTime=0.0;
        double avgTurnAroundTime=0.0;
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println(processes.get(i).getName()+" "+processes.get(i).getWaitingTime()+" "+processes.get(i).getTurnAroundTime()+" "
                    +processes.get(i).getSaveBurstTime());
            avgWaitingTime+=processes.get(i).getWaitingTime();
            avgTurnAroundTime+=processes.get(i).getTurnAroundTime();
        }
        avgWaitingTime/=numberOfProcesses;
        avgTurnAroundTime/=numberOfProcesses;
        System.out.print("the avrage waiting time for processes = ");
        System.out.println(avgWaitingTime);
        System.out.print("the avrage turnaround time for processes = ");
        System.out.println(avgTurnAroundTime);
    }
}
//7 0
//p1 0 3 2
//p2 2 5 6
//p3 1 4 3
//p4 4 2 5
//p5 6 9 7
//p6 5 4 4
//p7 7 10 10

