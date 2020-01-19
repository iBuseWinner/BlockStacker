package ru.ibusewinner.fundaily.blockstacker.addon.Utils;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;
import world.bentobox.bentobox.BentoBox;

import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
    private static List<Hologram> displays = new ArrayList<>();

    public static Hologram createDisplay(BentoBox plugin, String type, int value, Location location) {
        String valueFormat = Settings.getStackByType(type).getValueFormat();
        String name = BSAddon.get().mm.applyCC(valueFormat.replace("%VALUE%", "" + value));

        Hologram hologram = HologramsAPI.createHologram(BentoBox.getInstance(), location);
        hologram.appendTextLine(name);

        displays.add(hologram);
        return hologram;
    }

    public static void deleteAllDisplays() {
        displays.forEach(Hologram::delete);
    }
    public static void deleteDisplay(Hologram hologram) {
        hologram.delete();
    }

    public static void updateHologram(Hologram hologram, String type, int value) {
        for(IStack stack : Settings.getAllStackers()) {
            if(stack.getCfgName().equalsIgnoreCase(type)) {
                String newName = stack.getValueFormat().replaceAll("%VALUE%", value+"");
                hologram.insertTextLine(0, newName);
                hologram.getLine(1).removeLine();
            }
        }
    }

    public static void reloadAllDisplays(BSAddon plugin) {
        for (StackerPlaced sp : plugin.placedStacks.getPlacedStacksMap().values()) {
            Hologram replacement = createDisplay(BentoBox.getInstance(), sp.getType(), sp.getValue(), sp.getDisplay().getLocation());
            sp.getDisplay().delete();
            sp.setDisplay(replacement);
        }
    }
}
