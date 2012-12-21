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
        private const int TABLE_LEVEL = 1;
        private SQLiteConnection sqlCon = null;
        private DbDataReader sqlReader = null;

        public MainForm()
        {
            InitializeComponent();
            
        }

        private void onFormLoad(object sender, EventArgs e)
        {
            sqlCon = DataBaseHelper.connectDB("D:/factory.db");
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
            if (null != sqlCon) {
                sqlCon.Close();
                sqlCon.Dispose();
            }
        }

        private void mTreeView_AfterSelect(object sender, TreeViewEventArgs e)
        {
            if (TABLE_LEVEL == e.Node.Level) {
                mDataView.DataSource = DataBaseHelper.queryReturnTable(sqlCon, "*",
                    e.Node.Text, null);
            }
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

        private void onClick(object sender, EventArgs e)
        {
            if (!"".Equals(mDataKey.Text) && !"".Equals(mDataValue.Text)) {
                Dictionary<String, String> values = new Dictionary<String, String>();
                values.Add(mDataKey.Text, mDataValue.Text);
                DataBaseHelper.update(sqlCon, mTreeView.SelectedNode.Text, values, mDataWhere.Text);
                mDataView.DataSource = DataBaseHelper.queryReturnTable(sqlCon,
                    "*", mTreeView.SelectedNode.Text, null);
            }
        }

    }
}
