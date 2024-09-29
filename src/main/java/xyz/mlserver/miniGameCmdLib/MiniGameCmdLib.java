package xyz.mlserver.miniGameCmdLib;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.mlserver.miniGameCmdLib.cmds.MiniGameCmdLibCommand;

public final class MiniGameCmdLib extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("mgcl").setExecutor(new MiniGameCmdLibCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
