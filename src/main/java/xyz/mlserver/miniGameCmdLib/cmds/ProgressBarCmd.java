package xyz.mlserver.miniGameCmdLib.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.mlserver.miniGameCmdLib.utils.GlassTimer;
import xyz.mlserver.miniGameCmdLib.utils.HoloTimer;
import xyz.mlserver.miniGameCmdLib.utils.ProgressBar;

public class ProgressBarCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        // /progressbar create <name> <x> <y> <z> [<world>]
        // /progressbar update <name> <progress> ... progress: 0.0-1.0
        Component message;
        if (args.length < 2) {
            message = Component.text("Usage: /progressbar create <name> <x> <y> <z> [<world>]", NamedTextColor.RED);
            sender.sendMessage(message);
            message = Component.text("Usage: /progressbar update <name> <0.0-1.0>", NamedTextColor.RED);
            sender.sendMessage(message);
            return false;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 5) {
                message = Component.text("Usage: /progressbar create <name> <x> <y> <z> [<world>]", NamedTextColor.RED);
                sender.sendMessage(message);
                return false;
            }
            String name = args[1];
            double x = Double.parseDouble(args[2]);
            double y = Double.parseDouble(args[3]);
            double z = Double.parseDouble(args[4]);
            World world = null;
            if (args.length == 6) {
                world = Bukkit.getWorld(args[5]);
            } else {
                if (sender instanceof Player) {
                    world = ((Player) sender).getWorld();
                } else if (sender instanceof CommandBlock) {
                    world = ((CommandBlock) sender).getWorld();
                }
            }
            Location loc;
            if (world != null) {
                loc = new Location(world, x, y, z);
                ProgressBar.createProgressBar(name, loc);
            } else {
                message = Component.text("World not found", NamedTextColor.RED);
                sender.sendMessage(message);
                return false;
            }
        } else if (args[0].equalsIgnoreCase("update")) {
            if (args.length < 3) {
                message = Component.text("Usage: /progressbar update <name> <progress>", NamedTextColor.RED);
                sender.sendMessage(message);
                return false;
            }
            String name = args[1];
            try {
                double progress = Double.parseDouble(args[2]);
                ProgressBar.updateProgressBar(name, progress);
            } catch (NumberFormatException e) {
                message = Component.text("Invalid progress", NamedTextColor.RED);
                sender.sendMessage(message);
                return false;
            }
        } else {
            message = Component.text("Usage: /progressbar create <name> <x> <y> <z> [<world>]", NamedTextColor.RED);
            sender.sendMessage(message);
            message = Component.text("Usage: /progressbar update <name> <0.0-1.0>", NamedTextColor.RED);
            sender.sendMessage(message);
            return false;
        }
        return true;
    }

}
