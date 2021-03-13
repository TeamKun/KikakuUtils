package net.kunmc.lab.kikakuutils.rules;

import net.kunmc.lab.kikakuutils.utils.GameModeUtils;
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

        // TODO: 権限確認
        // TODO: 権限を持っていたら適用の確認をする
        // TODO: 拒否られたらreturn

        player.setGameMode(getGameMode());
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
