package ru.ibusewinner.fundaily.blockstacker.addon.Objects;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.*;
import world.bentobox.bentobox.BentoBox;

import java.util.UUID;

public class StackerPlaced {
    private BSAddon plugin = BSAddon.get();
    private Worth worth = plugin.worth;
    private MessageManager mm = plugin.mm;

    private String type;
    private UUID uuid;
    private Material chosenMaterial;
    private SLocation location;
    private Hologram display;
    private int value;
    private int id;


    public StackerPlaced(int id, String type, UUID uuid, Material chosenMaterial, SLocation location, Hologram display, int value)
    {
        this.type = type;
        this.uuid = uuid;
        this.chosenMaterial = chosenMaterial;
        this.location = location;
        this.display = display;
        this.value = value;
        this.id = id;

        plugin.placedStacks.addStack(location.toLocation(), this);
    }

    public StackerPlaced(String dataString)
    {
        String[] data0 = dataString.split(";");

        int id;
        String type = data0[1];
        UUID uuid = UUID.fromString(data0[2]);
        Material cmat;
        SLocation location = formatSlocation(data0[4].split(","));
        SLocation displayLoc = formatSlocation(data0[5].split(","));
        int value;

        if (data0[3].equalsIgnoreCase("unspecified"))
        {
            cmat = null;
        }else cmat = Material.valueOf(data0[3].toUpperCase());

        try{

            id = Integer.parseInt(data0[0]);
            value = Integer.parseInt(data0[6]);

        }catch (Exception e)
        {
            Bukkit.broadcastMessage(ChatColor.RED+mm.stackerFormatError());
            e.printStackTrace();
            return;
        }
        Hologram display = DisplayManager.createDisplay(BentoBox.getInstance(), type, value, displayLoc.toLocation());


        this.type = type;
        this.uuid = uuid;
        this.chosenMaterial = cmat;
        this.location = location;
        this.display = display;
        this.value = value;
        this.id = id;

        plugin.placedStacks.addStack(location.toLocation(), this);
    }



    public SLocation formatSlocation(String[] data)
    {
        String world = data[0];
        double x = 0, y = 0, z = 0;

        try{
            x = Double.parseDouble(data[1]);
            y = Double.parseDouble(data[2]);
            z = Double.parseDouble(data[3]);


        }catch (Exception e)
        {
            Bukkit.broadcastMessage(ChatColor.RED+mm.stackerFormatError());
            e.printStackTrace();

        }
        return new SLocation(world, x, y, z);

    }

    public double computeAndGetLevels()
    {
        double blockValue = Worth.getWorth(this.chosenMaterial);

        return (blockValue * this.value);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public String toString()
    {
        SLocation displayLoc = new SLocation(display.getLocation());
        String cmat;
        if (chosenMaterial != null) cmat = chosenMaterial.toString();
        else cmat = "unspecified";

        Location holoLocation = location.toLocation().add(plugin.utils.getOffset(type));

        return this.id+";"+type+";"+uuid.toString()+";"+cmat+";"+location.world+","+location.x+","+location.y+","+location.z+";"+holoLocation.getWorld().getName()+","+holoLocation.getX()+","+holoLocation.getY()+","+holoLocation.getZ()+";"+value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Material getChosenMaterial() {
        return chosenMaterial;
    }

    public void setChosenMaterial(Material chosenMaterial) {
        if (chosenMaterial == null)
        {
            this.chosenMaterial = null;
            for(IStack stack : Settings.getAllStackers()) {
                if(stack.getCfgName().equalsIgnoreCase(this.type)) {
                    this.location.toLocation().getWorld().getBlockAt(this.location.toLocation())
                            .setType(Material.valueOf(stack.getItemType().toUpperCase()));
                }
            }
            return;
        }
        this.chosenMaterial = chosenMaterial;
        this.location.toLocation().getWorld().getBlockAt(this.location.toLocation()).setType(chosenMaterial);
    }

    public SLocation getLocation() {
        return location;
    }

    public void setLocation(SLocation location) {
        this.location = location;
    }

    public Hologram getDisplay() {
        return display;
    }

    public void setDisplay(Hologram display) {
        this.display = display;
    }

    public int getValue() {
        return value;
    }

    public void decrementValueby(int value)
    {
        this.value -= value;
        DisplayManager.updateHologram(this.display, this.type, this.value);
    }

    public void incrementValueBy(int value)
    {
        this.value += value;
        DisplayManager.updateHologram(this.display, this.type, this.value);
    }
    public void setValue(int value) {
        this.value = value;
        DisplayManager.updateHologram(this.display, this.type, this.value);

    }
}
