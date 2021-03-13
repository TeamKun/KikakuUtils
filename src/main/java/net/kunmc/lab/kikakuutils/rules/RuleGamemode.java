package net.kunmc.lab.kikakuutils.rules;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

import static net.kunmc.lab.kikakuutils.utils.Utils.isValidTarget;

public class RuleGamemode extends AbstractRule {
    private GameMode gameMode;

    private Optional<GameMode> toGameMode(String text) {
        if (text.equals("adventure")) return Optional.of(GameMode.ADVENTURE);
        if (text.equals("creative")) return Optional.of(GameMode.CREATIVE);
        if (text.equals("spectator")) return Optional.of(GameMode.SPECTATOR);
        if (text.equals("survival")) return Optional.of(GameMode.SURVIVAL);
        return Optional.empty();
    }

    private GameMode getGameMode() {
        return gameMode;
    }

    private void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public boolean setRule(CommandSender sender, String value, String target) {
        // validation
        Optional<GameMode> gameMode = toGameMode(value);
        if (!gameMode.isPresent()) return false;
        if (!isValidTarget(sender, target)) return false;

        setSender(sender);
        setTarget(target);
        setGameMode(gameMode.get());
        setExecTime();
        return true;
    }

    @Override
    public void applyToPlayer(Player player) {
        if (!player.isOnline()) return;
        if (!isPlayerContainedInTarget(player)) return;

        // TODO: 最後に設定された時間チェック
        // TODO: 最後に設定された時間が一致したらreturn

        // TODO: 権限確認
        // TODO: 権限を持っていたら適用の確認をする
        // TODO: 拒否られたらreturn

        player.setGameMode(getGameMode());
    }
}
