package com.company.enums;

public enum Sources {

    WOOD("wood"),
    STONE("stone"),
    IRON("iron");

    private final String text;

    Sources(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
