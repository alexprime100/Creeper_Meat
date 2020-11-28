package fr.alexprime100.creeper_meat;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("Creeper_meat2 just started");
        getServer().getPluginManager().registerEvents(new MeatListener(), this);
        //Timer timer = new Timer();
        //timer.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        System.out.println("Creeper_meat just stopped");
    }
}
