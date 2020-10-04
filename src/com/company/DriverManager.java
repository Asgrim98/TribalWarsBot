package com.company;

import com.company.enums.Army;
import com.company.enums.Sources;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public DriverManager(String login, String password) throws IOException {

        this.login = login;
        this.password = password;
        villageList = new ArrayList<>();

        System.setProperty("webdriver.gecko.driver", "..\\..\\..\\chromedriver.exe");
        mainDriver = new ChromeDriver();
        mainDriver.get("https://www.plemiona.pl/");
        wait = new WebDriverWait(mainDriver, 30);

        login();
        initVillage();
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

        //Geting source WebElements
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("wood")));
        String wood = mainDriver.findElement(By.id("wood")).getText();
        String stone = mainDriver.findElement(By.id("stone")).getText();
        String iron = mainDriver.findElement(By.id("iron")).getText();

        //Add keys and values (Source, quantity)
        HashMap<Sources, String> sources = new HashMap<Sources, String>();
        sources.put(Sources.WOOD, wood);
        sources.put(Sources.STONE, stone);
        sources.put(Sources.IRON, iron);

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

        Village village = new Village(sources, army);
        villageList.add(village);

        return true;
    }
}
