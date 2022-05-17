package me.viseper.spellbook;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SprintBoost implements CommandExecutor {
    //Gives a player a short boost of speed
    //Should also give them a small amount of resistance and saturation to ensure that the speed boost doesn't kill them.
    //For balance's sake, we also need to disable the casters' ability to cast spells or attack while sprinting

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        double speed;
        if(player == null) {
            System.out.print("This command can only be used by a player");
            return true;
        }
        if (player.getScoreboardTags().contains("Sprint")) {
            player.sendMessage("Can't cast this spell while Sprint is active.");
            player.removeScoreboardTag("Sprint");
            return true;
        }
        if(args.length > 0) {
            speed = Double.parseDouble(args[0]);
        }else {
            speed = 0.5;
        }

        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(new AttributeModifier(UUID.fromString("bf170573-3b96-36df-9eb1-671b993bb1bb"),"Boost", speed, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier(UUID.fromString("bf170573-3b96-36df-9eb1-671b993bb1bb"),"Boost", speed, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        double defSpeed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10, 1));
        player.addScoreboardTag("Sprint");

        new BukkitRunnable() {
            @Override
            public void run() {
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(new AttributeModifier(UUID.fromString("bf170573-3b96-36df-9eb1-671b993bb1bb"), "Boost", speed, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
                player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                player.removeScoreboardTag("Sprint");
            }
        }.runTaskLater(player.getServer().getPluginManager().getPlugin("SpellBook"), 200L);
        return true;
    }


}
