package my.app;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.bukkit.configuration.file.FileConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import my.app.Configs.DatabaseConfig;

@Configuration
@EnableJpaRepositories(basePackages = { "my.app.Repositories" })
@EnableTransactionManagement
public class AppConfig {

    FileConfiguration fc = DatabaseConfig.getInstance().getFileConfiguration();
    Main main = Main.getInstance();

    private DataSource getDateSource() {
        boolean isMySQLEnabled = fc.getBoolean("mysql");
        return isMySQLEnabled ? MySQLDataSource() : SQLiteDataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        boolean isMySQLEnabled = fc.getBoolean("mysql");

        factoryBean.setDataSource(getDateSource());
        factoryBean.setPackagesToScan("my.app.*");
        factoryBean.setPersistenceUnitName("MinecraftDB");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "true");
        if (!isMySQLEnabled) {
            properties.setProperty("hibernate.dialect", "my.app.Dialects.SQLDialect");
        }

        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(getDateSource());

        boolean isMySQLEnabled = fc.getBoolean("mysql");

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource(isMySQLEnabled ? "createMySQL.sql" : "createSQLite.sql"));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(true);

        return dataSourceInitializer;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public DataSource MySQLDataSource() {

        String ip = fc.getString("ip");
        String port = Integer.toString(fc.getInt("port"));
        String database = fc.getString("database");
        String username = fc.getString("username");
        String password = fc.getString("password");

        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(String.format("jdbc:mysql://%s:%s/%s", ip, port, database));
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public DataSource SQLiteDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl(String.format("jdbc:sqlite:plugins/%s/db.sqlite", main.getDescription().getName()));
        return dataSource;
    }

}