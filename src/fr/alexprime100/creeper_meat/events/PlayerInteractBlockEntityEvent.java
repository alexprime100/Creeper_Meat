package fr.alexprime100.creeper_meat.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractBlockEntityEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    protected ItemStack item;
    protected Action action;
    protected Block blockClicked;
    protected BlockFace blockFace;
    protected Entity clickedEntity;
    private Result useClickedBlock;
    private Result useItemInHand;
    private EquipmentSlot hand;

    public PlayerInteractBlockEntityEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity) {
        this(who, action, item, clickedBlock, clickedFace, clickedEntity, EquipmentSlot.HAND);
    }

    public PlayerInteractBlockEntityEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace, Entity clickedEntity, EquipmentSlot hand) {
        super(who);
        this.action = action;
        this.item = item;
        this.blockClicked = clickedBlock;
        this.blockFace = clickedFace;
        this.clickedEntity = clickedEntity;
        this.hand = hand;
        this.useItemInHand = Result.DEFAULT;
        this.useClickedBlock = clickedBlock == null ? Result.DENY : Result.ALLOW;
    }

    public Action getAction() {
        return this.action;
    }

    public ItemStack getItem() {
        return this.item;
    }

    
    public Material getMaterial() {
        return !this.hasItem() ? Material.AIR : this.item.getType();
    }

    public boolean hasBlock() {
        return this.blockClicked != null;
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public boolean isBlockInHand() {
        return !this.hasItem() ? false : this.item.getType().isBlock();
    }

    
    public Block getClickedBlock() {
        return this.blockClicked;
    }

    
    public BlockFace getBlockFace() {
        return this.blockFace;
    }

    
    public Result useInteractedBlock() {
        return this.useClickedBlock;
    }

    public void setUseInteractedBlock( Result useInteractedBlock) {
        this.useClickedBlock = useInteractedBlock;
    }

    
    public Result useItemInHand() {
        return this.useItemInHand;
    }

    public void setUseItemInHand( Result useItemInHand) {
        this.useItemInHand = useItemInHand;
    }

    public Entity getRightClicked() {
        return this.clickedEntity;
    }

    
    public EquipmentSlot getHand() {
        return this.hand;
    }

    
    public HandlerList getHandlers() {
        return handlers;
    }

    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
