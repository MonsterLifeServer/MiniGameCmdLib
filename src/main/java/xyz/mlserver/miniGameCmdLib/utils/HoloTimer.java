package xyz.mlserver.miniGameCmdLib.utils;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
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
        for (Hologram hologram : getHolograms().values()) hologram.unregister();
        getHolograms().clear();
    }

    public static void removeTask(UUID uuid) {
        BukkitTask task = getTasks().get(uuid);
        if (task != null && !task.isCancelled()) task.cancel();
        getTasks().remove(uuid);
        Hologram hologram = getHolograms().get(uuid);
        if (hologram != null) hologram.unregister();
        getHolograms().remove(uuid);
    }

    public static HashMap<BukkitTask, Hologram> startTask(Location loc, int count) {
        UUID uuid = UUID.randomUUID();
        if (DHAPI.getHologram(uuid.toString()) != null)
            return null;

        // 初期ホログラムのラインを定義
        String line = "&7||||||||||||||||||||||||||||||";
        Hologram hologram = DHAPI.createHologram(uuid.toString(), loc, false);
        DHAPI.addHologramLine(hologram, line);

        BukkitTask task = new BukkitRunnable() {
            int countTemp = count;

            @Override
            public void run() {
                if (countTemp == 0) {
                    cancel();
                    return;
                }

                // 左から緑色に変化させる部分の長さを決定
                int greenLength = (int) ((count - countTemp) * (line.length() / (double) count));

                // 緑色と灰色のバーを生成
                String greenPart = "&a" + line.substring(0, greenLength);
                String grayPart = "&7" + line.substring(greenLength);

                // ホログラムのラインを更新
                DHAPI.setHologramLine(hologram, 0, greenPart + grayPart);

                countTemp--;
            }
        }.runTaskTimer(MiniGameCmdLib.getPlugin(), 0, 20);
        return new HashMap<BukkitTask, Hologram>() {{
            put(task, hologram);
        }};
    }


}
