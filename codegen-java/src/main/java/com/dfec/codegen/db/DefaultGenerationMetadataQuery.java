package com.dfec.codegen.db;

import com.dfec.codegen.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author zhangth
 * @since 2026/1/10
 */
public class DefaultGenerationMetadataQuery implements GenerationMetadataQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGenerationMetadataQuery.class);

    private DatabaseConfig config;

    public  DefaultGenerationMetadataQuery(DatabaseConfig config) {
        this.config = config;
    }

    @Override
    public GenerationMetadata query() {

        try (Connection connection = getConnection()){
            GenerationMetadata result = new GenerationMetadata();
            DatabaseMetaData metadata = connection.getMetaData();
            result.setMetadata(metadata);
            result.setTables(getTables(connection));
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(config.getJdbcUrl(), config.getUsername(), config.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Table> getTables(Connection connection) {
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            ResultSet rs = metadata.getTables(catalog, schema, "%", null);

            List<Table> tables = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("TABLE_NAME");
                String type = rs.getString("TABLE_TYPE");
                String remark = rs.getString("REMARKS");

                Table table = new Table();
                table.setName(name);
                table.setType(type);
                table.setRemark(remark);
                List<TableColumn> columns = getTableColumns(connection, table);
                table.setColumns(columns);
                tables.add(table);
            }

            return tables;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<TableColumn> getTableColumns(Connection connection, Table table) {
        try {
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            DatabaseMetaData metadata = connection.getMetaData();
            List<TableColumn> columns = new LinkedList<>();

            List<String> primaryKeys = new LinkedList<>();
            ResultSet prs = metadata.getPrimaryKeys(catalog, schema, table.getName());
            while (prs.next()) {
                String primaryKey = prs.getString("COLUMN_NAME");
                primaryKeys.add(primaryKey);
            }

            if (primaryKeys.isEmpty()) {
                LOGGER.warn("table[{}] not found primary key", table.getName());
            }

            ResultSet rs = metadata.getColumns(catalog, schema, table.getName(), null);
            while (rs.next()) {
                TableColumn column = new TableColumn();
                String name = rs.getString("COLUMN_NAME");
                column.setName(name);
                int dataType = rs.getInt("DATA_TYPE");
                column.setDataType(dataType);
                column.setTypeName(rs.getString("TYPE_NAME"));
                column.setLength(rs.getInt("COLUMN_SIZE"));
                column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
                column.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX"));
                column.setNullable(rs.getInt("NULLABLE"));
                column.setRemark(rs.getString("REMARKS"));
                column.setColumnDef(rs.getString("COLUMN_DEF"));
                column.setAutoIncrement(rs.getString("IS_AUTOINCREMENT"));
                column.setGeneratedColumn(rs.getString("IS_GENERATEDCOLUMN"));

                if (primaryKeys.contains(name)) {
                    column.setPrimaryKey(true);
                }
                columns.add(column);
            }
            return columns;
        } catch (Exception e) {
            throw new  RuntimeException(e);
        }

    }
}
