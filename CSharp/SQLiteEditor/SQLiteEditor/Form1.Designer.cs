namespace SQLiteEditor {
    partial class MainForm {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("节点0");
            this.mTreeView = new System.Windows.Forms.TreeView();
            this.mDataView = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.mDataView)).BeginInit();
            this.SuspendLayout();
            // 
            // mTreeView
            // 
            this.mTreeView.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.mTreeView.Location = new System.Drawing.Point(12, 57);
            this.mTreeView.Name = "mTreeView";
            treeNode1.Name = "mBaseNode";
            treeNode1.Text = "节点0";
            this.mTreeView.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode1});
            this.mTreeView.Size = new System.Drawing.Size(164, 263);
            this.mTreeView.TabIndex = 0;
            this.mTreeView.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.mTreeView_AfterSelect);
            // 
            // mDataView
            // 
            this.mDataView.AllowUserToAddRows = false;
            this.mDataView.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.mDataView.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.mDataView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.mDataView.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnEnter;
            this.mDataView.Location = new System.Drawing.Point(191, 57);
            this.mDataView.MultiSelect = false;
            this.mDataView.Name = "mDataView";
            this.mDataView.ReadOnly = true;
            this.mDataView.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.AutoSizeToAllHeaders;
            this.mDataView.RowTemplate.Height = 23;
            this.mDataView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;
            this.mDataView.Size = new System.Drawing.Size(593, 263);
            this.mDataView.TabIndex = 1;
            this.mDataView.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataView_CellContentClick);
            this.mDataView.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.onRowAdded);
            this.mDataView.RowsRemoved += new System.Windows.Forms.DataGridViewRowsRemovedEventHandler(this.onRowRemoved);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(816, 401);
            this.Controls.Add(this.mDataView);
            this.Controls.Add(this.mTreeView);
            this.Name = "MainForm";
            this.Text = "SQLiteEditor";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.onFormClose);
            this.Load += new System.EventHandler(this.onFormLoad);
            ((System.ComponentModel.ISupportInitialize)(this.mDataView)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TreeView mTreeView;
        private System.Windows.Forms.DataGridView mDataView;
    }
}

