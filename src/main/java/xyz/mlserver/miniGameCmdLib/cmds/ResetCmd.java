package xyz.mlserver.miniGameCmdLib.cmds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.mlserver.miniGameCmdLib.utils.GlassTimer;
import xyz.mlserver.miniGameCmdLib.utils.HoloTimer;
import xyz.mlserver.miniGameCmdLib.utils.ProgressBar;

public class ResetCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, @NotNull String[] args) {
        GlassTimer.removeAllTasks();
        HoloTimer.removeAllTasks();
        ProgressBar.removeAllHolograms();
        return true;
    }

}
