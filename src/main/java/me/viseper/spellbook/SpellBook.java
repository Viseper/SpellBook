package me.viseper.spellbook;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpellBook extends JavaPlugin implements Listener {



    private static SpellBook plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Simple statement of project purpose.
        System.out.println("Welcome to the book of spells plug-in made by Viseper.");
        //basic declaration of the book
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        //set up of spells
        this.getCommand("magicMissiles").setExecutor(new MagicMissiles());
        this.getCommand("shockwave").setExecutor(new Shockwave());
        this.getCommand("shield").setExecutor(new Shield());
        this.getCommand("sprint").setExecutor(new SprintBoost());
        getServer().getPluginManager().registerEvents(this, this);
        //set up of the book
        //Example for future reference.
                //BaseComponent[] page1 = new ComponentBuilder().append("Your Text Here").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kill @e[type=!player]")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("This text shall kill everything.").create())).create();
        BaseComponent[] page1 = new ComponentBuilder("Welcome to the ancient book of spells.").color(ChatColor.DARK_PURPLE).append("\n \n \n \n").append("Written by ").append("Viseper").obfuscated(true).create();
        BaseComponent[] page2 = new ComponentBuilder("SpellBook Contents").color(ChatColor.GOLD).append("\nFireball").color(ChatColor.RESET).event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "3")).append("\nMagic Missiles").event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "4")).append("\nShockwave").event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "5")).append("\nShield").event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "6")).append("\nSprint").event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "7")).create();
        BaseComponent[] page3 = new ComponentBuilder("Fireball").color(ChatColor.DARK_RED).append("\n\n A decently powerful spell that summons a ball of fire.").color(ChatColor.RESET).append("\n\nSummon").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/summon minecraft:fireball ~ ~1 ~ {ExplosionPower:10}")).create();
        //ignore
        //BaseComponent[] page4 = new ComponentBuilder("Level 3 Fireball").color(ChatColor.DARK_RED).append("\n\n A very  more complex spell that does a very complex thing. Summons a massive fireball that could do some serious damage.").color(ChatColor.RESET).append("\n\nSummon").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/summon minecraft:fireball ~ ~1 ~ {ExplosionPower:10}")).create();
        //Do not enable this spell unless absolutely necessary
        //BaseComponent[] page5 = new ComponentBuilder("Doomsday").color(ChatColor.DARK_RED).append("\n\nThis will kill you. There is no escaping this fireball. This spell will end everything. You, your allies, and your enemies. Do not use!").color(ChatColor.RESET).append("\n\nEnd everything").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/summon minecraft:fireball ~ ~1 ~ {ExplosionPower:127}")).create();
        BaseComponent[] page4 = new ComponentBuilder("Magic Missiles").color(ChatColor.AQUA).append("\n\nSummons an array of magic missiles.").color(ChatColor.RESET).append("\n\nActivate Spell").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/magicMissiles")).create();
        BaseComponent[] page5 = new ComponentBuilder("Shockwave").color(ChatColor.AQUA).append("\n\nCreates a shockwave knocking all enemies away from you.").color(ChatColor.RESET).append("\n\nActivate Spell").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shockwave")).create();
        BaseComponent[] page6 = new ComponentBuilder("Shield").color(ChatColor.AQUA).append("\n\nCreates a cube of obsidian around you for 5 seconds").color(ChatColor.RESET).append("\n\nDeploy Shield!").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shield")).create();
        BaseComponent[] page7 = new ComponentBuilder("Sprint").color(ChatColor.AQUA).append("\n\nGives a boost of speed that can save your life in a pinch.").color(ChatColor.RESET).append("\n\nRun!").color(ChatColor.DARK_AQUA).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sprint 1")).create();

        bookMeta.spigot().addPage(page1);
        bookMeta.spigot().addPage(page2);
        bookMeta.spigot().addPage(page3);
        bookMeta.spigot().addPage(page4);
        bookMeta.spigot().addPage(page5);
        bookMeta.spigot().addPage(page6);
        bookMeta.spigot().addPage(page7);
        bookMeta.setAuthor("Unknown");
        bookMeta.setTitle("The Book of Spells");
        book.setItemMeta(bookMeta);
        //creating of the magic book recipe

        NamespacedKey bookKey = new NamespacedKey(this, "book_spell");
        ShapedRecipe bookRecipe = new ShapedRecipe(bookKey, book);
        bookRecipe.shape("BBB","BLB","BBB");
        bookRecipe.setIngredient('B', Material.BLAZE_POWDER);
        bookRecipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(bookRecipe);
        //set up of wands
        getServer().getPluginManager().registerEvents(new MagicWands(), this);
        MagicWands mw = new MagicWands();
        getServer().addRecipe(mw.missileRecipe());
        getServer().addRecipe(mw.fireballRecipe());
        getServer().addRecipe(mw.shockwaveRecipe());
        getServer().addRecipe(mw.shieldRecipe());
        getServer().addRecipe(mw.sprintRecipe());


    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent damage) {
        Player player = (Player) damage.getDamager();
        if (player != null && player.getScoreboardTags().contains("Sprint")) {
            damage.setCancelled(true);
        }
    }

    public static SpellBook getPlugin() {
        return plugin;
    }


}
