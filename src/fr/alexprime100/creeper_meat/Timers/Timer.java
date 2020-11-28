package fr.alexprime100.creeper_meat.Timers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    private int timer = 10;

    @Override
    public void run() {
        Bukkit.broadcastMessage("lancement du jeu dans " + timer + " secondes");
        if (this.timer==0){
            cancel();
        }
        this.timer--;
    }
}

