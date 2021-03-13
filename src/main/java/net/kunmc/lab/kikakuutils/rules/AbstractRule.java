package net.kunmc.lab.kikakuutils.rules;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;

abstract public class AbstractRule {
    protected CommandSender sender;
    protected LocalDateTime lastExecTime;
    protected String target;

    protected CommandSender getSender() {
        return sender;
    }

    protected void setSender(CommandSender sender) {
        this.sender = sender;
    }

    protected LocalDateTime getLastExecTime() {
        return lastExecTime;
    }

    protected String getTarget() {
        return target;
    }

    protected void setTarget(String target) {
        this.target = target;
    }

    protected void setExecTime() {
        this.lastExecTime = LocalDateTime.now();
    }

    protected boolean isPlayerContainedInTarget(Player player) {
        // CAUTION: これオフラインプレイヤーで実行したらどうなるの？
        List<Entity> entities = Bukkit.selectEntities(getSender(), getTarget());

        return entities.stream()
                .anyMatch(v -> v instanceof Player && v.equals(player));
    }

    abstract public boolean setRule(CommandSender sender, String value, String target);

    abstract public void applyToPlayer(Player player);
}
