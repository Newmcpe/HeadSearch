package ru.newmcpe.headsearch.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    public static Location getLocationFromString(String locationString) {
        String[] loc = locationString.split(":");

        return new Location(getDefaultWorld(), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
    }

    public static World getDefaultWorld() {
        return Bukkit.getWorlds().get(0);
    }
}
