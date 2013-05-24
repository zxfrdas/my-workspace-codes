using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;
using System.IO;
using System.Data.Common;
using System.Data;
using System.Data.SqlClient;

namespace SQLiteEditor {
    class DataBaseHelper {
        private const String SELECT = "SELECT ";
        private const String UPDATE = "UPDATE ";
        private const String FROM = " FROM ";
        private const String WHERE = " WHERE ";
        private const String SET = " SET ";
        private const String AND = " AND ";
        private const String EQUAL = " = ";
        private const String COMMA = ",";

        public static SQLiteConnection connectDB(String filePath)
        {
            if (File.Exists(filePath)) {
                return new SQLiteConnection(@"Data Source=" + filePath);
            }
            throw new FileNotFoundException(filePath);
        }

        public static String[] getAllTableName(SQLiteConnection con)
        {
            String[] tableNames = new String[getRowCount(con, "sqlite_master")];
            DbDataReader reader = queryReturnReader(con, "name", "sqlite_master", "type='table'");
            int index = 0;
            while (reader.Read()) {
                tableNames[index] = reader.GetString(reader.GetOrdinal("name"));
                Console.WriteLine("table name = " + tableNames[index]);
                index++;
            }
            return tableNames;
        }

        public static DbDataReader queryReturnReader(SQLiteConnection con,
            String name, String tableName, String where)
        {
            return getQueryCommand(con, name, tableName, where).
                ExecuteReader(CommandBehavior.CloseConnection);
        }

        public static DataTable queryReturnTable(SQLiteConnection con,
            String name, String tableName, String where)
        {
            SQLiteDataAdapter adapter = new SQLiteDataAdapter(getQueryCommand(con, name,
                tableName, where));
            DataTable dataTable = new DataTable();
            adapter.Fill(dataTable);
            return dataTable;
        }

        private static SQLiteCommand getQueryCommand(SQLiteConnection con,
            String name, String tableName, String where)
        {
            if (ConnectionState.Closed == con.State) {
                throw new Exception("SQLiteConnection not open");
            }
            StringBuilder sb = new StringBuilder();
            if (null == name || "".Equals(name)) {
                name = "*";
            }
            sb.Append(SELECT + name);
            if (null == tableName || "".Equals(tableName)) {
                throw new ArgumentNullException("tableName should not be null");
            }
            sb.Append(FROM + tableName);
            if (null != where && !"".Equals(where)) {
                sb.Append(WHERE + where);
            }
            SQLiteCommand command = con.CreateCommand();
            command.CommandText = sb.ToString();
            return command;
        }

        public static void update(SQLiteConnection con, String tableName,
            Dictionary<String, String> values, String where)
        {
            StringBuilder sb = new StringBuilder();
            foreach (var value in values) {
                if (value.Key == null) {
                    continue;
                }
                sb.Append(value.Key).Append(EQUAL).Append(value.Value).Append(COMMA);
            }
            sb.Remove(sb.ToString().LastIndexOf(COMMA), 1);
            getUpdateCommand(con, sb.ToString(), tableName, where).ExecuteNonQuery();
        }

        private static SQLiteCommand getUpdateCommand(SQLiteConnection con,
            String name, String tableName, String where)
        {
            if (ConnectionState.Closed == con.State) {
                throw new Exception("SQLiteConnection not open");
            }
            StringBuilder sb = new StringBuilder();
            if (null == tableName || "".Equals(tableName)) {
                throw new ArgumentNullException("tableName should not be null");
            }
            sb.Append(UPDATE + tableName);
            if (null == name || "".Equals(name)) {
                name = "*";
            }
            sb.Append(SET + name);
            if (null != where && !"".Equals(where)) {
                sb.Append(WHERE + where);
            }
            SQLiteCommand command = con.CreateCommand();
            command.CommandText = sb.ToString();
            return command;
        }

        public static int getRowCount(SQLiteConnection con, String tableName)
        {
            DbDataReader reader = queryReturnReader(con, "Count(*)", "sqlite_master", "type='table'");
            if (reader.Read()) {
                Console.WriteLine("table row count = " + reader.GetValue(0));
                return reader.GetInt32(0);
            }
            throw new Exception("result is null");
        }

    }
}
