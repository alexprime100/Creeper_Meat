package fr.alexprime100.creeper_meat.listeners;

import fr.alexprime100.creeper_meat.Main;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MeatListener implements Listener {

    private Main mainClass;

    public MeatListener(Main main) {
        this.mainClass = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //unitTest1(event);
        //event.getPlayer().sendMessage("meat");
    }

    public void unitTest1(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack WitcherSword = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta custom = WitcherSword.getItemMeta();
        custom.setDisplayName("Witcher Sword");
        custom.setLore(Arrays.asList("able to behead mobs!"));
        WitcherSword.setItemMeta(custom);
        player.getInventory().addItem(WitcherSword);
    }


    @EventHandler 
    public void getMobHead(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        //Action action = event.getAction();
        ItemStack item = player.getItemInHand();
        Entity clickedEntity = event.getRightClicked();
        boolean dead = clickedEntity.isDead();
        if (item == null){
            return;
        }
        else{
            if (item.getItemMeta().getDisplayName().equals("Witcher Sword")) {
                player.sendMessage(item.getItemMeta().getDisplayName());
                if (dead) {
                    switch (clickedEntity.getType()) {
                        case CREEPER:
                            //give creeper's head to the player
                            player.sendMessage("given");
                            ItemStack creeperHead = new ItemStack(Material.CREEPER_HEAD, 1);
                            player.getInventory().addItem(creeperHead);
                            break;
                        case ZOMBIE:
                            ItemStack zombieHead = new ItemStack(Material.ZOMBIE_HEAD, 1);
                            player.getInventory().addItem(zombieHead);
                            break;
                        case SKELETON:
                            ItemStack skeletonHead = new ItemStack(Material.SKELETON_SKULL);
                            player.getInventory().addItem(skeletonHead);
                        case PLAYER:
                            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                            player.getInventory().addItem(playerHead);
                        default:
                            //event.setCancelled(true);
                            break;
                    }
                }
                else
                {
                    player.sendMessage("not still dead");
                }
            }
            else{
                player.sendMessage("fail");
                player.sendMessage(item.getItemMeta().getDisplayName());
            }
        }

    }

    public void setCreeperSteak(){
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 1);
        ItemMeta steakMeta = steak.getItemMeta();
        steakMeta.setDisplayName("Creeper's steak");

    }

    public void setWitcherSword(ItemStack item)
    {
        ItemMeta itemM = item.getItemMeta();
        itemM.setDisplayName("Witcher Sword");
        itemM.setLore(Arrays.asList("able to behead mobs!"));

    }
}
