package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import org.bukkit.scheduler.BukkitRunnable;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Data.MySQLManager;
import world.bentobox.bentobox.BentoBox;

import java.sql.SQLException;

public class AutoSave {
    private BSAddon bsAddon = BSAddon.get();
    public AutoSave(BentoBox plugin)
    {
        if (bsAddon.cm.isAutoSaveEnabled()) {
            MySQLManager mySqlManager = bsAddon.myM;
            int saveIntervalMinutes = bsAddon.cm.getAutosaveTimer();
            int minute = (60 * 20);

            new BukkitRunnable() {
                @Override
                public void run() {

                    try {
                        if(bsAddon.getConnection() != null && !bsAddon.getConnection().isClosed()) {
                            mySqlManager.updateAndSave();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }.runTaskTimer(plugin, saveIntervalMinutes * minute, saveIntervalMinutes * minute);
        }
    }
}
