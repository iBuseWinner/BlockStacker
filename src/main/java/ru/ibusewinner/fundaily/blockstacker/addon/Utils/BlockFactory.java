package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;

import java.util.List;

public class BlockFactory {
    private BSAddon bsAddon = BSAddon.get();

    public ItemStack getStacker(String type, int amt) {
        for(IStack stack : Settings.getAllStackers()) {
            if(stack.getCfgName().equalsIgnoreCase(type)) {
                String itemType = stack.getItemType();
                String displayName = stack.getDisplayName();
                List<String> lore =stack.getLore();

                ItemStack item = new ItemStack(Material.valueOf(itemType.toUpperCase()), amt);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(bsAddon.mm.applyCC(displayName));
                meta.setLore(bsAddon.mm.applyCC(lore));

                if(stack.glow()) {
                    meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                }
                item.setItemMeta(meta);

                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setString("BlockStackerX", type);
                return nbtItem.getItem();
            }
        }
        return null;
    }
}
