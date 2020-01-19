package ru.ibusewinner.fundaily.blockstacker.addon.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Data.PlacedStacks;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerNonSolid;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.DisplayManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.MessageManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Settings;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Utils;

public class StackerBreak implements Listener {
    private BSAddon plugin = BSAddon.get();
    private Utils util = plugin.utils;
    private MessageManager mm = plugin.mm;

    private PlacedStacks placedStacks = plugin.placedStacks;

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        if (placedStacks.contains(location)) {
            Player player = event.getPlayer();
            StackerPlaced sp = placedStacks.getStacker(location);
            boolean teamStacking = Settings.getStackByType(sp.getType()).teamStacking();
            if(sp.getUuid().equals(player.getUniqueId()) || teamStacking && util.playerIsOnTeamOf(player.getUniqueId(), sp.getUuid())) {
                if(player.hasPermission("bs.breakstacker")) {
                    Material mat = player.getItemInHand().getType();
                    if(util.isPickaxe(mat)) {
                        player.sendMessage(mm.applyCC(mm.brokeStacker()));
                        DisplayManager.deleteDisplay(sp.getDisplay());
                        new StackerNonSolid(sp.getType(), sp.getChosenMaterial(), sp.getValue(), player.getUniqueId());
                        placedStacks.deleteStack(location);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(mm.applyCC(mm.holdPickaxe()));
                    }
                } else {
                    event.setCancelled(true);
                    player.sendMessage(mm.applyCC(mm.noPermission()));
                }
            } else {
                event.setCancelled(true);
                player.sendMessage(mm.applyCC(mm.notYourStacker()));
            }
        }
    }
}
