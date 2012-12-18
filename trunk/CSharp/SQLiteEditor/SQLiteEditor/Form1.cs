using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SQLite;
using System.Data.Common;

namespace SQLiteEditor {
    public partial class Form1 : Form {
        public Form1()
        {
            InitializeComponent();
            SQLiteConnection conn = (SQLiteConnection)new SQLiteFactory().CreateConnection();
            conn.ConnectionString = @"Data Source=./factory.db";
            conn.Open();
            DbCommand sqlCommand = new SQLiteCommand("SELECT tbl_name FROM sqlite_master" +
                " WHERE type='table' order by name;", conn);
            DbDataReader sqlReader = sqlCommand.ExecuteReader();
            while (sqlReader.Read()) {
                Console.WriteLine("table name = " + sqlReader.GetName(0));
            }
        }
    }
}
