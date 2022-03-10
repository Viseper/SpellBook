package me.viseper.spellbook;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpellBook extends JavaPlugin {

    @Override
    public void onEnable() {
        // Simple statement of project purpose.
        System.out.println("Welcome to the book of spells plug-in made by Viseper.");
        //basic declaration of the book
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        //set up of spells
        this.getCommand("magicMissiles").setExecutor(new MagicMissiles());
        //set up of the book
        //Example for future reference.
                //BaseComponent[] page1 = new ComponentBuilder().append("Your Text Here").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kill @e[type=!player]")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("This text shall kill everything.").create())).create();
        BaseComponent[] page1 = new ComponentBuilder("Welcome to the ancient book of spells.").color(ChatColor.DARK_PURPLE).append("\n \n \n \n").append("Written by ").append("Viseper").obfuscated(true).create();
        BaseComponent[] page2 = new ComponentBuilder("Fireball").color(ChatColor.DARK_RED).append("\n\n A simple spell that does a simple thing. Summons a small fireball that could do some minor damage.").color(ChatColor.RESET).append("\n\nSummon").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/summon minecraft:fireball ~ ~1 ~ {ExplosionPower:1}")).create();
        BaseComponent[] page3 = new ComponentBuilder("Level 2 Fireball").color(ChatColor.DARK_RED).append("\n\n A slightly more complex spell that does a slightly more complex thing. Summons a medium sized fireball that could do some medium levels of damage.").color(ChatColor.RESET).append("\n\nSummon").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/summon minecraft:fireball ~ ~1 ~ {ExplosionPower:5}")).create();
        BaseComponent[] page4 = new ComponentBuilder("Level 3 Fireball").color(ChatColor.DARK_RED).append("\n\n A very  more complex spell that does a very complex thing. Summons a massive fireball that could do some serious damage.").color(ChatColor.RESET).append("\n\nSummon").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/summon minecraft:fireball ~ ~1 ~ {ExplosionPower:10}")).create();
        BaseComponent[] page5 = new ComponentBuilder("Doomsday").color(ChatColor.DARK_RED).append("\n\nThis will kill you. There is no escaping this fireball. This spell will end everything. You, your allies, and your enemies. Do not use!").color(ChatColor.RESET).append("\n\nEnd everything").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/summon minecraft:fireball ~ ~1 ~ {ExplosionPower:127}")).create();
        BaseComponent[] page6 = new ComponentBuilder("Magic Missiles").color(ChatColor.AQUA).append("\n\nSummons an array of magic missiles.").color(ChatColor.RESET).append("\n\nActivate Spell").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/magicMissiles")).create();

        bookMeta.spigot().addPage(page1);
        bookMeta.spigot().addPage(page2);
        bookMeta.spigot().addPage(page3);
        bookMeta.spigot().addPage(page4);
        bookMeta.spigot().addPage(page5);
        bookMeta.spigot().addPage(page6);
        bookMeta.setAuthor("Viseper");
        bookMeta.setTitle("The Book of Spells");
        book.setItemMeta(bookMeta);
        //creating of the magic book recipe

        NamespacedKey bookKey = new NamespacedKey(this, "book_spell");
        ShapedRecipe bookRecipe = new ShapedRecipe(bookKey, book);
        bookRecipe.shape("BBB","BLB","BBB");
        bookRecipe.setIngredient('B', Material.BLAZE_POWDER);
        bookRecipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(bookRecipe);



    }




}
