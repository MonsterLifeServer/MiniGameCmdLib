package xyz.mlserver.miniGameCmdLib.utils;

import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.mlserver.miniGameCmdLib.MiniGameCmdLib;

import java.util.HashMap;
import java.util.List;
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

    public static BukkitTask startTask(List<Player> players, int count) {
        // countは5以下もしくは50,40,30,20,10のいずれか
        if (!(count <= 5) && (count != 50 && count != 40 && count != 30 && count != 20 && count != 10)) {
            return null;
        }
        int countTemp = count;
        int stackCount = 1;
        if (countTemp > 5) {
            countTemp = countTemp / 10;
            stackCount = 10;
        }
        for (int slot = 8; slot > 3; slot--) {
            if (countTemp == 0) break;
            for (Player player : players) {
                player.getInventory().setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE, stackCount));
            }
            countTemp--;
        }
        boolean isTen = count > 5;
        int countI;
        if (isTen) {
            countI = count / 10;
        } else {
            countI = count;
        }
        return new BukkitRunnable() {
            int i = countI;
            int tenCount = 10;
            int slot = 9 - i;
            @Override
            public void run() {
                if (i <= -1) {
                    for (int slot = 8; slot > 3; slot--) {
                        for (Player player : players) {
                            player.getInventory().setItem(slot, new ItemStack(Material.AIR));
                        }
                    }
                    cancel();
                    return;
                }
                int stackCount = 1;
                if (isTen) {
                    stackCount = 10;
                }
                if (tenCount == 0) {
                    for (Player player : players) {
                        player.getInventory().setItem(slot, new ItemStack(Material.GREEN_STAINED_GLASS_PANE, stackCount));
                    }
                    slot++;
                }
                if (isTen) {
                    if (tenCount == 0) {
                        tenCount = 10;
                        i--;
                    }
                    tenCount--;
                    if (i == 0) i = -1;
                } else {
                    i--;
                    tenCount = 0;
                }
            }
        }.runTaskTimer(MiniGameCmdLib.getPlugin(), 0, 20);
    }

    public static BukkitTask startTask(Player player, int count) {
        return startTask(List.of(player), count);
    }

}
