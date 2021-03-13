package net.kunmc.lab.kikakuutils.rules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;

public class KikakuRuleManager implements Listener {
    private AbstractRule[] rules;
    public RuleGameMode gameMode;

    public KikakuRuleManager() {
        gameMode = new RuleGameMode();

        rules = new AbstractRule[]{
                gameMode,
        };
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Arrays.stream(rules)
                .filter(v -> v.isPlayerContainedInTarget(player))
                .forEach(v -> v.applyToPlayer(player));
    }

    public void execImmediately() {
    }
}
