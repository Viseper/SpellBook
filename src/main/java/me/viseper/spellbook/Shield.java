package me.viseper.spellbook;

import jdk.javadoc.internal.doclint.HtmlTag;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sun.jvm.hotspot.oops.Metadata;

import javax.sound.midi.MetaEventListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Shield implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location pLoc = player.getLocation();
        if(player == null) {
            System.out.print("Only a player can cast this spell.");
            return true;
        }
        World world = player.getWorld();
        Material[] materials = new Material[6 * (5 * 2)];
        Location l = player.getLocation();
        // example: player.getLocation().getBlock().setType(Material.OBSIDIAN);
        ArrayList<Block> blocks = getData(pLoc, 5);
        ArrayList<Material> blockList = getBlocks(pLoc, 5, Material.OBSIDIAN);
        ArrayList<Material> insideBlock = getBlocks(pLoc, 4, Material.AIR);
        //makeCircle(player.getLocation(), 5d, Material.OBSIDIAN);

        new BukkitRunnable() {
            @Override
            public void run() {
                int arrayLocation = 0;
                int arrayLocation2 = 0;
                for(double x = pLoc.getX() - 5; x <= pLoc.getX() + 5; x++){
                    for(double y = pLoc.getY() - 5; y <= pLoc.getY() + 5; y++){
                        for(double z = pLoc.getZ() - 5; z <= pLoc.getZ() + 5; z++){
                            Location loc = new Location(pLoc.getWorld(), x, y, z);
                            if(loc.getBlock() instanceof Chest || loc.getBlock() instanceof CreatureSpawner || loc.getBlock() instanceof ShulkerBox || loc.getBlock() instanceof DoubleChest || loc.getBlock() instanceof Furnace || loc.getBlock() instanceof Hopper || loc.getBlock() instanceof Dropper || loc.getBlock() instanceof Dispenser) {
                                continue;
                            }
                            loc.getBlock().setType(blockList.get(arrayLocation++));
                            loc.getBlock().setBlockData(blocks.get(arrayLocation2++).getBlockData());
                        }
                    }
                }
            }

        }.runTaskLater(player.getServer().getPluginManager().getPlugin("SpellBook"), 100L);
        return true;
    }
    /* Just gonna disable this until we can come up with a better method for a sphere.
    public void makeCircle(Location loc, Double r, Material m) {
        for (double i = 0; i <= Math.PI; i += Math.PI / r) {
            double radius = Math.sin(i) * r;
            double y = Math.cos(i) * r;
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / r) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                loc.add(x, y, z);
                loc.getBlock().setType(m);
                loc.subtract(x, y, z);
            }
        }
    }

    public double[][] storeCircle(Location location, int r) {
        double[][] coordinates = new double[(r + 1) * (r * 2)][];
        int arrayLocation = 0;
        for (double i = 0; i <= Math.PI; i += Math.PI / r) {
            double radius = Math.sin(i) * r;
            double y = Math.cos(i) * r;
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / r) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                location.add(x, y, z);
                coordinates[arrayLocation++] = new double[] { x, y, z };
                location.subtract(x, y, z);
            }
        }
        return coordinates;
    }

    public void restoreCircle(Location loc, Double r, Material[] m) {
        int arrayLocation = 0;
        for (double i = 0; i <= Math.PI; i += Math.PI / r) {
            double radius = Math.sin(i) * r;
            double y = Math.cos(i) * r;
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / r) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                loc.add(x, y, z);
                loc.getBlock().setType(m[arrayLocation++]);
                loc.subtract(x, y, z);
            }
        }
    } */

    public ArrayList<Material> getBlocks(Location start, int radius, Material m){
        ArrayList<Material> blocks = new ArrayList<Material>();
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    if(loc.getBlock() instanceof Chest || loc.getBlock() instanceof CreatureSpawner || loc.getBlock() instanceof ShulkerBox || loc.getBlock() instanceof DoubleChest || loc.getBlock() instanceof Furnace || loc.getBlock() instanceof Hopper || loc.getBlock() instanceof Dropper || loc.getBlock() instanceof Dispenser) {
                        continue;
                    }
                    blocks.add(loc.getBlock().getType());
                    loc.getBlock().setType(m);
                }
            }
        }
        start.add(0,-1,0).getBlock().setType(Material.OBSIDIAN);
        start.add(0,1,0);
        return blocks;
    }

    public ArrayList<Block> getData(Location start, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (double x = start.getX() - radius; x <= start.getX() + radius; x++) {
            for (double y = start.getY() - radius; y <= start.getY() + radius; y++) {
                for (double z = start.getZ() - radius; z <= start.getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());

                }
            }
        }
        return blocks;
    }

}
