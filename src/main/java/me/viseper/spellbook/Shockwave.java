package me.viseper.spellbook;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class Shockwave implements CommandExecutor {
    //Give players upwards velocity followed by a high downwards velocity.
    //Locate all nearby mobs
    //Give them all slowness for a small amount of time
    //Deal the mobs a small amount of damage.
    //Then set all nearby entities velocity to travel away from us.


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(player == null) {
            System.out.print("Only a player can use this ability.");
            return true;
        }
        World world = player.getWorld();
        List<Entity> nearest = player.getNearbyEntities(10,20,10);
        player.setVelocity(player.getVelocity().setY(1));

        new BukkitRunnable() {
            @Override
            public void run() {
                    player.setVelocity(player.getVelocity().setY(-2));
                    player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation().add(0,-2,0), 10);
                    for (Entity target : nearest) {
                        if(target instanceof LivingEntity){
                            target.setVelocity(target.getLocation().toVector().subtract(player.getLocation().toVector().add(new Vector(0,-1,0))).normalize().multiply(10));
                            ((LivingEntity) target).damage(((LivingEntity) target).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/5);
                        }

                    }
                    this.cancel();
                    //player.setVelocity(player.getVelocity().setY(2));
            }
        }.runTaskTimer(player.getServer().getPluginManager().getPlugin("SpellBook"), 5L, 5L);
        return true;
    }



}
