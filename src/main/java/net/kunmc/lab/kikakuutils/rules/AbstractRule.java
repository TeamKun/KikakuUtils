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
    private LocalDateTime registerTime;
    private NamespacedKey key;
    private RuleConfig config;

    protected AbstractRule(String key) {
        this.key = new NamespacedKey(KikakuUtils.plugin, key);
    }

    protected void setRegisterTime() {
        this.registerTime = LocalDateTime.now();
    }

    protected boolean isAppliedPlayer(Player player) {
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
        CommandSender sender = getConfig().getSender();
        String target = getConfig().getTarget();

        List<Entity> entities = Bukkit.selectEntities(sender, target);

        return entities.stream()
                .anyMatch(v -> v instanceof Player && v.equals(player));
    }

    public void setRule(RuleConfig config) {
        setConfig(config);
        setRegisterTime();
    }

    protected void setConfig(RuleConfig config) {
        this.config = config;
    }

    protected RuleConfig getConfig() {
        return this.config;
    }

    abstract public void applyToPlayer(Player player, boolean checkTargetContain);

    abstract public void applyToTargetedOnlinePlayers();
}
