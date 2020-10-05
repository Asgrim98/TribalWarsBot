package com.company;

import com.company.enums.Army;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import picocli.CommandLine;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AttackManager {

    Village myVillage;
    WebDriver driver;
    WebDriverWait wait;
    ArrayList<Village> villageToAttack;
    Integer possibleFarmAttacks;

    public AttackManager(Village village, WebDriver driver, WebDriverWait wait) throws FileNotFoundException {
        this.myVillage = village;
        this.driver = driver;
        this.wait = wait;
    }

    public void countAttacks() throws FileNotFoundException {

        Integer spears = Integer.valueOf(myVillage.getArmy().get(Army.SPEAR));
        Integer swords = Integer.valueOf(myVillage.getArmy().get(Army.SWORD));
        Integer lessTroops = (spears > swords) ?  swords : spears;

        possibleFarmAttacks = lessTroops / 2;
        villageToAttack = readFile();
    }

    public void planAttacks() throws IOException {

        for (Iterator<Village> it = villageToAttack.iterator(); it.hasNext();) {

            Village village = it.next();
            if (possibleFarmAttacks == 0) {
                System.out.println("Brak wojsk");
                break;
            } else {

                attack(village.getCords());
                possibleFarmAttacks--;
                driver.manage().timeouts().implicitlyWait(150, TimeUnit.MILLISECONDS);
            }
            File myObj = new File("C:\\PLIKI\\Programowanie\\TribalWarsBot\\src\\main\\java\\com\\company\\files\\actual.txt");
            FileWriter myWriter = new FileWriter(myObj);
            myWriter.write( village.getCords() );
            myWriter.close();
            it.remove();

        }
    }


    private void attack(String cords){

        driver.get(DriverManager.place);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("unit_input_spear")));
        driver.findElement(By.id("unit_input_spear")).sendKeys("2");
        driver.findElement(By.id("unit_input_sword")).sendKeys("2");
        driver.findElement(By.className("target-input-field")).sendKeys(cords);
        driver.findElement(By.id("target_attack")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("troop_confirm_go")));
        driver.findElement(By.id("troop_confirm_go")).click();
    }

    private float calculateDistance(String vil){

//        int cordX = Integer.parseInt(vil.substring(0, 3));
//        int cordY = Integer.parseInt(vil.substring(4, 7));
//
//        int myCordX = Integer.parseInt(cords.substring(0, 3));
//        int myCordY = Integer.parseInt(cords.substring(4, 7));
//
//        float a = Math.abs(cordX - myCordX);
//        float b = Math.abs(cordY - myCordY);
//
//        float c = (float) Math.sqrt( a*a + b*b);
//
//        return c;
        return Float.parseFloat(null);
    }

    private ArrayList<Village> readFile() throws FileNotFoundException {

        ArrayList<String> result = new ArrayList<>();
        ArrayList<Village> enemyVillages = new ArrayList<>();

        File file = new File("C:\\PLIKI\\Programowanie\\TribalWarsBot\\src\\main\\java\\com\\company\\files\\cords.txt");

        System.out.println("Czytam");
        try (Scanner s = new Scanner(new FileReader(file))) {
            while (s.hasNext()) {
                result.add(s.nextLine());
            }
            System.out.println("Przeczytalem");

            for(int i = 0; i < result.size(); i++){

                String tempCords = result.get(i);
                //Float distance = calculateDistance( result.get(i) );

                enemyVillages.add(new Village(tempCords));
            }

            return enemyVillages;
        }
    }
}
