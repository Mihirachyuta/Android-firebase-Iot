package com.example.firestore7;

import java.util.ArrayList;

public class ProgramBack {
    public String programName;
    public static ArrayList<ProgramTimeTemp> timeTempArrayList= new ArrayList<ProgramTimeTemp>();


    public ProgramBack(String programName){
        this.programName=programName;
    }
    public static class ProgramTimeTemp{
        public int temp, time;
        public ProgramTimeTemp(int temp, int time){
            this.temp=temp;
            this.time=time;
        }

        public int getTemp() {
            return temp;
        }

        public int getTime() {
            return time;
        }
    }


    public void addStep(int temp, int time){
        timeTempArrayList.add(new ProgramTimeTemp(temp,time));
    }
    /*public void runProgram() throws InterruptedException {
        int count=0;
        if(timeTempArrayList.isEmpty()==false & timeTempArrayList !=null){
            for (count=0;count<timeTempArrayList.size();count++){
                runStep(count);

            }

        }
    }*/
    /*public void runStep(int count){
        MainActivity.firebaseConnect.setTemp(timeTempArrayList.get(count).temp,timeTempArrayList.get(count).time);
    }*/

    public static ArrayList<ProgramTimeTemp> getTimeTempArrayList() {
        return timeTempArrayList;
    }
}

