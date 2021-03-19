package net.kunmc.lab.kikakuutils.ask;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractAsk {
    private int waitTimeSec = 10;
    private String argument;

    protected Map<String, AbstractAskTask> tickets = new HashMap<>();

    public AbstractAsk(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return this.argument;
    }

    protected int getWaitTimeSec() {
        return this.waitTimeSec;
    }

    protected String createToken() {
        String token = RandomStringUtils.random(16, true, true);
        return token;
    }

    protected String createRejectCommand(String token) {
        String[] commandParticles = new String[]{
                AskInternalCommand.command,
                getArgument(),
                token
        };

        String rejectCommand = String.join(" ", commandParticles);
        return rejectCommand;
    }

    protected BaseComponent[] createRejectHoverComponents() {
        BaseComponent[] baseComponents = new ComponentBuilder()
                .append("拒否するにはここをクリック！")
                .create();
        return baseComponents;
    }

    public boolean reject(String token) {
        if (!tickets.containsKey(token)) return false;

        // NOTE: Taskの生成が重い場合にはここも変更！
        AbstractAskTask task = tickets.get(token);
        tickets.remove(token);

        if (task.isCancelled()) return false;
        if (!task.isAliveTask()) return false;

        task.cancel();
        return true;
    }
}