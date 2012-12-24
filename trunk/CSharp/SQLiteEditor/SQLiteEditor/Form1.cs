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
        private const int MENU_FILE = 0;
        private const int MENU_OPEN = 1;
        private const int MENU_HELP = 2;
        private const int MENU_ABOUT = 3;
        private String mDBPath = "";
        private SQLiteConnection mSqlCon = null;
        private DbDataReader mSqlReader = null;

        public MainForm()
        {
            InitializeComponent();
            
        }

        private void onFormLoad(object sender, EventArgs e){}

        private void onFormClose(object sender, FormClosedEventArgs e)
        {
            if (null != mSqlReader) {
                mSqlReader.Close();
                mSqlReader.Dispose();
            }
            if (null != mSqlCon) {
                mSqlCon.Close();
                mSqlCon.Dispose();
            }
        }

        private void mTreeView_AfterSelect(object sender, TreeViewEventArgs e)
        {
            if (TABLE_LEVEL == e.Node.Level) {
                DataTable table = DataBaseHelper.queryReturnTable(mSqlCon, "*",
                    e.Node.Text, null);
                String[] names = new String[table.Columns.Count];
                int index = 0;
                mDataView.DataSource = table;
                foreach (DataColumn dc in table.Columns) {
                    names[index] = dc.ColumnName;
                    index++;
                }
                foreach (Control c in mKVBox.Controls) {
                    if (c is ComboBox) {
                        ((ComboBox)c).DataSource = names.ToList();
                    }
                }
                foreach (Control c in mWhereBox.Controls) {
                    if (c is ComboBox) {
                        ((ComboBox)c).DataSource = names.ToList();
                    }
                }
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
            if (sender is Button) {
                if (!"".Equals(mDataKeyBox.Text) && !"".Equals(mDataValue.Text)) {
                    Dictionary<String, String> values = new Dictionary<String, String>();
                    String[] key = new String[mKVBox.Controls.Count / 2];
                    int iKey = 0;
                    String[] value = new String[mKVBox.Controls.Count / 2];
                    int iValue = 0;
                    foreach (Control c in mKVBox.Controls) {
                        if (c is ComboBox) {
                            key[iKey] = c.Text;
                            iKey++;
                        } else if (c is TextBox) {
                            value[iValue] = c.Text;
                            iValue++;
                        }
                    }
                    for (int i = 0; i < mKVBox.Controls.Count / 2; i++) {
                        values.Add(key[i], value[i]);
                    }
                    DataBaseHelper.update(mSqlCon, mTreeView.SelectedNode.Text, values,
                        mDataWhereBox.Text + mWhereArg.Text);
                    mDataView.DataSource = DataBaseHelper.queryReturnTable(mSqlCon,
                        "*", mTreeView.SelectedNode.Text, null);
                }
            } else if (sender is ToolStripMenuItem) {
                Console.WriteLine("sender is ToolStripMenuItem");
                ToolStripMenuItem item = sender as ToolStripMenuItem;
                if (null != item) {
                    switch (item.MergeIndex) {
                    case MENU_OPEN:
                        openFileDialog1.ShowDialog();
                        break;

                    case MENU_ABOUT:
                        MessageBox.Show(Properties.Resources.aboutContent1 + System.Environment.
                            NewLine + Properties.Resources.aboutContent2 + System.Environment.
                            NewLine, Properties.Resources.aboutTitle);
                        break;

                    default:
                        break;
                    }
                }
            }
        }

        private void onFileOk(object sender, CancelEventArgs e)
        {
            OpenFileDialog dialog = sender as OpenFileDialog;
            if (null != dialog) {
                mDBPath = dialog.FileName;
                mSqlCon = DataBaseHelper.connectDB(mDBPath);
                mSqlCon.Open();
                mSqlReader = DataBaseHelper.queryReturnReader(mSqlCon, "tbl_name",
                    "sqlite_master", "type='table'");
                String[] tableNames = DataBaseHelper.getAllTableName(mSqlCon);
                TreeNode[] tableNameNodes = new TreeNode[tableNames.Length];
                int index = 0;
                while (mSqlReader.Read()) {
                    tableNameNodes[index] = new TreeNode(mSqlReader.GetString(
                        mSqlReader.GetOrdinal("tbl_name")));
                    index++;
                }
                if (null != mTreeView.TopNode.Nodes && 0 != mTreeView.TopNode.Nodes.Count) {
                    mTreeView.TopNode.Nodes.Clear();
                }
                mTreeView.TopNode.Text = mDBPath.Substring(mDBPath.LastIndexOf(@"\") + 1);
                mTreeView.TopNode.Nodes.AddRange(tableNameNodes);
            }
        }

    }
}
