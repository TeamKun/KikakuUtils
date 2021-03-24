package net.kunmc.lab.kikakuutils;

import net.kunmc.lab.kikakuutils.ask.AskInternalCommand;
import net.kunmc.lab.kikakuutils.ask.AskManager;
import net.kunmc.lab.kikakuutils.command.KikakuCommand;
import net.kunmc.lab.kikakuutils.rules.RuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KikakuUtils extends JavaPlugin {
    public static KikakuUtils plugin;
    public RuleManager ruleManager;
    public AskManager askManager;

    @Override
    public void onEnable() {
        this.plugin = this;

        ruleManager = new RuleManager();
        askManager = new AskManager();

        this.getCommand("kikaku").setExecutor(new KikakuCommand());
        this.getCommand("kikaku-internal-please").setExecutor(new AskInternalCommand());

        getServer().getPluginManager().registerEvents(ruleManager, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
