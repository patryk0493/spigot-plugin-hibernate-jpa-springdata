package my.app.Configs;

public class DatabaseConfig extends AbstractConfig {

    private static DatabaseConfig instance = null;

    public static DatabaseConfig getInstance() {
        if (instance == null)
            instance = new DatabaseConfig();

        return instance;
    }

    @Override
    public String getFileName() {
        return "database.yml";
    }

}