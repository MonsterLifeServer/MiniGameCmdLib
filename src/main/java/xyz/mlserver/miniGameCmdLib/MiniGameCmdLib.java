package xyz.mlserver.miniGameCmdLib;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.mlserver.mc.util.CustomConfiguration;
import xyz.mlserver.miniGameCmdLib.cmds.BroadcastCmd;
import xyz.mlserver.miniGameCmdLib.cmds.FillCmd;
import xyz.mlserver.miniGameCmdLib.cmds.GlassTimerCmd;
import xyz.mlserver.miniGameCmdLib.cmds.SetBlockCmd;

import java.util.Objects;

public final class MiniGameCmdLib extends JavaPlugin {

    public static CustomConfiguration config;

    private static String commandWorld = null;

    public static String getCommandWorld() {
        return commandWorld;
    }

    @Override
    public void onEnable() {
        config = new CustomConfiguration(this, "config.yml");
        config.saveDefaultConfig();

        if (config.getConfig().get("command-world") != null) commandWorld = config.getConfig().getString("command-world");

        Objects.requireNonNull(getCommand("set_block")).setExecutor(new SetBlockCmd());
        Objects.requireNonNull(getCommand("fill2")).setExecutor(new FillCmd());
        Objects.requireNonNull(getCommand("glass-timer")).setExecutor(new GlassTimerCmd());
        Objects.requireNonNull(getCommand("broadcast")).setExecutor(new BroadcastCmd());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
