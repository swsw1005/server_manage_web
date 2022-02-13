package sw.im.swim.bean.enums;

import lombok.Getter;

public enum DbType {
    POSTGRESQL(
            "postgres",
            "org.postgresql.Driver",
            "jdbc:postgresql://%s:%s/%s",
            "SELECT NOW()",
            "psql -h %s -p %s -U %s -d %s -w  -c '\\l' | grep UTF-8",
            "postgres|template0|template1",
            "pg_dump -h %s -p %s -U %s -d %s -E utf-8 --inserts  > %s"
    ),
    MYSQL(
            "mysql",
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8",
            "SELECT 1",
            "mysql -h%s -P %s -u%s -p%s %s -e \"show databases\" | grep \"\\\\|\"",
            "Database|information_schema|mysql|performance_schema|sys",
            "mysqldump -h%s -P %s -u%s -p%s %s > %s"
    ),
    MYSQL8(
            "mysql",
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8",
            "SELECT 1",
            "mysql -h%s -P %s -u%s -p%s %s -e \"show databases\" | grep \"\\\\|\"",
            "Database|information_schema|mysql|performance_schema|sys",
            "mysqldump -h%s -P %s -u%s -p%s %s > %s"
    ),
    MARIADB(
            "mysql",
            "org.mariadb.jdbc.Driver",
            "jdbc:mariadb://%s:%s/%s",
            "SELECT 1",
            "mysql -h%s -P %s -u%s -p%s %s -e \"show databases\" | grep \"\\\\|\"",
            "Database|information_schema|mysql|performance_schema|sys",
            "mysqldump -h%s -P %s -u%s -p%s %s > %s"
    ),
    ;

    final String ROOT_DB_NAME;
    final String JDBC_DRIVER_NAME;
    final String JDBC_URL;
    final String CHECK_QUERY;
    final String DB_LIST_COMMAND;
    final String DB_LIST_EXCLUDE;
    final String DUMP_COMMAND;

    DbType(
            final String ROOT_DB_NAME,
            final String JDBC_DRIVER_NAME,
            final String JDBC_URL,
            final String CHECK_QUERY,
            final String DB_LIST_COMMAND,
            final String DB_LIST_EXCLUDE,
            final String DUMP_COMMAND
    ) {
        this.ROOT_DB_NAME = ROOT_DB_NAME;
        this.JDBC_DRIVER_NAME = JDBC_DRIVER_NAME;
        this.JDBC_URL = JDBC_URL;
        this.CHECK_QUERY = CHECK_QUERY;
        this.DB_LIST_COMMAND = DB_LIST_COMMAND;
        this.DB_LIST_EXCLUDE = DB_LIST_EXCLUDE;
        this.DUMP_COMMAND = DUMP_COMMAND;
    }


}