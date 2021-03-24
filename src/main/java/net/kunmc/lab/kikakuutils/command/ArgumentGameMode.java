package net.kunmc.lab.kikakuutils.command;

import net.kunmc.lab.kikakuutils.KikakuUtils;
import net.kunmc.lab.kikakuutils.rules.ExecutionAttitude;
import net.kunmc.lab.kikakuutils.rules.RuleGameMode;
import net.kunmc.lab.kikakuutils.utils.CommandUtils;
import net.kunmc.lab.kikakuutils.utils.GameModeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArgumentGameMode extends AbstractArgument {
    private CommandSender sender;
    private GameMode gameMode;
    private String targetSelector;
    private ExecutionAttitude attitude;
    private TargetPlayerState targetPlayerState;

    /**
     * ゲームモードの設定を全プレイヤー（含オフライン）に適用する
     * - コマンド内容
     * /ku gamemode <adventure|creative|survival|spectator> [TARGET_SELECTOR] [permission|ask|force] [all|online|offline]
     */

    // デフォルトの設定に初期化
    private void init(CommandSender sender) {
        this.sender = sender;
        this.gameMode = GameMode.SURVIVAL;
        this.targetSelector = "";
        this.attitude = ExecutionAttitude.PERMISSION;
        this.targetPlayerState = TargetPlayerState.ALL;
    }

    private boolean execution() {
        // null check
        if (attitude == null) return false;
        if (gameMode == null) return false;
        if (targetSelector == null) return false;
        if (targetPlayerState == null) return false;

        // オンラインプレイヤーへの適用
        if (targetPlayerState.equals(TargetPlayerState.ONLINE)) {
            // オンラインプレイヤーの一覧
            List<Player> targetPlayers = Bukkit.selectEntities(sender, this.targetSelector)
                    .stream()
                    .filter(v -> v instanceof Player)
                    .map(v -> (Player) v)
                    .collect(Collectors.toList());

            // 変更時のメッセージを作成
            BaseComponent[] message = new ComponentBuilder()
                    .append("[Kikaku Utils] ").color(ChatColor.LIGHT_PURPLE)
                    .append("ゲームモードが").color(ChatColor.WHITE)
                    .append(GameModeUtils.toJapanese(gameMode)).color(ChatColor.GREEN)
                    .append("に変更されました。").color(ChatColor.WHITE)
                    .create();

            // 変更の可否を確認するのは...
            targetPlayers.forEach(player -> {
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

                // 強制変更(含、権限を持たない人)
                targetPlayers.forEach(v -> {
                    v.setGameMode(this.gameMode);
                    v.sendMessage(message);
                });
            });
        }

        // オフラインのみ、あるいはオンラインとオフラインの両方
        RuleGameMode ruleGamemode = KikakuUtils.plugin.ruleManager.gameMode;
        ruleGamemode.setRule(new RuleGameMode.GameModeConfig(
                this.sender,
                this.gameMode,
                this.targetSelector,
                this.attitude
        ));

        // 全プレイヤー対象
        if (targetPlayerState.equals(TargetPlayerState.ALL)) {
            ruleGamemode.applyToTargetedOnlinePlayers();
        }

        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) return false;
        if (!args[0].equals("gamemode")) return false;

        // メンバ変数の初期化
        init(sender);

        if (args.length < 2) {
            String message = createFailedMessage("引数が不足しています");
            sender.sendMessage(message);
            return true;
        }

        if (args.length > 5) {
            String message = createFailedMessage("引数が多すぎます");
            sender.sendMessage(message);
            return true;
        }

        Optional<GameMode> gameModeOptional = GameModeUtils.toGameMode(args[1]);
        if (!gameModeOptional.isPresent()) {
            String message = createFailedMessage("ゲームモードが間違っています");
            sender.sendMessage(message);
            return true;
        }
        this.gameMode = gameModeOptional.get();

        int foundAttitudeIdx = 0;
        for (int i = 3; i < args.length; ++i) {
            // attitude
            Optional<ExecutionAttitude> attitudeOptional
                    = ExecutionAttitude.valueOf(args[i]);

            if (attitudeOptional.isPresent()) {
                if (foundAttitudeIdx != 0) {
                    String message = createFailedMessage("引数が不正です");
                    sender.sendMessage(message);
                    return true;
                }

                foundAttitudeIdx = i;
                attitude = attitudeOptional.get();
                continue;
            }

            // player state
            Optional<TargetPlayerState> targetPlayerStateOptional
                    = TargetPlayerState.valueOf(args[i]);

            if (targetPlayerStateOptional.isPresent()) {
                if (i != args.length - 1) {
                    String message = createFailedMessage("引数が不正です");
                    sender.sendMessage(message);
                    return true;
                }

                targetPlayerState = targetPlayerStateOptional.get();
                continue;
            }

            // target selector
            targetSelector = args[i];
            if (i != 3) {
                String message = createFailedMessage("引数が不正です");
                sender.sendMessage(message);
                return true;
            }
            if (!CommandUtils.isValidTarget(sender, targetSelector)) {
                String message = createFailedMessage("セレクタが間違っています");
                sender.sendMessage(message);
                return true;
            }
        }

        targetSelector = targetSelector.length() > 0 ? targetSelector : sender.getName();

        // 実行処理
        execution();

        String message = String.format("ゲームモード変更 : %s | 方法 : %s | 対象 : %s",
                GameModeUtils.toJapanese(this.gameMode),
                this.targetPlayerState.value(),
                this.attitude.value()
        );
        message = createSuccessMessage(message);
        sender.sendMessage(message);

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

        if (args.length == 3) {
            List<String> suggestion = Arrays.asList("@a", "@p", "@r");
            KikakuUtils.plugin.getServer().getOnlinePlayers()
                    .stream()
                    .map(v -> v.getName())
                    .forEach(v -> suggestion.add(v));

            Arrays.stream(ExecutionAttitude.values()).forEach(v -> suggestion.add(v));
            Arrays.stream(TargetPlayerState.values()).forEach(v -> suggestion.add(v));

            return suggestion.stream()
                    .filter(v -> v.startsWith(args[2]))
                    .collect(Collectors.toList());
        }

        // 第3引数にTargetPlayerStateが入っていたらそれ以上コマンドは続かない
        if (TargetPlayerState.contains(args[2])) {
            return null;
        }

        if (args.length == 4) {
            List<String> suggestion = Arrays.asList(TargetPlayerState.values());

            // 第３引数がターゲットだった場合
            if (!ExecutionAttitude.contains(args[2])) {
                Arrays.stream(ExecutionAttitude.values()).forEach(v -> suggestion.add(v));
            }

            return suggestion.stream()
                    .filter(v -> v.startsWith(args[3]))
                    .collect(Collectors.toList());
        }

        // 第4引数にTargetPlayerStateが入っていたらそれ以上コマンドは続かない
        if (TargetPlayerState.contains(args[3])) {
            return null;
        }

        if (args.length == 5) {
            if (ExecutionAttitude.contains(args[3])) {
                List<String> suggestion = Arrays.asList(TargetPlayerState.values())
                        .stream()
                        .filter(v -> v.startsWith(args[4]))
                        .collect(Collectors.toList());
            }
        }

        return null;
    }
}
