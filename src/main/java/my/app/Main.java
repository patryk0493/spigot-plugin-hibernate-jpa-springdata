package my.app;

import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import my.app.Commands.TestCommandExecutor;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static AnnotationConfigApplicationContext appContext;

    @Override
    public void onEnable() {
        instance = this;

        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        appContext = new AnnotationConfigApplicationContext();
        appContext.scan("my.app");
        appContext.refresh();

        getCommand("test").setExecutor(new TestCommandExecutor());

    }

    @Override
    public void onDisable() {
        appContext.close();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public static Main getInstance() {
        return instance;
    }

    public AnnotationConfigApplicationContext getAppContext() {
        return appContext;
    }

}