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

        normalizeArmyStrings();
        printVillageData();
    }

    private void printVillageData(){

        System.out.println(sources.get(Sources.WOOD));
        System.out.println(sources.get(Sources.STONE));
        System.out.println(sources.get(Sources.IRON));

        System.out.println(army.get(Army.SPEAR));
        System.out.println(army.get(Army.SWORD));
        System.out.println(army.get(Army.SCOUT));
    }

    private void normalizeArmyStrings(){

        army.replaceAll((k, v) -> v.substring(1, v.length() - 1));


//        for (Map.Entry<Army, String> e : army.entrySet())
//            e.getValue().substring(1, e.getValue().length() - 1);
    }

}