package xyz.mlserver.miniGameCmdLib.utils;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.mlserver.java.Log;
import xyz.mlserver.miniGameCmdLib.MiniGameCmdLib;

import java.util.HashMap;
import java.util.UUID;

public class HoloTimer {

    private static HashMap<UUID, BukkitTask> tasks;

    public static HashMap<UUID, BukkitTask> getTasks() {
        if (tasks == null) {
            tasks = new HashMap<>();
        }
        return tasks;
    }

    private static HashMap<UUID, Hologram> holograms;

    public static HashMap<UUID, Hologram> getHolograms() {
        if (holograms == null) {
            holograms = new HashMap<>();
        }
        return holograms;
    }

    public static void addTimer(BukkitTask task, Hologram hologram) {
        UUID uuid = UUID.randomUUID();
        getTasks().put(uuid, task);
        getHolograms().put(uuid, hologram);
    }

    public static void removeAllTasks() {
        for (BukkitTask task : getTasks().values()) if (!task.isCancelled()) task.cancel();
        getTasks().clear();
        for (Hologram hologram : getHolograms().values()) {
            for (HologramPage page : hologram.getPages()) {
                page.realignLines();
            }
        }
        getHolograms().clear();
    }

    public static void removeTask(UUID uuid) {
        BukkitTask task = getTasks().get(uuid);
        if (task != null && !task.isCancelled()) task.cancel();
        getTasks().remove(uuid);
        Hologram hologram = getHolograms().get(uuid);
        if (hologram != null) hologram.destroy();
        getHolograms().remove(uuid);
    }

    public static HashMap<BukkitTask, Hologram> startTask(Location loc, int count) {
        UUID uuid = UUID.randomUUID();
        if (DHAPI.getHologram(uuid.toString()) != null)
            return null;

        // 初期ホログラムのラインを定義
        String line = "||||||||||||||||||||||||||||||";
        Hologram hologram = DHAPI.createHologram(uuid.toString(), loc, false);
        DHAPI.addHologramLine(hologram, "&7" + line);

        int countFinal = count * 20;

        BukkitTask task = new BukkitRunnable() {
            int countTemp = countFinal;

            @Override
            public void run() {
                if (countTemp == 0) {
                    cancel();
                    DHAPI.setHologramLine(hologram, 0, "&a" + line);
                    return;
                }

                int greenLength = (int) ((countFinal - countTemp) * (line.length() / (double) countFinal));

                String greenPart = "&a" + line.substring(0, greenLength);
                String grayPart = "&7" + line.substring(greenLength);

                DHAPI.setHologramLine(hologram, 0, greenPart + grayPart);

                countTemp--;
            }
        }.runTaskTimer(MiniGameCmdLib.getPlugin(), 0, 1);
        return new HashMap<BukkitTask, Hologram>() {{
            put(task, hologram);
        }};
    }


}
