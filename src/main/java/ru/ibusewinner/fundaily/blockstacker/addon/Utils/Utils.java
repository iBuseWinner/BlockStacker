package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import world.bentobox.bentobox.BentoBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {
    private BSAddon bsAddon = BSAddon.get();
    private MessageManager mm = bsAddon.mm;

    public boolean playerIsOnTeamOf(UUID player, UUID target) {
        if(BentoBox.getInstance().getIslands().getIsland(Bukkit.getWorld("world"), target).getMembers().containsKey(player)) {
            return true;
        } else return false;
    }

    public boolean isPickaxe(Material mat) {
        return mat.equals(Material.WOODEN_PICKAXE) || mat.equals(Material.STONE_PICKAXE) || mat.equals(Material.GOLDEN_PICKAXE) || mat.equals(Material.IRON_PICKAXE) || mat.equals(Material.DIAMOND_PICKAXE);
    }

    public List<Material> getBlockList(String type) {
        List<String> confList = new ArrayList<>();
        for(IStack stack : Settings.getAllStackers()) {
            if(stack.getCfgName().equalsIgnoreCase(type)) {
                confList.addAll(stack.getAllowedBlocks());
            }
        }
        List<Material> matList = new ArrayList<>();
        for (String s : confList) {
            matList.add(Material.valueOf(s.toUpperCase()));
        }
        return matList;
    }

    public Vector getOffset(String type) {
        String[] data = new String[]{};

        for(IStack stack : Settings.getAllStackers()) {
            if(stack.getCfgName().equalsIgnoreCase(type)) {
                data = stack.getDisplayOffset().split(",");
            }
        }

        float x;
        float y;
        float z;

        try {

            x = Float.parseFloat(data[0]);
            y = Float.parseFloat(data[1]);
            z = Float.parseFloat(data[2]);
        } catch (Exception e) {
            BSLogger.msg("displayOffset of Stacker " + type + " is invalid!!!");
            return new Vector(0, 0, 0);
        }
        return new Vector(x, y, z);
    }
}
