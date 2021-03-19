package net.kunmc.lab.kikakuutils.ask;

import net.kunmc.lab.kikakuutils.KikakuUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class AskInternalCommand implements CommandExecutor {
    // /kikaku-internal-please <name> <token>"

    public static final String command = "/kikaku-internal-please";

    private BaseComponent[] createErrorMessage(String text) {
        BaseComponent[] baseComponents = new ComponentBuilder()
                .append("[Kikaku Utils] ").color(ChatColor.RED)
                .append(text).color(ChatColor.WHITE)
                .create();
        return baseComponents;
    }

    private BaseComponent[] createRejectCompleteMessage() {
        BaseComponent[] baseComponents = new ComponentBuilder()
                .append("[Kikaku Utils] ").color(ChatColor.LIGHT_PURPLE)
                .append("拒否しました！").color(ChatColor.WHITE)
                .create();
        return baseComponents;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            BaseComponent[] bc = createErrorMessage("このコマンドはプラグインの内部コマンドです");
            sender.sendMessage(bc);
            return true;
        }

        if (!(sender instanceof Player)) {
            BaseComponent[] bc = createErrorMessage("このコマンドはプレイヤー専用です");
            sender.sendMessage(bc);
            return true;
        }

        String name = args[0];
        String token = args[1];

        AskManager askManager = KikakuUtils.plugin.askManager;
        Optional<AbstractAsk> pleaseOptional = Arrays.stream(askManager.please)
                .filter(v -> v.getArgument().equals(name))
                .findAny();

        if (pleaseOptional.isPresent()) {
            AbstractAsk please = pleaseOptional.get();
            boolean rejectComplete = please.reject(token);
            if (rejectComplete) sender.sendMessage(createRejectCompleteMessage());
            return true;
        }

        String text = "内部コマンドの引数が間違っています。プラグイン開発者に連絡ください。";
        BaseComponent[] bc = createErrorMessage(text);
        sender.sendMessage(bc);
        return true;
    }
}
