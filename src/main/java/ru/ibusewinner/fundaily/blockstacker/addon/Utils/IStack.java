package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import java.util.List;

public class IStack {
    private String cfgName;
    private String displayOffset;
    private String itemType;
    private String displayName;
    private boolean glow;
    private List<String> lore;
    private String valueFormat;
    private int max;
    private boolean teamStacking;
    private List<String> allowedBlocks;
    private String itemDisplayName;
    private boolean itemGlow;
    private List<String> itemLore;

    public IStack(String cfgName, String displayOffset, String itemType, String displayName, boolean glow, List<String> lore, String valueFormat,
                  int max, boolean teamStacking, List<String> allowedBlocks, String itemDisplayName, boolean itemGlow, List<String> itemLore) {
        this.cfgName = cfgName;
        this.displayOffset = displayOffset;
        this.itemType = itemType;
        this.displayName = displayName;
        this.glow = glow;
        this.lore = lore;
        this.valueFormat = valueFormat;
        this.max = max;
        this.teamStacking = teamStacking;
        this.allowedBlocks = allowedBlocks;
        this.itemDisplayName = itemDisplayName;
        this.itemGlow = itemGlow;
        this.itemLore = itemLore;
    }

    public IStack() {}

    public String getDisplayName() { return displayName; }
    public List<String> getLore() { return lore; }
    public String getCfgName() { return cfgName; }
    public String getDisplayOffset() { return displayOffset; }
    public String getItemType() { return itemType; }
    public String getValueFormat() { return valueFormat; }
    public int getMax() { return max; }
    public List<String> getAllowedBlocks() { return allowedBlocks; }
    public List<String> getItemLore() { return itemLore; }
    public String getItemDisplayName() { return itemDisplayName; }
    public boolean itemGlow() { return itemGlow; }
    public boolean glow() { return glow; }
    public boolean teamStacking() { return teamStacking; }

    //Sets
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDisplayOffset(String displayOffset) {
        this.displayOffset = displayOffset;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setAllowedBlocks(List<String> allowedBlocks) {
        this.allowedBlocks = allowedBlocks;
    }

    public void setValueFormat(String valueFormat) {
        this.valueFormat = valueFormat;
    }

    public void setItemDisplayName(String itemDisplayName) {
        this.itemDisplayName = itemDisplayName;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setItemGlow(boolean itemGlow) {
        this.itemGlow = itemGlow;
    }

    public void setItemLore(List<String> itemLore) {
        this.itemLore = itemLore;
    }

    public void setTeamStacking(boolean teamStacking) {
        this.teamStacking = teamStacking;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
