package net.kunmc.lab.kikakuutils.rules;

import net.kunmc.lab.kikakuutils.KikakuUtils;
import net.kunmc.lab.kikakuutils.utils.GameModeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RuleGameMode extends AbstractRule {
    public RuleGameMode() {
        super("gamemode");
    }

    @Override
    public void applyToPlayer(Player player, boolean checkTargetContain) {
        if (!player.isOnline()) return;
        if (isAppliedPlayer(player)) return;

        // チェックが必要な場合には、ターゲットにプレイヤーが含まれるか確認
        if (checkTargetContain && !isPlayerContainedInTarget(player)) return;

        GameModeConfig config = (GameModeConfig) getConfig();
        CommandSender sender = config.getSender();
        GameMode gameMode = config.getGameMode();
        ExecutionAttitude attitude = config.getAttitude();

        setExecTimeToPlayer(player);

        // 変更の可否を確認するのは...
        // 全員に確認
        if (attitude.equals(ExecutionAttitude.ASK)) {
            KikakuUtils.plugin.askManager.gameMode.exec(sender, player, gameMode);
            return;
        }

        // 権限で確認
        if (attitude.equals(ExecutionAttitude.PERMISSION)) {
            if (player.hasPermission("kikakuutils.rule.reject")) {
                KikakuUtils.plugin.askManager.gameMode.exec(sender, player, gameMode);
                return;
            }
        }

        // 強制はそのまま

        player.setGameMode(gameMode);
        BaseComponent[] message = new ComponentBuilder()
                .append("[Kikaku Utils] ").color(ChatColor.LIGHT_PURPLE)
                .append("ゲームモードが").color(ChatColor.WHITE)
                .append(GameModeUtils.toJapanese(gameMode)).color(ChatColor.GREEN)
                .append("に変更されました。").color(ChatColor.WHITE)
                .create();
        player.sendMessage(message);
    }

    @Override
    public void applyToTargetedOnlinePlayers() {
        CommandSender sender = getConfig().getSender();
        String target = getConfig().getTarget();

        Bukkit.selectEntities(sender, target)
                .stream()
                .filter(v -> v instanceof Player)
                .map(v -> (Player) v)
                .forEach(v -> applyToPlayer(v, false));
    }

    public static class GameModeConfig extends RuleConfig {
        private GameMode gameMode;

        public GameModeConfig(CommandSender sender, GameMode gameMode, String target, ExecutionAttitude attitude) {
            super(sender, target, attitude);
            this.gameMode = gameMode;
        }

        public GameMode getGameMode() {
            return gameMode;
        }
    }
}
