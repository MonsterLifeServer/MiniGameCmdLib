package xyz.mlserver.miniGameCmdLib.utils;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.HashMap;

public class ProgressBar {

    private static String progressBarText = "||||||||||||||||||||||||||||||";

    private static HashMap<String, Hologram> holograms;

    public static HashMap<String, Hologram> getHolograms() {
        if (holograms == null) {
            holograms = new HashMap<>();
        }
        return holograms;
    }

    public static void removeAllHolograms() {
        for (Hologram hologram : getHolograms().values()) {
            hologram.destroy();
        }
        getHolograms().clear();
    }

    public static void createProgressBar(String name, Location loc) {
        Hologram hologram = DHAPI.createHologram(name, loc, false);
        DHAPI.addHologramLine(hologram, "&7" + progressBarText);
        getHolograms().put(name, hologram);
    }

    /**
     * Update the progress bar
     * @param name The name of the progress bar
     * @param progress progress of the progress bar(0.0-1.0)
     */
    public static void updateProgressBar(String name, double progress) {
        if (progress < 0 || progress > 1) {
            return;
        }
        Hologram hologram = getHolograms().get(name);
        if (hologram == null) {
            return;
        }
        int length = progressBarText.length();
        int progressLength = (int) (length * progress);
        String text = "&a" + progressBarText.substring(0, progressLength) + "&7" + progressBarText.substring(progressLength);
        DHAPI.setHologramLine(hologram, 0, text);
    }

}
