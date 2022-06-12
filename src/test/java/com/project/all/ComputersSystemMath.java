package com.project.all;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName:ComputersSystemMath
 * @Author:lxx
 * @Date 2022/5/11 17:21
 **/
class ProcessComputer{
    public int arrivalTime;//到达时间
    public int serviceTime;//服务时间
    public int finishTime;//结束时间
    public int startTime;//开始时间
    public int WholeTime;//周转时间
    public double weightWholeTime;//带权周转时间
    public String pid;

    ProcessComputer(int x,int y,String id){
        arrivalTime=x;
        serviceTime=y;
        pid=id;
    }

    public ProcessComputer() {

    }
}
public class ComputersSystemMath {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("请输入进程个数：");
        int n = in.nextInt();

        ProcessComputer[] p = new ProcessComputer[n];

        System.out.println("请输入每个进程的到达时间和服务时间和进程ID:");

        //初始化进程数据
        for (int i = 0; i < n; i++) {
            int arrTime = in.nextInt();
            int serTime = in.nextInt();
            String pid = in.nextLine();
            p[i] = new ProcessComputer(arrTime, serTime, pid);
        }

        while (true) {
            System.out.println("请选择进程调度算法，1：FCFS 2:SJF 其他键:quit");
            int select = in.nextInt();
            if (select == 1) {
                System.out.println("----------------您选择了FCFS-------------------");
                FCFS(p);
                Out(p);

            } else if (select == 2) {
                System.out.println("----------------您选择了SJF-------------------");
                ProcessComputer[] processes = SJF(p);
                Out(processes);
            } else {
                break;
            }
        }

    }

    private static ProcessComputer[] SJF(ProcessComputer[] p) {
        //当前时间
        int now = 0;
        //待处理list
        List<ProcessComputer> list = new LinkedList<>();
        //结果list
        List<ProcessComputer> res = new LinkedList<>();
        //按时间对进程进行排序
        sortByArrive(p);

        //处理第一个进程
        p[0].finishTime = p[0].arrivalTime + p[0].serviceTime;
        p[0].WholeTime = p[0].finishTime - p[0].arrivalTime;
        p[0].weightWholeTime = p[0].WholeTime / p[0].serviceTime;
        res.add(p[0]);

        now = p[0].finishTime;

        //将剩余进程添加进待处理list
        for (int i = 1; i < p.length; i++) {
            list.add(p[i]);
        }

        while (list.size() != 0) {
            ProcessComputer next = FindNextPro(list, now);
            if (next.arrivalTime > now) {
                next.finishTime = next.arrivalTime + next.serviceTime;
                next.startTime = next.arrivalTime;
            } else {
                next.finishTime = now + next.serviceTime;
                next.startTime = now;
            }
            now = next.finishTime;
            next.WholeTime = next.finishTime - next.arrivalTime;
            next.weightWholeTime = (double) next.WholeTime / (double) next.serviceTime;
            res.add(next);
        }

        return res.toArray(new ProcessComputer[0]);

    }


    private static ProcessComputer FindNextPro(List<ProcessComputer> list, int now) {

        ProcessComputer pro=list.get(0);
        int index=0;
        for(int i=1;i<list.size();i++){
            if(list.get(i).serviceTime<pro.serviceTime&&list.get(i).arrivalTime<now){
                pro=list.get(i);
                index=i;
            }
        }
        list.remove(index);
        return pro;
    }

    private static void Out(ProcessComputer[] p) {
        DecimalFormat df = new DecimalFormat("#.00");
        float sumWT=0;
        float sumWWT=0;
        float AverageWT;
        float AverageWWT;
        for(int i=0;i<p.length;i++){
            System.out.println("时刻"+p[i].startTime+": 进程"+p[i].pid+"开始运行，完成时间为:"+p[i].finishTime+",周转时间为："+p[i].WholeTime+",带权周转时间为："+df.format(p[i].weightWholeTime));
            sumWT+=p[i].WholeTime;
            sumWWT+=p[i].weightWholeTime;
        }
        AverageWT=sumWT/p.length;
        AverageWWT=sumWWT/p.length;

        System.out.println("平均周转时间为："+df.format(AverageWT));
        System.out.println("平均带权周转时间为："+df.format(AverageWWT));
        System.out.println("---------------------------------------------------------");
    }

    public static void FCFS(ProcessComputer[] p){
        sortByArrive(p);
        for(int i=0;i<p.length;i++){
            if(i==0){
                p[i].finishTime=p[i].serviceTime+p[i].arrivalTime;
            }
            else{
                if(p[i].arrivalTime>p[i-1].finishTime){
                    p[i].finishTime=p[i].arrivalTime+p[i].serviceTime;
                    p[i].startTime=p[i].arrivalTime;
                }
                else{
                    p[i].finishTime=p[i-1].finishTime+p[i].serviceTime;
                    p[i].startTime=p[i-1].finishTime;
                }
            }
            //计算周转时间。
            p[i].WholeTime=p[i].finishTime-p[i].arrivalTime;
            p[i].weightWholeTime=(double)p[i].WholeTime/(double)p[i].serviceTime;
        }
    }

    private static void sortByArrive(ProcessComputer[] p) {
        ProcessComputer t;
        for(int i=0;i<p.length;i++){
            for(int j=i+1;j<p.length;j++){
                if(p[i].arrivalTime<p[j].arrivalTime){
                    t=p[i];
                    p[i]=p[j];
                    p[j]=t;
                }
            }
        }
    }
}
