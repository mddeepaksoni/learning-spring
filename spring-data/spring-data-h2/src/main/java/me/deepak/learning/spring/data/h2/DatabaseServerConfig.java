package me.deepak.learning.spring.data.h2;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class DatabaseServerConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource.server")
    public Properties databaseServerProperties() {
        return new Properties();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server databaseTcpServer(@Autowired Properties properties) throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", properties.getPort());
    }

    public static class Properties {
        private String port;

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }
}
