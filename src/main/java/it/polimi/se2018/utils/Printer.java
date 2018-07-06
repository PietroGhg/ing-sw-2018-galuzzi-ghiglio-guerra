package it.polimi.se2018.utils;

//import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

public class Printer{
    private static Printer instance;
    private static boolean jAnsiActive = false;

    public static Printer getInstance(){
        if (instance == null) instance = new Printer();
        return instance;
    }

    private Printer(){
        System.out.println("Activate JAnsi (Recommended for Windows users)? [yes/no]");
        Scanner in = new Scanner(System.in);
        String ans;
        do{
            ans = in.nextLine();
        }while(!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("no")));
        if(ans.equalsIgnoreCase("yes")){
            jAnsiActive = true;
        }
    }

    public void println(String s){
        if(jAnsiActive){
            //AnsiConsole.out.println(s);
        }
        else System.out.println(s);
    }

    public static boolean isjAnsiActive(){
        return jAnsiActive;
    }
}