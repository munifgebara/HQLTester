package br.com.munif.tools.hqltester;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author munif
 */
public class MunifHibernateTools {

    public static Properties loadDatabaseProperties() {
        Properties properties = new Properties();
        properties.put("javax.persistence.jdbc.driver","com.mysql.jdbc.Driver");
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/security?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=utf8");
        properties.put("javax.persistence.jdbc.user", "root");
        properties.put("javax.persistence.jdbc.password", "senha");
        properties.put("eclipselink.weaving", "false");
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.connection.charSet", "UTF-8");
        properties.put("hibernate.connection.characterEncoding", "UTF-8");
        properties.put("hibernate.connection.useUnicode", "true");
        return properties;
    }

    public static EntityManagerFactory emf() {
        return Persistence.createEntityManagerFactory("HQLTester", loadDatabaseProperties());
    }

}
