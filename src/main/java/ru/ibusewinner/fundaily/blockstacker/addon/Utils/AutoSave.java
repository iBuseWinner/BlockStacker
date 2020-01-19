package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import org.bukkit.scheduler.BukkitRunnable;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import world.bentobox.bentobox.BentoBox;

import java.sql.SQLException;

public class AutoSave {
    private BSAddon bsAddon = BSAddon.get();
    public AutoSave(BentoBox plugin)
    {
        if (bsAddon.cm.isAutoSaveEnabled()) {
            //YamlData yamlData = plugin.yamlData;
            //MySqlManager mySqlManager = plugin.mySqlManager;
            int saveIntervalMinutes = bsAddon.cm.getAutosaveTimer();
            int minute = (60 * 20);

            new BukkitRunnable() {
                @Override
                public void run() {

                    try {
                        if (!bsAddon.useSQL) {
                            //yamlData.save();
                        } else {
                            if(bsAddon.getConnection() != null && !bsAddon.getConnection().isClosed()) {
                                //mySqlManager.updateAndSave();
                            } else {
                                //yamlData.save();
                            }
                            /*if (plugin.getConnection() != null && !plugin.getConnection().isClosed()) {
                                mySqlManager.updateAndSave();
                            } else {
                                yamlData.save();
                            }*/
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }.runTaskTimer(plugin, saveIntervalMinutes * minute, saveIntervalMinutes * minute);
        }
    }
}
