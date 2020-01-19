package ru.ibusewinner.fundaily.blockstacker.addon.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Data.PlacedStacks;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.MessageManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Settings;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Utils;

import java.util.Objects;
import java.util.UUID;

public class StackerRemoveBlocks implements Listener {
    private BSAddon plugin = BSAddon.get();
    private Utils util = plugin.utils;
    private MessageManager mm = plugin.mm;

    private PlacedStacks placedStacks = plugin.placedStacks;

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
            if (placedStacks.contains(location)) {
                Player player = event.getPlayer();
                UUID uuid = player.getUniqueId();
                StackerPlaced sp = placedStacks.getStacker(location);
                if (sp.getValue() > 0) {
                    boolean teamStacking = Settings.getStackByType(sp.getType()).teamStacking();

                    if(sp.getUuid().equals(uuid) || teamStacking && util.playerIsOnTeamOf(uuid, sp.getUuid())) {
                        if(player.hasPermission("bs.takeblocks")) {
                            if(!util.isPickaxe(player.getItemInHand().getType())) {
                                if(!player.isSneaking()) {
                                    if(hasAvaliableSlot(player, sp.getChosenMaterial(), 1)) {
                                        sp.decrementValueby(1);
                                        player.getInventory().addItem(new ItemStack(sp.getChosenMaterial(), 1));
                                        player.sendMessage(mm.applyCC(mm.decrementMessage().replaceAll("%AMOUNT%", "1")));
                                    } else {
                                        player.sendMessage(mm.applyCC(mm.noInventorySpace()));
                                    }
                                }
                                if(player.isSneaking()) {
                                    if(sp.getValue() >= 64) {
                                        if (hasAvaliableSlot(player, sp.getChosenMaterial(), 64)) {
                                            sp.decrementValueby(64);
                                            player.getInventory().addItem(new ItemStack(sp.getChosenMaterial(), 64));
                                            player.sendMessage(mm.applyCC(mm.decrementMessage().replaceAll("%AMOUNT%", "1")));
                                        } else {
                                            player.sendMessage(mm.applyCC(mm.noInventorySpace()));
                                            return;
                                        }
                                    } else {
                                        if(hasAvaliableSlot(player, sp.getChosenMaterial(), sp.getValue())) {
                                            player.getInventory().addItem(new ItemStack(sp.getChosenMaterial(), sp.getValue()));
                                            player.sendMessage(mm.applyCC(mm.decrementMessage().replaceAll("%AMOUNT%", ""+sp.getValue())));
                                            sp.setValue(0);
                                        } else {
                                            player.sendMessage(mm.applyCC(mm.noInventorySpace()));
                                            return;
                                        }
                                    }
                                }
                                if(sp.getValue() == 0) {
                                    sp.setChosenMaterial(null);
                                }
                            }
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(mm.applyCC(mm.noPermission()));
                        }
                    } else {
                        player.sendMessage(mm.applyCC(mm.notYourStacker()));
                    }
                } else {
                    player.sendMessage(mm.applyCC(mm.stackerEmpty()));
                }
            }


        }

    }


    public boolean hasAvaliableSlot(Player player, Material mat, int amt) {
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType().equals(mat) && item.getAmount() <= 64 - amt) {
                return true;
            }
        }
        return false;
    }
}
