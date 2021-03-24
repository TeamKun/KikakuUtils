package net.kunmc.lab.kikakuutils.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

abstract public class AbstractArgument {
    protected static final String PREFIX_ACCEPT = ChatColor.GREEN + "[Kikaku Utils]" + ChatColor.RESET;
    protected static final String PREFIX_REJECT = ChatColor.RED + "[Kikaku Utils]" + ChatColor.RESET;

    protected String createSuccessMessage(String text) {
        return String.format("%s %s", PREFIX_ACCEPT, text);
    }

    protected String createFailedMessage(String text) {
        return String.format("%s %s", PREFIX_REJECT, text);
    }

    abstract public boolean onCommand(CommandSender sender, String[] args);

    abstract public List<String> tabComplete(CommandSender sender, String[] args);
}
