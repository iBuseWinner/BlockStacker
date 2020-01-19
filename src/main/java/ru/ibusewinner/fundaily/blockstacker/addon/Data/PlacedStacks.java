package ru.ibusewinner.fundaily.blockstacker.addon.Data;

import org.bukkit.Location;
import ru.ibusewinner.fundaily.blockstacker.addon.Objects.StackerPlaced;

import java.util.HashMap;

public class PlacedStacks {
    private HashMap<Location, StackerPlaced> placedStacks = new HashMap<>();

    public void addStack(Location loc, StackerPlaced SP) {
        placedStacks.put(loc, SP);
    }
    public void deleteStack(Location loc)
    {
        placedStacks.remove(loc);
    }
    public boolean contains(Location location)
    {
        return placedStacks.containsKey(location);
    }
    public StackerPlaced getStacker(Location location)
    {
        return placedStacks.get(location);
    }
    public HashMap<Location, StackerPlaced> getPlacedStacksMap()
    {
        return placedStacks;
    }

    public StackerPlaced getFirst()
    {
        return (StackerPlaced) placedStacks.values().toArray()[0];
    }

    public int getNewID()
    {
        return placedStacks.size()+1;
    }
}
