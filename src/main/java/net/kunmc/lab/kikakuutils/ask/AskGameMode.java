package net.kunmc.lab.kikakuutils.ask;

import net.kunmc.lab.kikakuutils.KikakuUtils;
import net.kunmc.lab.kikakuutils.utils.GameModeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AskGameMode extends AbstractAsk {
    public AskGameMode() {
        super("gamemode");
    }

    private BaseComponent[] createExecuteMessage(String sender, String gameMode, String token) {
        String waitTimeSecStr = String.valueOf(getWaitTimeSec());

        BaseComponent[] baseComponents = new ComponentBuilder()
                .append("[Kikaku Utils] ").color(ChatColor.LIGHT_PURPLE)
                .append(sender).color(ChatColor.WHITE)
                .append("がゲームモードを")
                .append(gameMode).color(ChatColor.GREEN)
                .append("に変えようとしています。").color(ChatColor.WHITE)
                .append("変更まで").color(ChatColor.WHITE)
                .append(waitTimeSecStr).color(ChatColor.WHITE)
                .append("秒！").color(ChatColor.WHITE)
                .append(" [クリックで拒否する]").color(ChatColor.GOLD)
                .create();

        BaseComponent[] hoverComponents = createRejectHoverComponents();
        String rejectCommand = createRejectCommand(token);

        BaseComponent[] ask = new ComponentBuilder(new TextComponent(baseComponents))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponents))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, rejectCommand))
                .create();

        return ask;
    }

    public void exec(CommandSender sender, Player target, GameMode gameMode) {
        String senderName = sender.getName();
        String gameModeStr = GameModeUtils.toJapanese(gameMode);
        String token = createToken();
        int waitTimeSec = getWaitTimeSec();

        BaseComponent[] executeMessage = createExecuteMessage(senderName, gameModeStr, token);
        target.sendMessage(executeMessage);

        // NOTE: 現状では1プレイヤ―につき1Taskを作っているが、重くなるなら複数プレイヤー適用に変更する
        Task task = new Task(target, gameMode);
        task.runTaskLater(KikakuUtils.plugin, waitTimeSec * 20);
        tickets.put(token, task);
    }

    public static class Task extends AbstractDelayedApplyTask {
        private Player target;
        private GameMode gameMode;

        public Task(Player target, GameMode gameMode) {
            super();
            this.target = target;
            this.gameMode = gameMode;
        }

        @Override
        public void run() {
            target.setGameMode(gameMode);

            BaseComponent[] message = new ComponentBuilder()
                    .append("[Kikaku Utils] ").color(ChatColor.LIGHT_PURPLE)
                    .append("ゲームモードが").color(ChatColor.WHITE)
                    .append(GameModeUtils.toJapanese(gameMode)).color(ChatColor.GREEN)
                    .append("に変更されました。").color(ChatColor.WHITE)
                    .create();
            target.sendMessage(message);

            setAlive(false);
        }
    }
}
