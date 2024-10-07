package xyz.mlserver.miniGameCmdLib.utils;

import it.unimi.dsi.fastutil.Hash;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class GlassTimer {

    private static HashMap<UUID, BukkitTask> tasks;

    public static HashMap<UUID, BukkitTask> getTasks() {
        if (tasks == null) {
            tasks = new HashMap<>();
        }
        return tasks;
    }

    public static UUID addTask(BukkitTask task) {
        UUID uuid = UUID.randomUUID();
        getTasks().put(uuid, task);
        return uuid;
    }

    public static void removeAllTasks() {
        for (BukkitTask task : getTasks().values()) if (!task.isCancelled()) task.cancel();
        getTasks().clear();
    }

}
