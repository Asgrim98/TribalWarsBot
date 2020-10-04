package com.company.enums;

public enum  Army {

    SPEAR("spear figther"),
    SWORD("swordsman"),
    AXE("axeman"),
    ARCHER("archer"),
    SCOUT("scout"),
    LIGHT("light cavalry"),
    MOUNTED("mounted archer"),
    HEAVY("heavy cavalry"),
    RAM("ram"),
    CATAPULT("catapult"),
    PALADIN("paladin"),
    NOBLEMAN("nobleman");


    private final String text;

    Army(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
