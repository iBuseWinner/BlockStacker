package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    public static void addAllStackers() {
        String valueFormat = "§9Содержит: §f%VALUE%";
        String displayOffset = "0.5,1.7,0.5";
        int max = 1000;
        int twoMax = 10000;
        boolean all = true;
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§a");
        lore.add("§eПоставьте на свой остров.");
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add("§1§e=========================");
        itemLore.add("§1");
        itemLore.add("§eУже содержит§f %VALUE% §eблоков");
        itemLore.add("§2");
        itemLore.add("§2§e=========================");

        ArrayList<String> allowedBlocks1 = new ArrayList<>();
        allowedBlocks1.add("IRON_BLOCK");

        IStack itack = new IStack("itack",displayOffset,"IRON_BLOCK","§fЖелезный стакер",all,lore,valueFormat,
                max,all,allowedBlocks1,"§4Железный стакер",all,itemLore);

        ArrayList<String> allowedBlocks2 = new ArrayList<>();
        allowedBlocks2.add("GOLD_BLOCK");

        IStack gstack = new IStack("gtack",displayOffset,"GOLD_BLOCK","§eЗолотой стакер",all,lore,valueFormat,
                max,all,allowedBlocks2,"§eЗолотой стакер",all,itemLore);

        ArrayList<String> allowedBlocks3 = new ArrayList<>();
        allowedBlocks3.add("DIAMOND_BLOCK");

        IStack dstack = new IStack("dtack",displayOffset,"DIAMOND_BLOCK","§bАлмазный стакер",all,lore,valueFormat,
                max,all,allowedBlocks3,"§bАлмазный стакер",all,itemLore);

        ArrayList<String> allowedBlocks4 = new ArrayList<>();
        allowedBlocks4.add("EMERALD_BLOCK");

        IStack estack = new IStack("etack",displayOffset,"EMERALD_BLOCK","§aИзумрудный стакер",all,lore,valueFormat,
                max,all,allowedBlocks4,"§aИзумрудный стакер",all,itemLore);

        ArrayList<String> allowedBlocks5 = new ArrayList<>();
        allowedBlocks5.add("IRON_BLOCK");

        IStack itack2 = new IStack("itack2",displayOffset,"IRON_BLOCK","§fЖелезный стакер",all,lore,valueFormat,
                twoMax,all,allowedBlocks5,"§4Железный стакер",all,itemLore);

        ArrayList<String> allowedBlocks6 = new ArrayList<>();
        allowedBlocks6.add("GOLD_BLOCK");

        IStack gstack2 = new IStack("gtack2",displayOffset,"GOLD_BLOCK","§eЗолотой стакер",all,lore,valueFormat,
                twoMax,all,allowedBlocks6,"§eЗолотой стакер",all,itemLore);

        ArrayList<String> allowedBlocks7 = new ArrayList<>();
        allowedBlocks7.add("DIAMOND_BLOCK");

        IStack dstack2 = new IStack("dtack2",displayOffset,"DIAMOND_BLOCK","§bАлмазный стакер",all,lore,valueFormat,
                twoMax,all,allowedBlocks7,"§bАлмазный стакер",all,itemLore);

        ArrayList<String> allowedBlocks8 = new ArrayList<>();
        allowedBlocks8.add("EMERALD_BLOCK");

        IStack estack2 = new IStack("etack2",displayOffset,"EMERALD_BLOCK","§aИзумрудный стакер",all,lore,valueFormat,
                twoMax,all,allowedBlocks8,"§aИзумрудный стакер",all,itemLore);

        BSAddon.stackers.add(itack);
        BSAddon.stackers.add(gstack);
        BSAddon.stackers.add(dstack);
        BSAddon.stackers.add(estack);
        BSAddon.stackers.add(itack2);
        BSAddon.stackers.add(gstack2);
        BSAddon.stackers.add(dstack2);
        BSAddon.stackers.add(estack2);
    }

    public static List<IStack> getAllStackers() {
        return BSAddon.stackers;
    }

    public static IStack getStackByType(String type) {
        IStack iStack = new IStack();
        for(IStack stack : getAllStackers()) {
            if(stack.getCfgName().equalsIgnoreCase(type)) {
                iStack = stack;
            }
        }
        return iStack;
    }

}
