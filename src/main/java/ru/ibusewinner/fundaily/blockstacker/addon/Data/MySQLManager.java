package ru.ibusewinner.fundaily.blockstacker.addon.Data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.MessageManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MySQLManager {
    private BSAddon plugin = BSAddon.get();
    private MessageManager mm = plugin.mm;

    private PlacedStacks placedStacks = plugin.placedStacks;
    private String table = plugin.cm.getMySQLTable();

    public void checkForTable(String table) {
        try{
            sendQuery("CREATE TABLE IF NOT EXISTS `"+table+"` ( `ID` INT NOT NULL , `VALUE` LONGTEXT NOT NULL , INDEX `INDEX` (`ID`)) ENGINE = InnoDB;");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAndSave() {
        if (plugin.getConnection() != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + mm.sqlUpdateAndSave());
            for (StackerPlaced sp : placedStacks.getPlacedStacksMap().values()) {
                int id = sp.getId();
                if (containsID(id)) {
                    update(id, sp.toString());
                } else {
                    try {
                        sendQuery("INSERT INTO " + table + " (ID, VALUE) VALUES ('" + sp.getId() + "','" + sp.toString() + "')");
                    } catch (SQLException e) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlError());
                        e.printStackTrace();
                    }

                }

            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlInvalidConnection());
            return;
        }
    }

    public void save() {
        if (plugin.getConnection() != null) {
            for (StackerPlaced sp : placedStacks.getPlacedStacksMap().values()) {
                try {
                    sendQuery("INSERT INTO " + table + " (ID, VALUE) VALUES ('" + sp.getId() + "','" + sp.toString() + "')");
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlError());
                }

            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlInvalidConnection());
            return;
        }
    }

    public boolean containsID(int id) {
        if (plugin.getConnection() != null) {
            try {
                PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE ID=?");
                statement.setString(1, "" + id);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlError());
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlInvalidConnection());
        }
        return true;
    }

    public void update(int id, String input) {
        if (plugin.getConnection() != null) {
            try {
                sendQuery("UPDATE " + table + " SET VALUE='" + input + "' WHERE ID='" + id + "'");
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlError());
            }

        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlInvalidConnection());
        }
    }


    public int load() {
        if (plugin.getConnection() != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + mm.sqlLoad());
            try {
                PreparedStatement sql = plugin.getConnection().prepareStatement("SELECT VALUE FROM `" + table + "`");
                ResultSet set = sql.executeQuery();
                ResultSetMetaData setMetaData = set.getMetaData();
                int amt = 0;

                while (set.next()) {
                    for (int i = 0; i < setMetaData.getColumnCount(); i++) {
                        String data = set.getString(i + 1);

                        new StackerPlaced(data);
                        amt++;
                    }
                }
                return amt;

            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlError());
            }

        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlInvalidConnection());
        }
        return 0;
    }


    public void sendQuery(String sql) throws SQLException {
        if (plugin.getConnection() != null) {
            plugin.getConnection().createStatement().execute(sql);

        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + mm.sqlInvalidConnection());
        }
    }
}
