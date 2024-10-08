package xyz.mlserver.miniGameCmdLib.cmds;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import xyz.mlserver.miniGameCmdLib.utils.GlassTimer;
import xyz.mlserver.miniGameCmdLib.utils.HoloTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HologramTimerCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        Component message;
        // /hologram_timer <秒数> x y z world
        // worldは省略可能
        if (args.length >= 4) {
            int time;
            try {
                time = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                time = -1;
            }
            if (time == -1) {
                message = Component.text()
                        .append(Component.text("Invalid number format! ").color(NamedTextColor.RED))
                        .build();
                sender.sendMessage(message);
                message = Component.text()
                        .append(Component.text("Usage: /hologram_timer <秒数> <x> <y> <z> [<world>]").color(NamedTextColor.GRAY))
                        .build();
                sender.sendMessage(message);
                return true;
            }
            try {
                String worldName;
                if (args.length >= 5) {
                    worldName = args[4];
                } else if (sender instanceof Player) {
                    worldName = ((Player) sender).getWorld().getName();
                } else if (sender instanceof World) {
                    worldName = ((World) sender).getName();
                } else if (sender instanceof CommandBlock) {
                    worldName = ((CommandBlock) sender).getWorld().getName();
                } else {
                    worldName = "world";
                }
                World world = sender.getServer().getWorld(worldName);
                if (world == null) {
                    message = Component.text()
                            .append(Component.text("World not found! ").color(NamedTextColor.RED))
                            .build();
                    sender.sendMessage(message);
                    return true;
                }
                Location loc = new Location(world, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                HashMap<BukkitTask, Hologram> map = HoloTimer.startTask(loc, Integer.parseInt(args[0]));
                if (map == null) {
                    message = Component.text()
                            .append(Component.text("Hologram already exists! ").color(NamedTextColor.RED))
                            .build();
                    sender.sendMessage(message);
                    return true;
                }
                HoloTimer.addTimer(map.keySet().iterator().next(), map.values().iterator().next());
            } catch (NumberFormatException e) {
                message = Component.text()
                        .append(Component.text("Invalid number format! ").color(NamedTextColor.RED))
                        .build();
                sender.sendMessage(message);
                message = Component.text()
                        .append(Component.text("Usage: /hologram_timer <秒数> <x> <y> <z> [<world>]").color(NamedTextColor.GRAY))
                        .build();
                sender.sendMessage(message);
            }
            return true;
        } else {
            message = Component.text()
                    .append(Component.text("Usage: /hologram_timer <秒数> <x> <y> <z> [<world>]").color(NamedTextColor.GRAY))
                    .build();
            sender.sendMessage(message);
            return true;
        }
    }

}
