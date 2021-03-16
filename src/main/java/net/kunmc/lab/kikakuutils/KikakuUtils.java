package net.kunmc.lab.kikakuutils;

import net.kunmc.lab.kikakuutils.command.KikakuCmd;
import net.kunmc.lab.kikakuutils.command.PleaseCmd;
import net.kunmc.lab.kikakuutils.please.PleaseInternalCommand;
import net.kunmc.lab.kikakuutils.please.PleaseManager;
import net.kunmc.lab.kikakuutils.rules.RuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KikakuUtils extends JavaPlugin {
    public static KikakuUtils plugin;
    public RuleManager ruleManager;
    public PleaseManager pleaseManager;

    @Override
    public void onEnable() {
        this.plugin = this;

        ruleManager = new RuleManager();
        pleaseManager = new PleaseManager();

        this.getCommand("kikaku").setExecutor(new KikakuCmd());
        this.getCommand("kikaku-internal-please").setExecutor(new PleaseInternalCommand());
        this.getCommand("please").setExecutor(new PleaseCmd());

        getServer().getPluginManager().registerEvents(ruleManager, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
