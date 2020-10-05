package com.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import picocli.CommandLine;

import javax.management.monitor.CounterMonitor;
import java.io.IOException;
import java.util.Scanner;


public class Main{


    public static void main(String[] args) throws IOException {

        DriverManager driverManager = new DriverManager("Wetido", "wojomental10");

        while(true){

            Scanner in = new Scanner(System.in);
            System.out.print("Podaj komende: ");
            String name = in.nextLine();

            if( name.equals("attack")){
                driverManager.startAction();
            }

            if( name.equals("sprzedaj")){

                driverManager.tradePremium();
            }
        }



    }


}
