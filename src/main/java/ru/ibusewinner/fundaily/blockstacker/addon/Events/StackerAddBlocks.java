package ru.ibusewinner.fundaily.blockstacker.addon.Events;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Data.PlacedStacks;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.MessageManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Settings;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Utils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StackerAddBlocks implements Listener {
    private BSAddon plugin = BSAddon.get();
    private Utils util = plugin.utils;
    private MessageManager mm = plugin.mm;

    private PlacedStacks placedStacks = plugin.placedStacks;

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
            if (placedStacks.contains(location)) {
                Player player = event.getPlayer();
                UUID uuid = player.getUniqueId();
                if (player.getItemInHand() != null && !player.getItemInHand().getType().equals(Material.AIR)) {
                    event.setCancelled(true);
                    StackerPlaced sp = placedStacks.getStacker(location);
                    Material mat = player.getItemInHand().getType();
                    NBTItem nbtItem = new NBTItem(player.getItemInHand());
                    List<Material> materialWhiteList = util.getBlockList(sp.getType());
                    if (player.hasPermission("bs.AddBlocks")) {
                        if (materialWhiteList.contains(mat)) {
                            if (nbtItem.hasNBTData() && nbtItem.hasKey("BlockStackerX") || nbtItem.hasKey("BlockStackerX_nonSolid")) {
                                player.sendMessage(mm.applyCC(mm.cantStackStackers()));
                                return;
                            }
                            boolean teamStacking = Settings.getStackByType(sp.getType()).teamStacking();
                            if (sp.getUuid().equals(uuid) || teamStacking && util.playerIsOnTeamOf(uuid, sp.getUuid())) {
                                if (sp.getValue() == 0) {
                                    sp.setChosenMaterial(mat);
                                    player.sendMessage(mm.applyCC(mm.chosenMaterial().replaceAll("%BLOCKTYPE%", mat.toString().toLowerCase())));
                                }
                                if (sp.getChosenMaterial().equals(mat)) {
                                    if (!player.isSneaking()) {
                                        if (sp.getValue() + 1 <= Settings.getStackByType(sp.getType()).getMax()) {
                                            int decreaseAmt = decreaseItemInHandBy(player, 1);
                                            sp.incrementValueBy(decreaseAmt);
                                            player.sendMessage(mm.applyCC(mm.incrementMessage().replaceAll("%AMOUNT%", "" + decreaseAmt)));
                                        } else {
                                            player.sendMessage(mm.applyCC(mm.maxStack().replaceAll("%MAX%", "" + Settings.getStackByType(sp.getType()).getMax())));
                                            return;
                                        }
                                    }
                                    if (player.isSneaking()) {
                                        if (sp.getValue() + player.getItemInHand().getAmount() <= Settings.getStackByType(sp.getType()).getMax()) {
                                            int decreaseAmt = decreaseItemInHandBy(player, 64);
                                            sp.incrementValueBy(decreaseAmt);
                                            player.sendMessage(mm.applyCC(mm.incrementMessage().replaceAll("%AMOUNT%", "" + decreaseAmt)));
                                        } else {
                                            player.sendMessage(mm.applyCC(mm.maxStack().replaceAll("%MAX%", ""+Settings.getStackByType(sp.getType()).getMax())));
                                        }
                                    }
                                } else {
                                    player.sendMessage(mm.applyCC(mm.stackerOccupied().replaceAll("%BLOCK%", sp.getChosenMaterial().toString().toLowerCase())));
                                }
                            } else {
                                player.sendMessage(mm.applyCC(mm.notYourStacker()));
                            }
                        } else {
                            player.sendMessage(mm.applyCC(mm.incorrectBlockInHand()));
                        }
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(mm.applyCC(mm.noPermission()));
                    }
                } else {
                    player.sendMessage(mm.applyCC(mm.noBlockInHand()));
                }
            }
        }
    }

    private int decreaseItemInHandBy(Player player, int amt) {
        int handAmt = player.getItemInHand().getAmount();
        if (amt >= handAmt) {
            player.setItemInHand(new ItemStack(Material.AIR));
            return handAmt;
        }
        ItemStack newAmt = new ItemStack(player.getItemInHand().getType(), handAmt - amt);
        player.setItemInHand(newAmt);
        return amt;
    }
}
