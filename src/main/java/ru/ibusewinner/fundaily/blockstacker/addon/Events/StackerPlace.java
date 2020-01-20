package ru.ibusewinner.fundaily.blockstacker.addon.Events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.SLocation;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.DisplayManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.MessageManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Utils;
import world.bentobox.bentobox.BentoBox;

import java.util.UUID;

public class StackerPlace implements Listener {
    private BSAddon plugin = BSAddon.get();
    private Utils util = plugin.utils;
    private MessageManager mm = plugin.mm;

    @EventHandler(priority = EventPriority.LOWEST)
    public void place(BlockPlaceEvent event) {
        if (!event.isCancelled()) {
            ItemStack item = event.getItemInHand();
            NBTItem nbtItem = new NBTItem(item);
            if (nbtItem.hasNBTData()) {
                Player player = event.getPlayer();
                UUID uuid = player.getUniqueId();
                SLocation loc = new SLocation(event.getBlock().getLocation());

                if (nbtItem.hasKey("BlockStackerX")) {
                    if (player.hasPermission("bs.placeStacker")) {
                        Material mat = event.getBlock().getType();
                        String type = nbtItem.getString("BlockStackerX");
                        Location displayLoc = event.getBlock().getLocation().add(util.getOffset(type));

                        Hologram hologram = DisplayManager.createDisplay(BentoBox.getInstance(), type, 0, displayLoc);
                        int newID = plugin.placedStacks.getNewID();
                        StackerPlaced sp = new StackerPlaced(newID, type, uuid, null, loc, hologram, 0);
                    }else{
                        event.setCancelled(true);
                        player.sendMessage(mm.applyCC(mm.noPermission()));
                    }
                } else if (nbtItem.hasKey("BlockStackerX_nonSolid")) {
                    if (player.hasPermission("bs.placePickupStacker")) {
                        String type = nbtItem.getString("StackerType");
                        Material chosenMat = Material.valueOf(nbtItem.getString("StackerMaterial").toUpperCase());
                        int value = nbtItem.getInteger("StackerValue");

                        Location displayLoc = event.getBlock().getLocation().add(util.getOffset(type));


                        Hologram hologram = DisplayManager.createDisplay(BentoBox.getInstance(), type, value, displayLoc);
                        int newID = plugin.placedStacks.getNewID();
                        StackerPlaced sp = new StackerPlaced(newID, type, uuid, chosenMat, loc, hologram, value);
                    }else{
                        event.setCancelled(true);
                        player.sendMessage(mm.applyCC(mm.noPermission()));
                    }
                }
            }
        }


    }
}
