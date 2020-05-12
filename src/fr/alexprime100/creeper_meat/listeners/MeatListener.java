package fr.alexprime100.creeper_meat.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class MeatListener implements Listener {

    public MeatListener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //unitTest1(event);
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
    public void onClickEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Entity clickedEntity = event.getRightClicked();
        boolean dead = clickedEntity.isDead();
        if (item == null){
            return;
        }
        else{
            if (item.getItemMeta().getDisplayName().equals("Witcher Sword of the school of the creeper")) {
                if (dead) {
                    switch (clickedEntity.getType()) {
                        case CREEPER:
                            //gives creeper's head to the player
                            ItemStack creeperHead = new ItemStack(Material.CREEPER_HEAD, 1);
                            player.getInventory().addItem(creeperHead);
                            break;
                        case ZOMBIE:
                            //gives zombie's head to the player
                            ItemStack zombieHead = new ItemStack(Material.ZOMBIE_HEAD, 1);
                            player.getInventory().addItem(zombieHead);
                            break;
                        case SKELETON:
                            //gives skeleton's skull to the player
                            ItemStack skeletonHead = new ItemStack(Material.SKELETON_SKULL,1);
                            player.getInventory().addItem(skeletonHead);
                        case PLAYER:
                            //gives killed player's head to the player
                            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD,1);
                            player.getInventory().addItem(playerHead);
                        default:
                            break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        PlayerInventory inventory = player.getInventory();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();
        if (item == null) return;

        switch (item.getType()){
            case IRON_SWORD:
                if (action == Action.LEFT_CLICK_BLOCK && clickedBlock.getType() == Material.SMITHING_TABLE && !item.hasItemMeta()) {
                    player.sendMessage("Checking if you have enough redstone...");
                    ItemStack[] ingredients = new ItemStack[3];
                    ingredients[0] = new ItemStack(Material.REDSTONE, 3);
                    ingredients[1] = new ItemStack(Material.FLINT,2);
                    ingredients[2] = new ItemStack(Material.LAVA_BUCKET, 1);

                    if (inventory.contains(Material.REDSTONE, 3) && inventory.contains(Material.LAVA_BUCKET) && inventory.contains(Material.FLINT,2))
                    {
                        player.sendMessage("yes !");
                        //turns an iron sword into a witcher one
                        ItemMeta itemM = item.getItemMeta();
                        itemM.setDisplayName("Witcher Sword of the school of the creeper");
                        itemM.setLore(Arrays.asList("able to behead mobs!"));
                        for (ItemStack ingredient : ingredients) {
                            inventory.removeItem(ingredient);
                        }
                        item.setItemMeta(itemM);
                    }
                    else{
                        player.sendMessage("You don't have enough redstone to build a witcher sword");
                    }
                }
                break;
            case SHEARS:
                if (inventory.getItemInOffHand() != null) {
                    if (inventory.getItemInOffHand().getType() == Material.CREEPER_HEAD && action == Action.LEFT_CLICK_BLOCK && clickedBlock.getType() == Material.CRAFTING_TABLE) {
                        ItemStack creeperMeat = new ItemStack(Material.ROTTEN_FLESH, 1);
                        ItemMeta creeperMeatMeta = creeperMeat.getItemMeta();
                        creeperMeatMeta.setDisplayName("Raw creeper meat");
                        creeperMeat.setItemMeta(creeperMeatMeta);
                        inventory.getItemInOffHand().setAmount(inventory.getItemInOffHand().getAmount() - 1);
                        inventory.addItem(creeperMeat);
                    }
                }
                break;
            case STICK:
                if (inventory.getItemInOffHand() != null) {
                    if (inventory.getItemInOffHand().hasItemMeta()) {
                        ItemStack[] ingredients = new ItemStack[3];
                        ingredients[0] = new ItemStack(Material.MILK_BUCKET);
                        ingredients[1] = new ItemStack(Material.EGG, 2);
                        ingredients[2] = new ItemStack(Material.BROWN_MUSHROOM, 1);
                        boolean bIngredients = inventory.contains(Material.MILK_BUCKET,1) && inventory.contains(Material.EGG, 2) && inventory.contains(Material.BROWN_MUSHROOM, 1);

                        if (inventory.getItemInOffHand().getItemMeta().getDisplayName().equals("Raw creeper meat")  && bIngredients && action == Action.LEFT_CLICK_BLOCK) {
                            if (clickedBlock.getType() == Material.CAULDRON && clickedBlock.getBlockData() instanceof Levelled) {
                                Levelled levelledBlock = (Levelled) clickedBlock.getBlockData();
                                int level = levelledBlock.getLevel();
                                if (level == 3) {
                                    ItemStack creeperSteak = new ItemStack(Material.COOKED_BEEF, 1);
                                    ItemMeta creeperSteakMeta = creeperSteak.getItemMeta();
                                    if (clickedBlock.getRelative(0,-1,0).getType() == Material.CAMPFIRE){
                                        if (inventory.contains(Material.HONEY_BOTTLE,1)){
                                            creeperSteakMeta.setDisplayName("Creeper steak");
                                            creeperSteakMeta.setLore(Arrays.asList(""));
                                            inventory.removeItem(new ItemStack(Material.HONEY_BOTTLE,1));
                                        }
                                        else{
                                            creeperSteakMeta.setDisplayName("Creeper steak");
                                            creeperSteakMeta.setLore(Arrays.asList("Cooked in hot cauldron"));
                                        }
                                    }
                                    else{
                                        creeperSteak = new ItemStack(Material.BEEF, 1);
                                        creeperSteakMeta = creeperSteak.getItemMeta();
                                        if (inventory.contains(Material.HONEY_BOTTLE,1)){
                                            creeperSteakMeta.setDisplayName("Raw creeper steak");
                                            creeperSteakMeta.setLore(Arrays.asList("cooked with expertise"));
                                            inventory.removeItem(new ItemStack(Material.HONEY_BOTTLE,1));
                                        }
                                        else{
                                            creeperSteakMeta.setDisplayName("Raw creeper steak");
                                            creeperSteakMeta.setLore(Arrays.asList(""));
                                        }
                                    }
                                    creeperSteak.setItemMeta(creeperSteakMeta);
                                    inventory.getItemInOffHand().setAmount(inventory.getItemInOffHand().getAmount() - 1);
                                    for (ItemStack itemStack : ingredients){
                                        inventory.removeItem(itemStack);
                                    }
                                    inventory.addItem(creeperSteak);
                                }
                            }
                        }
                    }
                }
                break;
            default: break;
        }
    }

    @EventHandler
    public void onCook(FurnaceSmeltEvent event){
        ItemStack sourceItem = event.getSource();
        ItemStack resultItem = event.getResult();

        if (sourceItem != null){
            if (sourceItem.hasItemMeta()){
                if (sourceItem.getItemMeta().getDisplayName().equals("Raw creeper steak")){
                    ItemMeta cookedSteakMeta = resultItem.getItemMeta();
                    cookedSteakMeta.setDisplayName("Creeper steak");
                    if (sourceItem.getItemMeta().getLore().size() > 0){
                        if (sourceItem.getItemMeta().getLore().get(0).equals("Cooked with expertise")) {
                            cookedSteakMeta.setLore(Arrays.asList("Cooked with expertise in master furnace"));
                        }
                        else{
                            cookedSteakMeta.setLore(Arrays.asList("Cooked in master furnace"));
                        }
                    }
                    else{
                        cookedSteakMeta.setLore(Arrays.asList("Cooked in master furnace"));
                    }
                    resultItem.setItemMeta(cookedSteakMeta);
                }
            }
        }
    }
        
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack meal = event.getItem();

        if (meal.hasItemMeta()) {
            ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
            if (meal.getItemMeta().getDisplayName().equals("Raw creeper meat")) {
                effects.add(new PotionEffect(PotionEffectType.HARM, 60, 2, true, true, true));
                effects.add(new PotionEffect(PotionEffectType.POISON, 20 * 10, 2, true, true, true));
                effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60, 2, true, true, true));
            }
            if (meal.getItemMeta().getDisplayName().equals("Raw creeper steak")){
                if(meal.getItemMeta().getLore().get(0).equals("")) {
                    effects.add(new PotionEffect(PotionEffectType.POISON, 20 * 10, 2, true, true, true));
                    effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60, 2, true, true, true));
                }
                if(meal.getItemMeta().getLore().get(0).equals("cooked with expertise")) {
                    int random2 = (int)(Math.random())*10;
                    if (player.getFoodLevel() > 18){
                        player.setFoodLevel(20);
                    }
                    else{
                        player.setFoodLevel(player.getFoodLevel()+2);
                    }
                    if (random2 >= 0 && random2 < 3){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*30, 1));
                    }
                }
            }

            if (meal.getItemMeta().getDisplayName().equals("Creeper steak")){
                int[][] coeff = new int[11][2];
                int random= -1;
                coeff[0][0] = 0;
                coeff[10][1] = 100;
                if (meal.getItemMeta().getLore().get(0).equals("")){
                    effects.add(new PotionEffect(PotionEffectType.LUCK, 20 * 60, 2, true, true, true));
                    effects.add(new PotionEffect(PotionEffectType.REGENERATION, 20 * 60, 2, true, true, true));
                    effects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 60, 2, true, true, true));
                    effects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 2, true, true, true));
                    effects.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60, 2, true, true, true));
                    player.setFoodLevel(20);
                }
                if (meal.getItemMeta().getLore().get(0).equals("Cooked in master furnace")) {
                    random = (int) (Math.random() * 100);
                    coeff[1][0] = 25;
                    coeff[2][0] = 45;
                    coeff[3][0] = 60;
                    coeff[4][0] = 70;
                    coeff[5][0] = 80;
                    coeff[6][0] = 85;
                    coeff[7][0] = 90;
                    coeff[8][0] = 95;
                    coeff[9][0] = 100;
                    coeff[10][0] = 100;
                    for (int i = 0; i < 10; i++) {
                        coeff[i][1] = coeff[i + 1][0];
                    }
                    //effects = setRandomEffects(player, coeff);
                }
                if (meal.getItemMeta().getLore().get(0).equals("Cooked in hot cauldron")) {
                    random = (int) (Math.random() * 100);
                    coeff[1][0] = 15;
                    coeff[2][0] = 30;
                    coeff[3][0] = 40;
                    coeff[4][0] = 50;
                    coeff[5][0] = 60;
                    coeff[6][0] = 70;
                    coeff[7][0] = 80;
                    coeff[8][0] = 90;
                    coeff[9][0] = 95;
                    coeff[10][0] = 100;
                    for (int i = 0; i < 10; i++) {
                        coeff[i][1] = coeff[i + 1][0];
                    }

                }
                if (meal.getItemMeta().getLore().get(0).equals("Cooked with expertise in master furnace")) {
                    random = (int) (Math.random() * 100);
                    coeff[1][0] = 0;
                    coeff[2][0] = 0;
                    coeff[3][0] = 5;
                    coeff[4][0] = 10;
                    coeff[5][0] = 15;
                    coeff[6][0] = 30;
                    coeff[7][0] = 40;
                    coeff[8][0] = 50;
                    coeff[9][0] = 75;
                    coeff[10][0] = 95;
                    for (int i = 0; i < 10; i++) {
                        coeff[i][1] = coeff[i + 1][0];
                    }

                }
                if (random >= 0) {
                    player.setFoodLevel(player.getFoodLevel()-8);
                    if (random >= coeff[0][0] && random < coeff[0][1]) {   //kills slowly the player
                        while (player.getHealth() > 0) {
                            if (player.getHealth() < 1) {
                                player.setHealth(0);
                            } else {
                                player.setHealth(player.getHealth() - 1);
                            }
                        }
                    }
                    if (random >= coeff[1][0] && random < coeff[1][1]) {   //instant damages
                        if (player.getHealth() >= 6) {
                            player.setHealth(player.getHealth() - 6);
                        } else {
                            player.setHealth(0);
                        }
                    }
                    if (random >= coeff[2][0] && random < coeff[2][1]) {   //poison
                        effects.add(new PotionEffect(PotionEffectType.POISON, 20 * 20, 1));
                    }
                    if (random >= coeff[3][0] && random < coeff[3][1]) {   //blindness & slowness
                        effects.add(new PotionEffect(PotionEffectType.SLOW, 20 * 60, 2, true, true, true));
                        effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60, 2, true, true, true));
                    }
                    if (random >= coeff[4][0] && random < coeff[4][1]) {
                        effects.add(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60, 2, true, true, true));
                        effects.add(new PotionEffect(PotionEffectType.SLOW, 20 * 60, 2, true, true, true));
                    }
                    if (random >= coeff[5][0] && random < coeff[5][1]) {   //slowness
                        effects.add(new PotionEffect(PotionEffectType.SLOW, 20 * 60, 2, true, true, true));
                    }
                    if (random >= coeff[6][0] && random < coeff[6][1]) {   //tiredness
                        effects.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 60, 2, true, true, true));
                    }
                    if (random >= coeff[7][0] && random < coeff[7][1]) {   //weakness
                        effects.add(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 60, 2, true, true, true));
                    }
                    if (random >= coeff[8][0] && random < coeff[8][1]) {   //+4 hunger
                        if (player.getFoodLevel() < 16) {
                            player.setFoodLevel(player.getFoodLevel() + 4
                            );
                        } else {
                            player.setFoodLevel(20);
                        }
                    }
                    if (random >= coeff[9][0] && random < coeff[9][1]) {  //+8 hunger
                        if (player.getFoodLevel() < 12) {
                            player.setFoodLevel(player.getFoodLevel() + 8);
                        } else {
                            player.setFoodLevel(20);
                        }
                    }
                    if (random >= coeff[10][0] && random < coeff[10][1]) {
                        effects.add(new PotionEffect(PotionEffectType.REGENERATION, 20 * 60, 2, true, true, true));
                        effects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 60, 2, true, true, true));
                        effects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 2, true, true, true));
                        effects.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60, 2, true, true, true));
                        player.setFoodLevel(20);
                    }
                }
            }

            for (PotionEffect effect : effects){
                player.addPotionEffect(effect);
            }
        }
    }
}
