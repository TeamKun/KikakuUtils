package net.kunmc.lab.kikakuutils.command;

import net.kunmc.lab.kikakuutils.KikakuUtils;
import net.kunmc.lab.kikakuutils.command.AbstractArgument;
import net.kunmc.lab.kikakuutils.rules.RuleGameMode;
import net.kunmc.lab.kikakuutils.utils.GameModeUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArgumentGameMode extends AbstractArgument {
    /**
     * ゲームモードの設定を全プレイヤー（含オフライン）に適用する
     * - コマンド内容
     * /ku gamemode <adventure|creative|survival|spectator> [TARGET_SELECTOR] [select]
     */

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) return false;
        if (args.length > 3) return false;
        if (!args[0].equals("gamemode")) return false;

        String gameMode = args[1];
        String target = (args.length == 3) ? args[2] : sender.getName();

        RuleGameMode ruleGamemode = KikakuUtils.plugin.ruleManager.gameMode;
        boolean isSucceeded = ruleGamemode.setRule(sender, gameMode, target);

        // "/ku gamemode" まで入力されていて、かつコマンドが不適だったらエラーメッセージを送る
        if (!isSucceeded) {
            String message = String.format("%s 引数が不適当です。", PREFIX_REJECT);
            sender.sendMessage(message);

            message = "/ku gamemode <adventure | creative | survival | spectator> [player]";
            sender.sendMessage(message);
            return false;
        }

        String message = String.format("%s ゲームモードが %s に設定されました。", PREFIX_ACCEPT, gameMode);
        sender.sendMessage(message);

        ruleGamemode.applyToAllTargetedPlayers();

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
