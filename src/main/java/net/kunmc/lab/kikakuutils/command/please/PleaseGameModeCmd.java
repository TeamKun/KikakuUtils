package net.kunmc.lab.kikakuutils.command.please;

import net.kunmc.lab.kikakuutils.KikakuUtils;
import net.kunmc.lab.kikakuutils.command.AbstractArgument;
import net.kunmc.lab.kikakuutils.please.PleaseGameMode;
import net.kunmc.lab.kikakuutils.utils.GameModeUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PleaseGameModeCmd extends AbstractArgument {
    // /please gamemode <GAMEMODE> [TARGET_SELECTOR]

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length < 2) return false;
        if (args.length > 3) return false;
        if (!args[0].equals("gamemode")) return false;

        String gameModeStr = args[1];
        String targetStr = (args.length == 3) ? args[2] : sender.getName();

        Optional<GameMode> gameModeOptional = GameModeUtils.toGameMode(gameModeStr);
        if (!gameModeOptional.isPresent()) {
            String message = String.format("%s 引数が不正です。正しいゲームモードを入力してください。", PREFIX_REJECT);
            sender.sendMessage(message);
            return true;
        }
        GameMode gameMode = gameModeOptional.get();

        List<Entity> entities = Bukkit.selectEntities(sender, targetStr);
        List<Player> players = entities.stream()
                .filter(v -> v instanceof Player)
                .map(v -> (Player) v)
                .collect(Collectors.toList());
        if (players.isEmpty()) {
            String message = String.format("%s 対象となるプレイヤーが見つかりませんでした。", PREFIX_REJECT);
            sender.sendMessage(message);
            return true;
        }

        PleaseGameMode pleaseGameMode = KikakuUtils.plugin.pleaseManager.gameMode;
        players.forEach(v -> pleaseGameMode.exec(sender, v, gameMode));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 0) return null;

        if (args.length == 1) {
            if ("gamemode".startsWith(args[0])) {
                return Collections.singletonList("gamemode");
            }

            return null;
        }

        // args[0] が "gamemode" でないものをのぞく
        if (!args[0].equals("gamemode")) return null;

        String gameMode = args[1];
        if (args.length == 2) {
            List<String> suggestion = GameModeUtils.stringLowerCaseValues()
                    .stream()
                    .filter(v -> v.startsWith(gameMode))
                    .collect(Collectors.toList());

            return suggestion;
        }

        // gamemode = args[1] がゲームモードでないものを除く
        if (!GameModeUtils.isGameMode(gameMode)) return null;

        String target = args[2];
        if (args.length == 3) {
            List<String> suggestion = Arrays.asList("@a", "@p", "@r")
                    .stream()
                    .filter(v -> v.startsWith(target))
                    .collect(Collectors.toList());

            KikakuUtils.plugin.getServer().getOnlinePlayers()
                    .stream()
                    .map(v -> v.getName())
                    .filter(v -> v.startsWith(target))
                    .forEach(v -> suggestion.add(v));

            return suggestion;
        }

        return null;
    }
}
