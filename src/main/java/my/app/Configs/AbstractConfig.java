package my.app.Configs;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import my.app.Main;

public abstract class AbstractConfig {

    protected File file;
    protected FileConfiguration fileConfiguration;
    protected Main main;

    protected AbstractConfig() {
        main = Main.getInstance();
        file = new File(main.getDataFolder(), getFileName());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            main.saveResource(getFileName(), false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public String getFileName() {
        return "xxx.yml";
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

}