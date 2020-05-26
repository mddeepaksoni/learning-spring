package me.deepak.learning.spring.data.jpa.properties;

public class DataSourceProperties {

    private String jdbcUrl;
    private String driverClassName;
    private String username;
    private String password;
    private HibernateProperties hibernate;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HibernateProperties getHibernate() {
        return hibernate;
    }

    public void setHibernate(HibernateProperties hibernate) {
        this.hibernate = hibernate;
    }

    public static class HibernateProperties {
        private String dialect;
        private String[] packages;

        private Integer batchSize;
        private Boolean orderInserts;
        private Boolean orderUpdates;

        private Boolean showSql;
        private Boolean formatSql;
        private Boolean generateStatistics;

        public String getDialect() {
            return dialect;
        }

        public void setDialect(String dialect) {
            this.dialect = dialect;
        }

        public String[] getPackages() {
            return packages;
        }

        public void setPackages(String[] packages) {
            this.packages = packages;
        }

        public Integer getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(Integer batchSize) {
            this.batchSize = batchSize;
        }

        public Boolean getOrderInserts() {
            return orderInserts;
        }

        public void setOrderInserts(Boolean orderInserts) {
            this.orderInserts = orderInserts;
        }

        public Boolean getOrderUpdates() {
            return orderUpdates;
        }

        public void setOrderUpdates(Boolean orderUpdates) {
            this.orderUpdates = orderUpdates;
        }

        public Boolean getShowSql() {
            return showSql;
        }

        public void setShowSql(Boolean showSql) {
            this.showSql = showSql;
        }

        public Boolean getFormatSql() {
            return formatSql;
        }

        public void setFormatSql(Boolean formatSql) {
            this.formatSql = formatSql;
        }

        public Boolean getGenerateStatistics() {
            return generateStatistics;
        }

        public void setGenerateStatistics(Boolean generateStatistics) {
            this.generateStatistics = generateStatistics;
        }
    }
}
