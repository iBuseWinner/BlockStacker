package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private BSAddon bsAddon = BSAddon.get();

    private String prefix = "&9&lBlockStacker &c&l// &9";
    private List<String> usage = new ArrayList<>();
    private String calculationEvent = "Levels From Stackers:&b&l %LEVELS%";
    private String receiveStacker = "You have received a blockStacker!";
    private String playerNotFound = "Player Could not be found";
    private String notAnInt = "Amount must be an Integer!";
    private String worthSave = "Worth of %ITEM% is now &b&l%VALUE%";
    private String worth = "Worth: &b&l%WORTH%";
    private String stackerNotLoaded = "The given stacker type could not be found";
    private String cmdNotRecognized = "Command not recognized!";
    private String onlyPlayers = "Only Players can run this command";
    private String brokeStacker = "Stacker broke!";
    private String cantStackStackers = "You cant stack other stackers";
    private String error = "An Error has occurred";
    private String chosenMaterial = "You have chosen to stack %BLOCKTYPE%";
    private String addValue = "Please add a value to your command";
    private String incrementMessage = "You have added %AMOUNT% blocks to your stacker!";
    private String decrementMessage = "You have taken %AMOUNT% blocks from your stacker!";
    private String noBlockInHand = "Please hold a block";
    private String stackerOccupied = "You cannot stack this block whilst stacker occupied by %BLOCK%";
    private String incorrectBlockInHand = "This block cannot be stacked here!";
    private String notYourStacker = "You have no permission to use this stacker!";
    private String disabledStacker = "This stacker is disabled - Type must be of type BLOCK";
    private String maxStack = "Maximum of this stacker is %MAX% !";
    private String stackerEmpty = "Stacker has no value left!";
    private String noInventorySpace = "Please make some room in your inventory!";
    private String holdPickaxe = "Please hold a pickaxe to break stacker";
    private String noPermission = "You have no permission to run this command!";
    private String pluginReload = "Plugin has reloaded successfully";
    private String syncAttempt = "Attempting to sync yaml with mySql!";
    private String syncSuccess = "Sync Successful!";
    private String syncFail = "Sync Failed!";
    private String displayReload = "Reloading displays!";
    private String sqlConnectionError = "Could not find connection to database!";

    private String sqlConnect = "[BlockStackerX] Attempting to connect to database";
    private String sqlError = "[BlockStackerX] Failed to execute query please check your database information!";
    private String sqlUpdateAndSave = "[BlockStackerX] Attempting to update and save to mySql Database!";
    private String sqlLoad = "[BlockStackerX] Attempting to load data!";
    private String sqlInvalidConnection = "[BlockStackerX] Please check your database information or turn mySql OFF!";
    private String stackerFormatError = "[BlockStackerX] Failed to format dataString back to object!";
    private String yamlSaveSqlError = "[BlockStackerX] Could not save to sql saving to yaml!";
    private String yamlLoadSqlError = "[BlockStackerX] Could not load from sql, attempting to load from Yaml File";
    private String yamlSave = "[BlockStackerX] Attempting to save data to yaml file!";
    private String yamlLoad = "[BlockStackerX] Attempting to load data from yaml file!";
    private String yamlUse = "[BlockStackerX] Using Yaml Storage";
    private String invalidStacker = "[BlockStackerX] Stacker %TYPE%`s Configuration is invalid!";

    public String applyCC(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public void sendUsage(Player p) {
        for(String s : usage) {
            p.sendMessage(applyCC(s));
        }
    }

    public List<String> applyCC(List<String> input) {
        List<String> ret = new ArrayList<>();
        for(String s : input) {
            ret.add(ChatColor.translateAlternateColorCodes('&',s));
        }
        return ret;
    }

    public String prefix() { return prefix; }
    public String calculationEvent() { return calculationEvent; }
    public String receiveStacker() { return receiveStacker; }
    public String playerNotFound() {return playerNotFound;}
    public String notAnInt() {return notAnInt;}
    public String worthSave() {return worthSave;}
    public String worth() {return worth;}
    public String stackerNotLoaded() {return stackerNotLoaded;}
    public String cmdNotRecognized() {return cmdNotRecognized;}
    public String onlyPlayers() {return onlyPlayers;}
    public String brokeStacker() {return brokeStacker;}
    public String cantStackStackers() {return cantStackStackers;}
    public String error() {return error;}
    public String chosenMaterial() {return chosenMaterial;}
    public String addValue() {return addValue;}
    public String incrementMessage() {return incrementMessage;}
    public String decrementMessage() {return decrementMessage;}
    public String noBlockInHand() {return noBlockInHand;}
    public String stackerOccupied() {return stackerOccupied;}
    public String incorrectBlockInHand() {return incorrectBlockInHand;}
    public String notYourStacker() {return notYourStacker;}
    public String disabledStacker() {return disabledStacker;}
    public String maxStack() {return maxStack;}
    public String stackerEmpty() {return stackerEmpty;}
    public String noInventorySpace() {return noInventorySpace;}
    public String holdPickaxe() {return holdPickaxe;}
    public String noPermission() {return noPermission;}
    public String pluginReload() {return pluginReload;}
    public String syncAttempt() {return syncAttempt;}
    public String syncSuccess() {return syncSuccess;}
    public String syncFail() {return syncFail;}
    public String displayReload() {return displayReload;}
    public String sqlConnectionError() {return sqlConnectionError;}
    public String sqlConnect() {return sqlConnect;}
    public String sqlError() {return sqlError;}
    public String sqlUpdateAndSave() {return sqlUpdateAndSave;}
    public String sqlLoad() {return sqlLoad;}
    public String sqlInvalidConnection() {return sqlInvalidConnection;}
    public String stackerFormatError() {return stackerFormatError;}
    public String yamlSaveSqlError() {return yamlSaveSqlError;}
    public String yamlLoadSqlError() {return yamlLoadSqlError;}
    public String yamlSave() {return yamlSave;}
    public String yamlLoad() {return yamlLoad;}
    public String yamlUse() {return yamlUse;}
    public String invalidStacker() {return invalidStacker;}
}
