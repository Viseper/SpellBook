package me.viseper.spellbook;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerHideEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MagicMissiles implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(player == null) {
            System.out.println("Only a player can use this command");
            return true;
        }
        if (player.getScoreboardTags().contains("Sprint")) {
            player.sendMessage("Can't cast this spell while Sprint is active.");
            return true;
        }
        int missileNumber = (int) Math.floor(Math.random() * 10) + 1;
        World world = player.getWorld();
        List<Arrow> arrows = new ArrayList<Arrow>();
        for (int i = 0; i < missileNumber; i++) {
            Arrow arrow = (Arrow) world.spawnEntity(new Location(world, player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ()), EntityType.ARROW);
            arrow.setVelocity(player.getLocation().getDirection().multiply(new Vector(Math.random() * 2 + 1, Math.random() * 2 + 1, Math.random() * 2 + 1)));
            List<Entity> nearest = arrow.getNearbyEntities(8,5,8);
            for (Entity target: nearest) {
                if(player.hasLineOfSight(target) && target instanceof LivingEntity && !target.isDead() && target != player) {
                    arrow.setVelocity(target.getLocation().toVector().subtract(arrow.getLocation().toVector().add(new Vector(0,-1,0))).normalize());
                }
            }
            arrow.addScoreboardTag("missile");
            arrows.add(arrow);
            player.hideEntity(player.getServer().getPluginManager().getPlugin("SpellBook"), arrow);
            arrow.setKnockbackStrength(10);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getWorld().spawnParticle(Particle.CRIT_MAGIC, arrow.getLocation(), 10);
                    if(arrow.isInBlock() || arrow.isDead()) {
                        cancel();
                    } else {
                        List<Entity> nearest = arrow.getNearbyEntities(8,5,8);
                        for (Entity target: nearest) {
                            if(player.hasLineOfSight(target) && target instanceof LivingEntity && !target.isDead() && target != player) {
                                arrow.setVelocity(target.getLocation().toVector().subtract(arrow.getLocation().toVector().add(new Vector(0,-1,0))).normalize());
                            }
                        }
                    }
                }
            }.runTaskTimer(player.getServer().getPluginManager().getPlugin("SpellBook"), 0L, 1L);
        }
        return true;
        }


}
