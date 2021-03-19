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

import java.util.Optional;

import static net.kunmc.lab.kikakuutils.utils.CommandUtils.isValidTarget;

public class RuleGameMode extends AbstractRule {
    private GameMode gameMode;

    public RuleGameMode() {
        super("gamemode");
    }

    private GameMode getGameMode() {
        return gameMode;
    }

    private void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public boolean setRule(CommandSender sender, String value, String target) {
        Optional<GameMode> gameMode = GameModeUtils.toGameMode(value);
        if (!gameMode.isPresent()) return false;

        return setRule(sender, gameMode.get(), target);
    }

    public boolean setRule(CommandSender sender, GameMode gameMode, String target) {
        if (!isValidTarget(sender, target)) return false;

        setCommandSender(sender);
        setTarget(target);
        setGameMode(gameMode);
        setRegisterTime();

        return true;
    }

    @Override
    public void applyToPlayer(Player player) {
        if (!player.isOnline()) return;
        if (isAppliedToPlayer(player)) return;

        GameMode gameMode = getGameMode();

        // 権限を持っている場合には強制変更ではなく問い合わせ
        if (player.hasPermission("kikakuutils.rule.reject")) {
            CommandSender sender = getCommandSender();
            KikakuUtils.plugin.askManager.gameMode.exec(sender, player, gameMode);
        } else {
            player.setGameMode(gameMode);

            BaseComponent[] message = new ComponentBuilder()
                    .append("[Kikaku Utils] ").color(ChatColor.LIGHT_PURPLE)
                    .append("ゲームモードが").color(ChatColor.WHITE)
                    .append(GameModeUtils.toJapanese(gameMode)).color(ChatColor.GREEN)
                    .append("に変更されました。").color(ChatColor.WHITE)
                    .create();
            player.sendMessage(message);
        }

        setExecTimeToPlayer(player);
    }

    @Override
    public void applyToAllTargetedPlayers() {
        Bukkit.selectEntities(getCommandSender(), getTarget())
                .stream()
                .filter(v -> v instanceof Player)
                .map(v -> (Player) v)
                .forEach(v -> applyToPlayer(v));
    }
}
