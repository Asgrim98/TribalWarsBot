package com.company;

import com.company.enums.Army;
import com.company.enums.Sources;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DriverManager {

    private WebDriver mainDriver;
    private String login;
    private String password;
    private WebDriverWait wait;
    private List<Village> villageList;

    private final static String villageId = "29303";
    private final static String worldNumber = "157";

    // Buildings
    private final static String main = "https://pl" + worldNumber + ".plemiona.pl/game.php?village=" + villageId + "&screen=main";
    private final static String barracks = "https://pl" + worldNumber + ".plemiona.pl/game.php?village=" + villageId + "&screen=barracks";
    protected final static String place = "https://pl" + worldNumber + ".plemiona.pl/game.php?village=" + villageId + "&screen=place";
    private final static String stable = "https://pl" + worldNumber + ".plemiona.pl/game.php?village=" + villageId + "&screen=stable";
    private final static String marketPremium = "https://pl" + worldNumber +
            ".plemiona.pl/game.php?village=" + villageId + "&screen=market&mode=exchange";

    public DriverManager(){}

    public DriverManager(String login, String password) throws IOException {

        this.login = login;
        this.password = password;
        villageList = new ArrayList<Village>();

        System.setProperty("webdriver.gecko.driver", "..\\..\\..\\chromedriver.exe");

        mainDriver = new ChromeDriver();
        mainDriver.get("https://www.plemiona.pl/");
        wait = new WebDriverWait(mainDriver, 30);

        login();
        initVillage();
        //checkSources();
        //checkArmy();
    }

    protected void startAction() throws IOException {

        checkArmy();
        AttackManager attackManager = new AttackManager(villageList.get(0), mainDriver, wait);
        attackManager.countAttacks();
        attackManager.planAttacks();
    }

    private Boolean login(){

        mainDriver.findElement(By.id("user")).sendKeys(login);
        mainDriver.findElement(By.id("password")).sendKeys(password);
        mainDriver.findElement(By.className("btn-login")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("world_button_active")));
        mainDriver.findElement(By.className("world_button_active")).click();

        return true;
    }

    private Boolean initVillage(){

        Village village = new Village();
        villageList.add(village);

        return true;
    }

    private void checkSources(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("wood")));
        String wood = mainDriver.findElement(By.id("wood")).getText();
        String stone = mainDriver.findElement(By.id("stone")).getText();
        String iron = mainDriver.findElement(By.id("iron")).getText();

        //Add keys and values (Source, quantity)
        HashMap<Sources, String> sources = new HashMap<Sources, String>();
        sources.put(Sources.WOOD, wood);
        sources.put(Sources.STONE, stone);
        sources.put(Sources.IRON, iron);

        villageList.get(0).setSources(sources);
    }

    private void checkArmy(){

        //Getting army WebElements
        mainDriver.get(place);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("units_entry_all_spear")));
        String spear = mainDriver.findElement(By.id("units_entry_all_spear")).getText();
        String sword = mainDriver.findElement(By.id("units_entry_all_sword")).getText();
        //AXE //ARCHER
        String scout = mainDriver.findElement(By.id("units_entry_all_spy")).getText();
        String light = mainDriver.findElement(By.id("units_entry_all_light")).getText();
        //REST OF TROOPS

        HashMap<Army, String> army = new HashMap<Army, String>();
        army.put(Army.SPEAR, spear);
        army.put(Army.SWORD, sword);
        army.put(Army.SCOUT, scout);
        army.put(Army.LIGHT, light);

        villageList.get(0).setArmy(army);
        villageList.get(0).normalizeArmyStrings();
    }

    public void tradePremium(){

        checkSources();
        HashMap<Sources, String> sources = villageList.get(0).getSources();

        mainDriver.get(marketPremium);

        String woodRate = mainDriver
                .findElement(By.id("premium_exchange_rate_wood"))
                .findElement(By.className("premium-exchange-sep"))
                .getText();

        String stoneRate = mainDriver
                .findElement(By.id("premium_exchange_rate_stone"))
                .findElement(By.className("premium-exchange-sep"))
                .getText();

        String ironRate = mainDriver
                .findElement(By.id("premium_exchange_rate_iron"))
                .findElement(By.className("premium-exchange-sep"))
                .getText();

        //System.out.println(woodRate);

        while(true){
            if(mainDriver.findElement(By.id("market_merchant_available_count")).getText().equals("0")){
                System.out.println("Brak kupców");
                return;
            }
            if( Integer.parseInt(sources.get(Sources.WOOD)) >= Integer.parseInt(woodRate)){
                mainDriver.findElement(By.id("premium_exchange_sell_wood"))
                        .findElement(By.className("premium-exchange-input")).sendKeys(woodRate);

            } else if( Integer.parseInt(sources.get(Sources.STONE)) >= Integer.parseInt(stoneRate)){
                mainDriver.findElement(By.id("premium_exchange_sell_stone"))
                        .findElement(By.className("premium-exchange-input")).sendKeys(woodRate);

            } else if( Integer.parseInt(sources.get(Sources.IRON)) >= Integer.parseInt(ironRate)){
                mainDriver.findElement(By.id("premium_exchange_sell_iron"))
                        .findElement(By.className("premium-exchange-input")).sendKeys(ironRate);
            } else {
                return;
            }

            mainDriver.findElement(By.className("btn-premium-exchange-buy")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("evt-confirm-btn")));
            mainDriver.findElement(By.className("evt-confirm-btn")).click();
        }
    }
}
