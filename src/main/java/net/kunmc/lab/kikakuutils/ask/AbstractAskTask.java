package net.kunmc.lab.kikakuutils.ask;

import org.bukkit.scheduler.BukkitRunnable;

abstract public class AbstractAskTask extends BukkitRunnable {
    private boolean isAliveTask;

    public AbstractAskTask() {
        isAliveTask = true;
    }

    public boolean isAliveTask() {
        return isAliveTask;
    }

    protected void setAlive(boolean isAlive) {
        isAliveTask = isAlive;
    }

    @Override
    public void cancel() throws IllegalStateException {
        isAliveTask = false;
        super.cancel();
    }
}
