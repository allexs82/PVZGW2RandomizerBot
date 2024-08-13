package ru.allexs82.enums;

public enum ModesOld {
    TURF_TAKEOVER("Turf Takeover"),
    GARDENS_AND_GRAVEYARDS("Gardens & Graveyards"),
    HERBAL_ASSAULT("Herbal Assault"),
    TEAM_VANQUISH("Team Vanquish"),
    VANQUISH_CONFIRMED("Vanquish Confirmed"),
    SUBURBINATION("Suburbination"),
    GNOME_BOMB("Gnome Bomb"),
    SOIL_SURVIVORS("Soil Survivors"),
    CAPTURE_THE_TACO("Capture the Taco"),
    CATS_VS_DINOS("Cats vs. Dinos");

    private final String name;

    ModesOld(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
