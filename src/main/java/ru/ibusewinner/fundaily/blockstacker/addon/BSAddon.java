package ru.ibusewinner.fundaily.blockstacker.addon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import ru.ibusewinner.fundaily.blockstacker.addon.Commands.Console.BSCommand;
import ru.ibusewinner.fundaily.blockstacker.addon.Data.MySQLManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Data.PlacedStacks;
import ru.ibusewinner.fundaily.blockstacker.addon.Events.*;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.*;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BSAddon extends Addon {
    private static BSAddon instance;
    public static BSAddon get() {return instance;}

    public static List<IStack> stackers;
    public ConfigManager cm;
    public MessageManager mm;

    private Connection con;
    private String host, port, database, user, password, table;

    public boolean useSQL;
    public BlockFactory blockFactory;
    public Utils utils;
    public PlacedStacks placedStacks;
    public Worth worth;
    public MySQLManager myM;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        BSLogger.msg("§5[BlockStacker] Default config saved.");
        cm = new ConfigManager();
        BSLogger.msg("§5[BlockStacker] ConfigManager loaded.");
        registerInstances();
        BSLogger.msg("§5[BlockStacker] Instances registered.");
        registerEvents();
        BSLogger.msg("§5[BlockStacker] Events registered.");
        stackers = new ArrayList<>();
        Settings.addAllStackers();
        BSLogger.msg("§5[BlockStacker] Stackers added.");

        useSQL = cm.isMySQLEnabled();
        BSLogger.msg("§5[BlockStacker] Use SQL: "+useSQL+".");

        if(useSQL) {
            try {
                BSLogger.msg("§5[BlockStacker] Trying to connect to DataBase...");
                connectSQL();
                BSLogger.msg("Con closed: "+con.isClosed());
                if(con == null) {
                    BSLogger.msg("Con is null!");
                }
                if(con != null && !con.isClosed()) {
                    BSLogger.msg("§5[BlockStacker] Checking table...");
                    myM.checkForTable(table);
                    new DataBasePing(this, BentoBox.getInstance());
                    BSLogger.msg("§5[BlockStacker] Now pinging database.");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"[BlockStackerX] Loaded "+myM.load()+" Stackers");
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED +"[BlockStackerX] Dyrak nastroy config.yml debil blyat");
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }

            if(cm.isAutoSaveEnabled()) {
                new AutoSave(BentoBox.getInstance());
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"[BlockStackerX] Конфиг настрой. Плагин не работает без му~сука");
        }
    }

    @Override
    public void onDisable() {
        myM.updateAndSave();
        disconnectSql();
        BSLogger.msg("§5[BlockStacker] Connection closed.");
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new StackerPlace(), BentoBox.getInstance());
        Bukkit.getPluginManager().registerEvents(new StackerBreak(), BentoBox.getInstance());
        Bukkit.getPluginManager().registerEvents(new StackerAddBlocks(), BentoBox.getInstance());
        Bukkit.getPluginManager().registerEvents(new StackerRemoveBlocks(), BentoBox.getInstance());
        Bukkit.getPluginManager().registerEvents(new BentoCalculation(), BentoBox.getInstance());

        new BSCommand(this);
    }

    private void registerInstances() {
        mm = new MessageManager();
        blockFactory = new BlockFactory();
        utils = new Utils();
        placedStacks = new PlacedStacks();
        worth = new Worth();
        myM = new MySQLManager();
    }

    private void connectSQL() {
        host = cm.getMySQLHost();
        database = cm.getMySQLDataBase();
        user = cm.getMySQLUser();
        password = cm.getMySQLPassword();
        table = cm.getMySQLTable();
        port = ""+cm.getMySQLPort();

        try {
            if(getConnection() != null && !getConnection().isClosed()) {
                BSLogger.msg("Con is closed: "+getConnection().isClosed());
                return;
            }

            this.con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database,user,password);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[BlockStackerX] Successfully connected to mySql");
        }catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not connect to mySql!");
            ex.printStackTrace();
        }
    }

    private void disconnectSql() {
        try {
            if (getConnection() != null || !getConnection().isClosed()) {
                getConnection().close();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[BlockStackerX] Successfully closed mySql connection");
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[BlockStackerX] Failed to close mySql connection!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }

    public String getTable() {
        return table;
    }
}
