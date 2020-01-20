package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;

public class ConfigManager {
    private BSAddon bsAddon = BSAddon.get();

    public int getAutosaveTimer() { return bsAddon.getConfig().getInt("autosave-timer"); }
    public int getMySQLPort() { return bsAddon.getConfig().getInt("MySQL.port"); }
    public String getMySQLDataBase() { return bsAddon.getConfig().getString("MySQL.database"); }
    public String getMySQLHost() { return bsAddon.getConfig().getString("MySQL.host"); }
    public String getMySQLPassword() { return bsAddon.getConfig().getString("MySQL.password"); }
    public String getMySQLTable() { return bsAddon.getConfig().getString("MySQL.table"); }
    public String getMySQLUser() { return bsAddon.getConfig().getString("MySQL.username"); }
    public boolean isAutoSaveEnabled() {
        return bsAddon.getConfig().getBoolean("autosave-enabled");
    }
    public boolean isMySQLEnabled() {
        return bsAddon.getConfig().getBoolean("MySQL.enabled");
    }
}
