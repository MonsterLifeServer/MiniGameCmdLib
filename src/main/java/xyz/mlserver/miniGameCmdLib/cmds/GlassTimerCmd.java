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

public class GlassTimerCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        Component message;
        if (args.length >= 2) {
            try {
                if (args[1].contains("=")) {
                    String key = args[1].split("=")[0];
                    String value = args[1].split("=")[1];
                    if (key.equalsIgnoreCase("player") || key.equalsIgnoreCase("team") || key.equalsIgnoreCase("world")) {

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
