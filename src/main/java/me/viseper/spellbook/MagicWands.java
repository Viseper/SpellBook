package me.viseper.spellbook;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class MagicWands implements Listener {

    //key = uuid of player
    //long = time since command was ran
    private final HashMap<UUID, Long> cooldownM;
    private final HashMap<UUID, Long> cooldownF;
    private final HashMap<UUID, Long> cooldownS;
    private final HashMap<UUID, Long> cooldownR;
    public MagicWands() {
        this.cooldownM = new HashMap<>();
        this.cooldownF = new HashMap<>();
        this.cooldownS = new HashMap<>();
        this.cooldownR = new HashMap<>();
    }

    public ShapedRecipe missileRecipe() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta meta = wand.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(SpellBook.getPlugin(), "missile"), PersistentDataType.STRING, "missileW");
        meta.setDisplayName("Magic Missiles");
        wand.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(SpellBook.getPlugin(), "missile_wand");
        ShapedRecipe missileRecipe = new ShapedRecipe(key, wand);
        missileRecipe.shape(" G "," G "," G ");
        missileRecipe.setIngredient('G', Material.GLOW_INK_SAC);
        return missileRecipe;
    }
    public ShapedRecipe fireballRecipe() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta meta = wand.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(SpellBook.getPlugin(), "fireball"), PersistentDataType.STRING, "missileF");
        meta.setDisplayName("Fireball");
        wand.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(SpellBook.getPlugin(), "fireball_wand");
        ShapedRecipe fireballRecipe = new ShapedRecipe(key, wand);
        fireballRecipe.shape("  B"," B ","B  ");
        fireballRecipe.setIngredient('B', Material.BLAZE_POWDER);
        return fireballRecipe;
    }

    public ShapedRecipe shockwaveRecipe() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta meta = wand.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(SpellBook.getPlugin(), "shockwave"), PersistentDataType.STRING, "shockwave");
        meta.setDisplayName("Shockwave");
        wand.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(SpellBook.getPlugin(), "shockwave_wand");
        ShapedRecipe shockwaveRecipe = new ShapedRecipe(key, wand);
        shockwaveRecipe.shape(" A "," A "," A ");
        shockwaveRecipe.setIngredient('A', Material.AMETHYST_SHARD);
        return shockwaveRecipe;
    }

    public ShapedRecipe shieldRecipe() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta meta = wand.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(SpellBook.getPlugin(), "shield"), PersistentDataType.STRING, "shield");
        meta.setDisplayName("Shield");
        wand.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(SpellBook.getPlugin(), "shield_wand");
        ShapedRecipe shieldRecipe = new ShapedRecipe(key, wand);
        shieldRecipe.shape(" A "," A "," A ");
        shieldRecipe.setIngredient('A', Material.END_CRYSTAL);
        return shieldRecipe;
    }

    public ShapedRecipe sprintRecipe() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta meta = wand.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(SpellBook.getPlugin(), "sprint"), PersistentDataType.STRING, "sprint");
        meta.setDisplayName("Sprint");
        wand.setItemMeta(meta);
        NamespacedKey key = new NamespacedKey(SpellBook.getPlugin(), "sprint_wand");
        ShapedRecipe sprintRecipe = new ShapedRecipe(key, wand);
        sprintRecipe.shape(" A "," A "," A ");
        sprintRecipe.setIngredient('A', Material.POTION, 8194);
        return sprintRecipe;
    }
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if(!this.cooldownM.containsKey(player.getUniqueId()) || !this.cooldownF.containsKey(player.getUniqueId()) || !this.cooldownS.containsKey(player.getUniqueId())) {
            this.cooldownM.put(player.getUniqueId(), System.currentTimeMillis());
            this.cooldownF.put(player.getUniqueId(), System.currentTimeMillis());
            this.cooldownS.put(player.getUniqueId(), System.currentTimeMillis());
            this.cooldownR.put(player.getUniqueId(), System.currentTimeMillis());
        }
        long timeElapsedM = System.currentTimeMillis() - cooldownM.get(player.getUniqueId());
        long timeElapsedF = System.currentTimeMillis() - cooldownF.get(player.getUniqueId());
        long timeElapsedS = System.currentTimeMillis() - cooldownS.get(player.getUniqueId());
        long timeElapsedR = System.currentTimeMillis() - cooldownR.get(player.getUniqueId());
        if(event.getItem() != null) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SpellBook.getPlugin(), "missile"), PersistentDataType.STRING)) {
                if (timeElapsedM >= 500) {
                    player.performCommand("magicmissiles");
                    player.sendMessage("Magic Missiles!");
                    this.cooldownM.put(player.getUniqueId(), System.currentTimeMillis());
                } else {
                    player.sendMessage("Magic Missile is currently on cooldown.");
                }
            } else if (event.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SpellBook.getPlugin(), "fireball"), PersistentDataType.STRING) && !player.getScoreboardTags().contains("Sprint")) {
                if (timeElapsedF >= 5000) {
                    int explosivePower = 3;
                    Fireball fireball = (Fireball) world.spawnEntity(new Location(world, player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ()), EntityType.FIREBALL);
                    fireball.setYield(explosivePower);
                    fireball.setVelocity(player.getLocation().getDirection());
                    fireball.setDirection(player.getLocation().getDirection());
                    player.sendMessage("Fireball!");
                    this.cooldownF.put(player.getUniqueId(), System.currentTimeMillis());
                } else {
                    player.sendMessage("Fireball is currently on cooldown.");
                }
            } else if (event.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SpellBook.getPlugin(), "shockwave"), PersistentDataType.STRING)) {
                player.performCommand("shockwave");
                player.sendMessage("Shockwave!");
                //this.cooldownM.put(player.getUniqueId(), System.currentTimeMillis());
            } else if (event.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SpellBook.getPlugin(), "shield"), PersistentDataType.STRING)) {
                if(timeElapsedS >= 10000) {
                    player.performCommand("shield");
                    player.sendMessage("Shield Activated!");
                    this.cooldownS.put(player.getUniqueId(), System.currentTimeMillis());
                } else {
                    player.sendMessage("Shield On cooldown, please wait.");
                }
            } else if (event.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SpellBook.getPlugin(), "sprint"), PersistentDataType.STRING)) {
                if(timeElapsedR >= 20000) {
                    player.performCommand("sprint");
                    player.sendMessage("Run!");
                    this.cooldownR.put(player.getUniqueId(), System.currentTimeMillis());
                } else {
                    player.sendMessage("Sprint On cooldown, please wait.");
                }
            }
        }
        }
    }






}
