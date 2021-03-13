package net.kunmc.lab.kikakuutils;

import net.kunmc.lab.kikakuutils.command.Ku;
import org.bukkit.plugin.java.JavaPlugin;

public final class KikakuUtils extends JavaPlugin {
    public static KikakuUtils plugin;

    @Override
    public void onEnable() {
        this.plugin = this;
        this.getCommand("ku").setExecutor(new Ku());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
