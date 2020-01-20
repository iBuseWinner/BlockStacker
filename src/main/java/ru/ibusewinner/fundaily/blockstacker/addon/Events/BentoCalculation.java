package ru.ibusewinner.fundaily.blockstacker.addon.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Data.PlacedStacks;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.BSLogger;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.MessageManager;
import world.bentobox.level.event.IslandPreLevelEvent;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class BentoCalculation implements Listener {
    private BSAddon plugin = BSAddon.get();
    private PlacedStacks placedStacks = plugin.placedStacks;
    private MessageManager mm = plugin.mm;

    @EventHandler
    public void levelRequestHandler(IslandPreLevelEvent event) {
        long TotalLevelsFromStacks = 0;
        long TotalLevel = event.getIsland().getLevelHandicap();

        UUID player = event.getPlayerUUID();
        Map<UUID, Integer> teamMembers = event.getIsland().getMembers();

        for(StackerPlaced SP : placedStacks.getPlacedStacksMap().values()) {
            if(SP.getChosenMaterial() != null) {
                if(SP.getUuid().equals(player) || teamMembers.containsKey(SP.getUuid())) {
                    long levelsToAdd = SP.computeAndGetLevels();
                    TotalLevel += levelsToAdd;
                    TotalLevelsFromStacks += levelsToAdd;
                }
            }
        }

        TotalLevel = Math.abs(TotalLevel) * (-1);
        BSLogger.msg("total: "+TotalLevel);
        event.getIsland().setLevelHandicap((int)TotalLevel);
        Objects.requireNonNull(Bukkit.getPlayer(player)).sendMessage(mm.applyCC(mm.calculationEvent().replace("%LEVELS%", ""+TotalLevelsFromStacks)));
    }
}
