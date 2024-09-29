package xyz.mlserver.miniGameCmdLib.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MiniGameCmdLibCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        Component message;
        if (args.length == 0) {
            sender.sendMessage("Hello, MiniGameCmdLib!");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "set_redstone_block":
                if (args.length >= 5) {
                    try {
                        Location loc = new Location(sender.getServer().getWorld(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
                        loc.getBlock().setType(org.bukkit.Material.REDSTONE_BLOCK);
                    } catch (NumberFormatException e) {
                        message = Component.text()
                                .append(Component.text("Invalid number format! ").color(NamedTextColor.RED))
                                .build();
                        sender.sendMessage(message);
                        message = Component.text()
                                .append(Component.text("Usage: /mgcl set_redstone_block <world> <x> <y> <z>").color(NamedTextColor.GRAY))
                                .build();
                        sender.sendMessage(message);
                    }
                    return true;
                }
        }
        return false;
    }

}
