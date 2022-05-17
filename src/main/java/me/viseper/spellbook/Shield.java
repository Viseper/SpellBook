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
import org.bukkit.inventory.Inventory;
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
        if (player.getScoreboardTags().contains("Sprint")) {
            player.sendMessage("Can't cast this spell while Sprint is active.");
            return true;
        }
        World world = player.getWorld();
        Material[] materials = new Material[6 * (5 * 2)];
        Location l = player.getLocation();
        // example: player.getLocation().getBlock().setType(Material.OBSIDIAN);
        ArrayList<Inventory> inv = getContainer(pLoc, 5);
        ArrayList<Block> blocks = getData(pLoc, 5);
        ArrayList<Material> blockList = getBlocks(pLoc, 5, Material.OBSIDIAN);
        ArrayList<Material> insideBlock = getBlocks(pLoc, 4, Material.AIR);
        //makeCircle(player.getLocation(), 5d, Material.OBSIDIAN);

        new BukkitRunnable() {
            @Override
            public void run() {
                int arrayLocation = 0;
                int arrayLocation2 = 0;
                int arrayLocation3 = 0;
                for(double x = pLoc.getX() - 5; x <= pLoc.getX() + 5; x++){
                    for(double y = pLoc.getY() - 5; y <= pLoc.getY() + 5; y++){
                        for(double z = pLoc.getZ() - 5; z <= pLoc.getZ() + 5; z++){
                            Location loc = new Location(pLoc.getWorld(), x, y, z);
                            loc.getBlock().setType(blockList.get(arrayLocation++));
                            //loc.getBlock().setBlockData(blocks.get(arrayLocation2++).getBlockData());
                            if(loc.getBlock().getState() instanceof Container) {
                                ((Container) loc.getBlock().getState()).getInventory().setContents(inv.get(arrayLocation3++).getContents());
                            }
                        }
                    }
                }
            }

        }.runTaskLater(player.getServer().getPluginManager().getPlugin("SpellBook"), 100L);
        return true;
    }

    public ArrayList<Material> getBlocks(Location start, int radius, Material m){
        ArrayList<Material> blocks = new ArrayList<Material>();
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock().getType());
                    loc.getBlock().setType(m);
                }
            }
        }
        start.add(0,-1,0).getBlock().setType(Material.OBSIDIAN);
        start.add(-1,0,0).getBlock().setType(Material.OBSIDIAN);
        start.add(2,0,0).getBlock().setType(Material.OBSIDIAN);
        start.add(-1,0,-1).getBlock().setType(Material.OBSIDIAN);
        start.add(0,0,2).getBlock().setType(Material.OBSIDIAN);
        start.add(0,1,-1);
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

    public ArrayList<Inventory> getContainer(Location start, int radius) {
        ArrayList<Inventory> blocks = new ArrayList<>();
        for (double x = start.getX() - radius; x <= start.getX() + radius; x++) {
            for (double y = start.getY() - radius; y <= start.getY() + radius; y++) {
                for (double z = start.getZ() - radius; z <= start.getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    if(loc.getBlock().getState() instanceof Container) {
                        blocks.add(((Container) loc.getBlock().getState()).getInventory());
                    }
                }
            }
        }
        return blocks;
    }

}
