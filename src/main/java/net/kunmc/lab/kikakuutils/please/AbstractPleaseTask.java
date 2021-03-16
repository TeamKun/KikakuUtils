package net.kunmc.lab.kikakuutils.please;

import org.bukkit.scheduler.BukkitRunnable;

abstract public class AbstractPleaseTask extends BukkitRunnable {
    private boolean isAliveTask;

    public AbstractPleaseTask() {
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
