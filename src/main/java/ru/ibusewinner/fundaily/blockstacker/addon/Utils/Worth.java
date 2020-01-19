package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import org.bukkit.Material;

public class Worth {
    public static int EMERALD_BLOCK = 5;
    public static int DIAMOND_BLOCK = 4;
    public static int GOLD_BLOCK = 3;
    public static int IRON_BLOCK = 2;

    public static int getWorth(Material mat) {
        if(mat.equals(Material.EMERALD_BLOCK)) {
            return EMERALD_BLOCK;
        } else if(mat.equals(Material.DIAMOND_BLOCK)) {
            return DIAMOND_BLOCK;
        } else if(mat.equals(Material.GOLD_BLOCK)) {
            return GOLD_BLOCK;
        } else if(mat.equals(Material.IRON_BLOCK)) {
            return IRON_BLOCK;
        } else {
            return 0;
        }
    }
}
