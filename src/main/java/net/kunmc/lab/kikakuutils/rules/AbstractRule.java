package net.kunmc.lab.kikakuutils.rules;

import net.kunmc.lab.kikakuutils.KikakuUtils;
import net.kunmc.lab.kikakuutils.utils.LocalDateTimeType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import java.time.LocalDateTime;
import java.util.List;

abstract public class AbstractRule {
    private CommandSender commandSender;
    private String target;
    private LocalDateTime registerTime;

    private NamespacedKey key;

    protected AbstractRule(String key) {
        this.key = new NamespacedKey(KikakuUtils.plugin, key);
    }

    protected void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    protected void setTarget(String target) {
        this.target = target;
    }

    protected void setRegisterTime() {
        this.registerTime = LocalDateTime.now();
    }

    protected CommandSender getCommandSender() {
        return this.commandSender;
    }

    protected String getTarget() {
        return this.target;
    }

    protected boolean isAppliedToPlayer(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(key, LocalDateTimeType.type)) return false;

        LocalDateTime playerTime = container.get(key, LocalDateTimeType.type);
        if (playerTime.isBefore(registerTime)) return false;

        return true;
    }

    protected void setExecTimeToPlayer(Player player) {
        LocalDateTime now = LocalDateTime.now();
        player.getPersistentDataContainer().set(key, LocalDateTimeType.type, now);
    }

    public boolean isPlayerContainedInTarget(Player player) {
        // CAUTION: これsenderにオフラインプレイヤーを指定して実行したらどうなるの？
        List<Entity> entities = Bukkit.selectEntities(commandSender, target);

        return entities.stream()
                .anyMatch(v -> v instanceof Player && v.equals(player));
    }

    abstract public boolean setRule(CommandSender sender, String value, String target);

    abstract public void applyToPlayer(Player player);

    abstract public void applyToAllTargetedPlayers();
}
