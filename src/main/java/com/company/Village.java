package com.company;

import com.company.enums.Army;
import com.company.enums.Sources;

import java.util.HashMap;
import java.util.Map;

public class Village {

    private String name;
    private String cords;
    private String points;
    private HashMap<Sources, String>sources;
    private HashMap<Army, String> army;

    public Village(HashMap<Sources,String>sources, HashMap<Army, String>army){

        this.sources = sources;
        this.army = army;
    }

    public Village(String cords){
        this.cords = cords;
    }

    public Village() { }

    private void printVillageData(){

        System.out.println(sources.get(Sources.WOOD));
        System.out.println(sources.get(Sources.STONE));
        System.out.println(sources.get(Sources.IRON));

        System.out.println(army.get(Army.SPEAR));
        System.out.println(army.get(Army.SWORD));
        System.out.println(army.get(Army.SCOUT));
    }

    public void normalizeArmyStrings(){

        army.replaceAll((k, v) -> v.substring(1, v.length() - 1));
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCords() {
        return cords;
    }

    public void setCords(String cords) {
        this.cords = cords;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public HashMap<Sources, String> getSources() {
        return sources;
    }

    public void setSources(HashMap<Sources, String> sources) {
        this.sources = sources;
    }

    public HashMap<Army, String> getArmy() {
        return army;
    }

    public void setArmy(HashMap<Army, String> army) {
        this.army = army;
    }
}
