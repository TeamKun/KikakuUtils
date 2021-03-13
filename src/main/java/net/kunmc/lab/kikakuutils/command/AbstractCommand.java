package net.kunmc.lab.kikakuutils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;


public class AbstractCommand implements CommandExecutor, TabCompleter {
    private List<AbstractArgument> arguments = new ArrayList<>();

    protected void registerArgument(AbstractArgument argument) {
        arguments.add(argument);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;

        for (AbstractArgument e : this.arguments) {
            boolean isExecuted = e.executeCommand(sender, args);
            if (isExecuted) return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();

        for (AbstractArgument e : this.arguments) {
            List<String> suggestion = e.tabComplete(sender, args);
            if (suggestion == null) continue;

            result.addAll(suggestion);
        }

        return result;
    }
}
