package fr.alexprime100.creeper_meat;

import fr.alexprime100.creeper_meat.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("Creeper_meat2 just started");
        getServer().getPluginManager().registerEvents(new MeatListener(), this);
        //getServer().getPluginManager().registerEvents(new TestListener(this), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Creeper_meat just stopped");
    }
}
