package net.kunmc.lab.kikakuutils.rules;

import org.bukkit.command.CommandSender;

public class RuleConfig {
    private String target;
    private CommandSender sender;
    private ExecutionAttitude attitude;

    public RuleConfig(CommandSender sender, String target, ExecutionAttitude attitude) {
        this.target = target;
        this.sender = sender;
        this.attitude = attitude;
    }

    public String getTarget() {
        return this.target;
    }

    public CommandSender getSender() {
        return sender;
    }

    public ExecutionAttitude getAttitude() {
        return attitude;
    }
}
