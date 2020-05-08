package fr.alexprime100.creeper_meat.listeners;

import fr.alexprime100.creeper_meat.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TestListener implements Listener {

    private Main mainClass;

    public TestListener(Main main) {
        this.mainClass = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Test0");

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        player.sendMessage("test1");
        if (item == null) return;

        switch (item.getType()){
            case DIAMOND_AXE:
                player.sendMessage("test2");
                diamondAxeTestClick(event);
                break;
            case COMPASS:
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase("Magic compass")){
                    //compassPotionEffect(event);
                }
                break;
            case STICK:
                //stickInventory(event);
                break;
            default:
                player.sendMessage("testX2");
                break;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player)event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;

        if (event.getView().getTitle().equalsIgnoreCase("§8My menu")){
            MyMenu(event);
        }


    }

    public void giveStuffOnConnect(PlayerJoinEvent event){
        Player player = event.getPlayer();
        //player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE, 3));
		/*ItemStack customItem = new ItemStack(Material.COMPASS, 1);
		ItemMeta customMeta = customItem.getItemMeta();
		customMeta.setDisplayName("Magic compass");
		customMeta.setLore(Arrays.asList("stronger", "better"));
		customItem.setItemMeta(customMeta);
		player.getInventory().setItemInMainHand(customItem);*/
        player.updateInventory();
    }

    public void diamondAxeTestClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        player.sendMessage("test3");
        if (action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR){
            player.sendMessage("you just clicked with a diamond axe in the air");
        }
        if (action == Action.LEFT_CLICK_BLOCK){
            player.sendMessage("you just hitted a block with a diamond axe");
        }
        if (action == Action.RIGHT_CLICK_BLOCK){
            player.sendMessage("you just clicked with a diamond axe on a block");
        }
    }

    public void compassPotionEffect(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        player.sendMessage(mainClass.getConfig().getString("messages.broadcast.plug1Listener.compassMessage"));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
    }

    public void stickInventory(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        Inventory inventory = Bukkit.createInventory(null, 54, "§8My menu");
        ItemStack legendarySword = give (Material.DIAMOND_SWORD, 1, "Legendary sword");
        ItemStack apples = give(Material.APPLE, 5, "poisonned apples");
        inventory.setItem(0, legendarySword);
        inventory.setItem(1,apples);
        player.openInventory(inventory);
    }

    public ItemStack give (Material material, int quantity, String customName){
        ItemStack item = new ItemStack(material, quantity);
        if (customName != null && !customName.equals("")){
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(customName);
            item.setItemMeta(itemMeta);
        }

        return item;
    }

    public void MyMenu(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player)event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        event.setCancelled(true);
        player.closeInventory();

        switch (currentItem.getType() ){
            case APPLE:
                if (currentItem.getItemMeta().getDisplayName().equalsIgnoreCase("poisonned apples")){
                    player.kickPlayer("you have been poisonned");
                }
                break;
            case DIAMOND_SWORD:
                if (currentItem.getItemMeta().getDisplayName().equalsIgnoreCase("Legendary sword")){
                    player.setGameMode(GameMode.SURVIVAL);
                }
                break;
            default:break;
        }
    }
}
