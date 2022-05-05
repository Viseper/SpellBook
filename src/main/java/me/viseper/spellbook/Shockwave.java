package me.viseper.spellbook;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Shockwave implements CommandExecutor {
    //Give players upwards velocity followed by a high downwards velocity.
    //Locate all nearby mobs
    //Give them all slowness for a small amount of time
    //Deal the mobs a small amount of damage.
    //Then set all nearby entities velocity to travel away from us.


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
