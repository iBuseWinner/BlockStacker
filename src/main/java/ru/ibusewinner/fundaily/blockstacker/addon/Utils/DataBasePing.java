package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import org.bukkit.scheduler.BukkitRunnable;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import world.bentobox.bentobox.BentoBox;

import java.sql.SQLException;

public class DataBasePing {
    public DataBasePing(BSAddon plugin, BentoBox pl) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try{
                    plugin.myM.sendQuery("SELECT * FROM `"+plugin.getTable()+"`");
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(pl, 60*20,60*20);
    }
}
