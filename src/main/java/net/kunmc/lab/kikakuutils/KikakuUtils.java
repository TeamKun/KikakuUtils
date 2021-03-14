package net.kunmc.lab.kikakuutils;

import net.kunmc.lab.kikakuutils.command.KikakuCmd;
import net.kunmc.lab.kikakuutils.rules.KikakuRuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KikakuUtils extends JavaPlugin {
    public static KikakuUtils plugin;
    public KikakuRuleManager ruleManager;

    @Override
    public void onEnable() {
        this.plugin = this;

        ruleManager = new KikakuRuleManager();

        this.getCommand("kikaku").setExecutor(new KikakuCmd());

        getServer().getPluginManager().registerEvents(ruleManager, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
