package xyz.mlserver.miniGameCmdLib.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetBlockCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        Component message;
        if (args.length >= 4) {
            try {
                String worldName;
                if (sender instanceof Player) {
                    worldName = ((Player) sender).getWorld().getName();
                } else if (sender instanceof CommandBlock) {
                    worldName = ((CommandBlock) sender).getWorld().getName();
                } else {
                    if (args.length < 5) {
                        message = Component.text()
                                .append(Component.text("Usage: /set_block <Material> <x> <y> <z> <world>").color(NamedTextColor.GRAY))
                                .build();
                        sender.sendMessage(message);
                        return true;
                    }
                    worldName = args[4];
                }
                Material material = Material.matchMaterial(args[1]);
                if (material == null) {
                    message = Component.text()
                            .append(Component.text("Invalid material! ").color(NamedTextColor.RED))
                            .build();
                    sender.sendMessage(message);
                    message = Component.text()
                            .append(Component.text("Usage: /set_block <Material> <x> <y> <z> [<world>]").color(NamedTextColor.GRAY))
                            .build();
                    sender.sendMessage(message);
                    return true;
                }
                Location loc = new Location(sender.getServer().getWorld(worldName), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                loc.getBlock().setType(material);
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
