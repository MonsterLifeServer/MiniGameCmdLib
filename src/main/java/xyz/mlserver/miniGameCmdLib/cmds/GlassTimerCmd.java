package xyz.mlserver.miniGameCmdLib.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.mlserver.miniGameCmdLib.utils.GlassTimer;

import java.util.ArrayList;
import java.util.List;

public class GlassTimerCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        Component message;
        if (args.length >= 2) {
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
                        .append(Component.text("Usage: /glass_timer <秒数> <player=playerName|team=teamName|world=world>").color(NamedTextColor.GRAY))
                        .build();
                sender.sendMessage(message);
                return true;
            }
            try {
                if (args[1].contains("=")) {
                    String key = args[1].split("=")[0];
                    String value = args[1].split("=")[1];
                    if (key.equalsIgnoreCase("player") || key.equalsIgnoreCase("team") || key.equalsIgnoreCase("world")) {
                        if (key.equalsIgnoreCase("player")) {
                            Player player = sender.getServer().getPlayer(value);
                            if (player != null) {
                                GlassTimer.addTask(GlassTimer.startTask(player, Integer.parseInt(args[0])));
                            } else {
                                message = Component.text()
                                        .append(Component.text("Player not found! ").color(NamedTextColor.RED))
                                        .build();
                                sender.sendMessage(message);
                            }
                        } else if (key.equalsIgnoreCase("team")) {
                            List<Player> players = new ArrayList<>();
                            for (Player all : sender.getServer().getOnlinePlayers()) {
                                if (all.getScoreboard().getEntryTeam(all.getName()) == null) continue;
                                if (all.getScoreboard().getEntryTeam(all.getName()).getName().equalsIgnoreCase(value)) {
                                    players.add(all);
                                }
                            }
                            GlassTimer.addTask(GlassTimer.startTask(players, Integer.parseInt(args[0])));
                        } else if (key.equalsIgnoreCase("world")) {
                            World world = sender.getServer().getWorld(value);
                            if (world != null) {
                                List<Player> players = world.getPlayers();
                                GlassTimer.addTask(GlassTimer.startTask(players, Integer.parseInt(args[0])));
                            } else {
                                message = Component.text()
                                        .append(Component.text("World not found! ").color(NamedTextColor.RED))
                                        .build();
                                sender.sendMessage(message);
                            }
                        }
                    } else {
                        message = Component.text()
                                .append(Component.text("Usage: /glass_timer <秒数> <player=playerName|team=teamName|world=world>").color(NamedTextColor.GRAY))
                                .build();
                        sender.sendMessage(message);
                    }
                } else {
                    message = Component.text()
                            .append(Component.text("Usage: /glass_timer <秒数> <player=playerName|team=teamName|world=world>").color(NamedTextColor.GRAY))
                            .build();
                    sender.sendMessage(message);
                }
            } catch (NumberFormatException e) {
                message = Component.text()
                        .append(Component.text("Invalid number format! ").color(NamedTextColor.RED))
                        .build();
                sender.sendMessage(message);
                message = Component.text()
                        .append(Component.text("Usage: /set_block <Material> <x> <y> <z> [<world>]").color(NamedTextColor.GRAY))
                        .build();
                sender.sendMessage(message);
            }
            return true;
        }
        return false;
    }

}
