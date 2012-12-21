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
            System.Windows.Forms.TreeNode treeNode3 = new System.Windows.Forms.TreeNode("节点0");
            this.mTreeView = new System.Windows.Forms.TreeView();
            this.mDataView = new System.Windows.Forms.DataGridView();
            this.mDataKey = new System.Windows.Forms.TextBox();
            this.mUpdateBtn = new System.Windows.Forms.Button();
            this.mDataValue = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.mDataWhere = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.mDataView)).BeginInit();
            this.SuspendLayout();
            // 
            // mTreeView
            // 
            this.mTreeView.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.mTreeView.Location = new System.Drawing.Point(12, 103);
            this.mTreeView.Name = "mTreeView";
            treeNode3.Name = "mBaseNode";
            treeNode3.Text = "节点0";
            this.mTreeView.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode3});
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
            this.mDataView.Location = new System.Drawing.Point(193, 103);
            this.mDataView.MultiSelect = false;
            this.mDataView.Name = "mDataView";
            this.mDataView.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.AutoSizeToAllHeaders;
            this.mDataView.RowTemplate.Height = 23;
            this.mDataView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;
            this.mDataView.Size = new System.Drawing.Size(593, 263);
            this.mDataView.TabIndex = 1;
            this.mDataView.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.onRowAdded);
            this.mDataView.RowsRemoved += new System.Windows.Forms.DataGridViewRowsRemovedEventHandler(this.onRowRemoved);
            // 
            // mDataKey
            // 
            this.mDataKey.Location = new System.Drawing.Point(193, 54);
            this.mDataKey.Name = "mDataKey";
            this.mDataKey.Size = new System.Drawing.Size(100, 21);
            this.mDataKey.TabIndex = 2;
            // 
            // mUpdateBtn
            // 
            this.mUpdateBtn.Location = new System.Drawing.Point(682, 52);
            this.mUpdateBtn.Name = "mUpdateBtn";
            this.mUpdateBtn.Size = new System.Drawing.Size(75, 23);
            this.mUpdateBtn.TabIndex = 5;
            this.mUpdateBtn.Text = "批量更新";
            this.mUpdateBtn.UseVisualStyleBackColor = true;
            this.mUpdateBtn.Click += new System.EventHandler(this.onClick);
            // 
            // mDataValue
            // 
            this.mDataValue.Location = new System.Drawing.Point(330, 54);
            this.mDataValue.Name = "mDataValue";
            this.mDataValue.Size = new System.Drawing.Size(108, 21);
            this.mDataValue.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(193, 36);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(23, 12);
            this.label1.TabIndex = 5;
            this.label1.Text = "key";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(330, 35);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(35, 12);
            this.label2.TabIndex = 6;
            this.label2.Text = "value";
            // 
            // mDataWhere
            // 
            this.mDataWhere.Location = new System.Drawing.Point(465, 54);
            this.mDataWhere.Name = "mDataWhere";
            this.mDataWhere.Size = new System.Drawing.Size(181, 21);
            this.mDataWhere.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(465, 35);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(35, 12);
            this.label3.TabIndex = 7;
            this.label3.Text = "where";
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(816, 401);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.mDataWhere);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.mDataValue);
            this.Controls.Add(this.mUpdateBtn);
            this.Controls.Add(this.mDataKey);
            this.Controls.Add(this.mDataView);
            this.Controls.Add(this.mTreeView);
            this.Name = "MainForm";
            this.Text = "SQLiteEditor";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.onFormClose);
            this.Load += new System.EventHandler(this.onFormLoad);
            ((System.ComponentModel.ISupportInitialize)(this.mDataView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TreeView mTreeView;
        private System.Windows.Forms.DataGridView mDataView;
        private System.Windows.Forms.TextBox mDataKey;
        private System.Windows.Forms.Button mUpdateBtn;
        private System.Windows.Forms.TextBox mDataValue;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox mDataWhere;
        private System.Windows.Forms.Label label3;
    }
}

