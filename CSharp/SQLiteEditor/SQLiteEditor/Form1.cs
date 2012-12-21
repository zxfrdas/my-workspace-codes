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
using System.Collections;

namespace SQLiteEditor {
    public partial class MainForm : Form {

        private SQLiteConnection sqlCon = null;
        private DbDataReader sqlReader = null;

        public MainForm()
        {
            InitializeComponent();
            
        }

        private void onFormLoad(object sender, EventArgs e)
        {
            sqlCon = DataBaseHelper.connectDB("./factory.db");
            sqlCon.Open();
            sqlReader = DataBaseHelper.queryReturnReader(sqlCon, "tbl_name",
                "sqlite_master", "type='table'");
            TreeNode[] tbleNameNodes = new TreeNode[DataBaseHelper.getAllTableName(sqlCon).Length];
            int index = 0;
            while (sqlReader.Read()) {
                tbleNameNodes[index] = new TreeNode(sqlReader.GetString(
                    sqlReader.GetOrdinal("tbl_name")));
                index++;
            }
            mTreeView.TopNode.Text = "factory.db";
            mTreeView.TopNode.Nodes.AddRange(tbleNameNodes);
        }

        private void onFormClose(object sender, FormClosedEventArgs e)
        {
            if (null != sqlReader) {
                sqlReader.Close();
                sqlReader.Dispose();
            }
        }

        private void mTreeView_AfterSelect(object sender, TreeViewEventArgs e)
        {
            bool bIsTableNode = false;
            if (0 != e.Node.Level) {
                bIsTableNode = true;
            }
            if (bIsTableNode) {
                mDataView.DataSource = DataBaseHelper.queryReturnTable(sqlCon, "*", e.Node.Text, null);
            }
        }

        private void dataView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void onRowAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            updateRowHeader(e.RowIndex, e.RowCount);
        }

        private void onRowRemoved(object sender, DataGridViewRowsRemovedEventArgs e)
        {
            updateRowHeader(e.RowIndex, e.RowCount);
        }

        private void updateRowHeader(int rowIndex, int rowCount)
        {
            if (0 == mDataView.RowCount) {
                return;
            }
            for (int i = 0; i < rowCount; i++) {
                mDataView.Rows[rowIndex + i].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleRight;
                mDataView.Rows[rowIndex + i].HeaderCell.Value = (rowIndex + i + 1).ToString();
            }
            for (int i = rowIndex + rowCount; i < mDataView.Rows.Count; i++) {
                mDataView.Rows[i].HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleRight;
                mDataView.Rows[i].HeaderCell.Value = (i + 1).ToString();
            }
        }

    }
}
