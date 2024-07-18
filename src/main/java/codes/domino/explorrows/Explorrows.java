package codes.domino.explorrows;

import codes.domino.explorrows.listeners.ArrowAnvilListener;
import codes.domino.explorrows.listeners.ExplosiveArrowLandEvent;
import codes.domino.explorrows.listeners.ExplosiveArrowLaunchListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Explorrows extends JavaPlugin {

    private static Explorrows INSTANCE;

    public static Explorrows getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(new ArrowAnvilListener(), this);
        getServer().getPluginManager().registerEvents(new ExplosiveArrowLandEvent(), this);
        getServer().getPluginManager().registerEvents(new ExplosiveArrowLaunchListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
