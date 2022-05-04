package me.viseper.spellbook;

import org.bukkit.Material;
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

public class MagicWands implements Listener {

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

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR && event.getItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(SpellBook.getPlugin(), "missile"), PersistentDataType.STRING) == "missileW") {
            player.performCommand("magicmissiles");
        }
    }






}
