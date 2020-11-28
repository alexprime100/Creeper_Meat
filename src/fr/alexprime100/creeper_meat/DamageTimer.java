package fr.alexprime100.creeper_meat;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageTimer extends BukkitRunnable {
    private Player player;

    public DamageTimer(Player player){
        this.player =player;
    }

    @Override
    public void run() {
        if (this.player.getHealth() <= 0){
            cancel();
        }
        this.player.setHealth(player.getHealth()-1);
    }
}
