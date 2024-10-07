package xyz.mlserver.miniGameCmdLib.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.mlserver.mc.util.Color;

public class BroadcastCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        Component message;
        String msg = "";
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                msg += args[i] + " ";
            }
            msg = Color.replaceColorCode(msg);
            sender.getServer().broadcastMessage(msg);
            return true;
        } else {
            message = Component.text()
                    .append(Component.text("Usage: /broadcast <message>").color(NamedTextColor.GRAY))
                    .build();
            sender.sendMessage(message);
            return true;
        }
    }

}
