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
            System.Windows.Forms.TreeNode treeNode2 = new System.Windows.Forms.TreeNode("");
            this.mTreeView = new System.Windows.Forms.TreeView();
            this.mDataView = new System.Windows.Forms.DataGridView();
            this.mUpdateBtn = new System.Windows.Forms.Button();
            this.mDataValue = new System.Windows.Forms.TextBox();
            this.mMenu = new System.Windows.Forms.MenuStrip();
            this.menuItemFile = new System.Windows.Forms.ToolStripMenuItem();
            this.menuItemOpen = new System.Windows.Forms.ToolStripMenuItem();
            this.helpToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.mDataKeyBox = new System.Windows.Forms.ComboBox();
            this.mWhereBox = new System.Windows.Forms.GroupBox();
            this.mDataWhereBox = new System.Windows.Forms.ComboBox();
            this.mWhereArg = new System.Windows.Forms.TextBox();
            this.mKVBox = new System.Windows.Forms.GroupBox();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.comboBox2 = new System.Windows.Forms.ComboBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.mDataView)).BeginInit();
            this.mMenu.SuspendLayout();
            this.mWhereBox.SuspendLayout();
            this.mKVBox.SuspendLayout();
            this.SuspendLayout();
            // 
            // mTreeView
            // 
            this.mTreeView.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.mTreeView.Location = new System.Drawing.Point(12, 143);
            this.mTreeView.Name = "mTreeView";
            treeNode2.Name = "mBaseNode";
            treeNode2.Text = "";
            this.mTreeView.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode2});
            this.mTreeView.Size = new System.Drawing.Size(164, 278);
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
            this.mDataView.Location = new System.Drawing.Point(195, 143);
            this.mDataView.MultiSelect = false;
            this.mDataView.Name = "mDataView";
            this.mDataView.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.AutoSizeToAllHeaders;
            this.mDataView.RowTemplate.Height = 23;
            this.mDataView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;
            this.mDataView.Size = new System.Drawing.Size(603, 278);
            this.mDataView.TabIndex = 1;
            this.mDataView.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.onRowAdded);
            this.mDataView.RowsRemoved += new System.Windows.Forms.DataGridViewRowsRemovedEventHandler(this.onRowRemoved);
            // 
            // mUpdateBtn
            // 
            this.mUpdateBtn.Location = new System.Drawing.Point(723, 35);
            this.mUpdateBtn.Name = "mUpdateBtn";
            this.mUpdateBtn.Size = new System.Drawing.Size(75, 23);
            this.mUpdateBtn.TabIndex = 5;
            this.mUpdateBtn.Text = global::SQLiteEditor.Properties.Resources.updateAll;
            this.mUpdateBtn.UseVisualStyleBackColor = true;
            this.mUpdateBtn.Click += new System.EventHandler(this.onClick);
            // 
            // mDataValue
            // 
            this.mDataValue.Location = new System.Drawing.Point(136, 18);
            this.mDataValue.Name = "mDataValue";
            this.mDataValue.Size = new System.Drawing.Size(108, 21);
            this.mDataValue.TabIndex = 3;
            // 
            // mMenu
            // 
            this.mMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.menuItemFile,
            this.helpToolStripMenuItem});
            this.mMenu.Location = new System.Drawing.Point(0, 0);
            this.mMenu.Name = "mMenu";
            this.mMenu.Size = new System.Drawing.Size(826, 25);
            this.mMenu.TabIndex = 8;
            this.mMenu.Text = "MainMenu";
            // 
            // menuItemFile
            // 
            this.menuItemFile.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.menuItemOpen});
            this.menuItemFile.MergeIndex = 0;
            this.menuItemFile.Name = "menuItemFile";
            this.menuItemFile.Size = new System.Drawing.Size(39, 21);
            this.menuItemFile.Text = "File";
            // 
            // menuItemOpen
            // 
            this.menuItemOpen.MergeIndex = 1;
            this.menuItemOpen.Name = "menuItemOpen";
            this.menuItemOpen.Size = new System.Drawing.Size(108, 22);
            this.menuItemOpen.Text = "Open";
            this.menuItemOpen.Click += new System.EventHandler(this.onClick);
            // 
            // helpToolStripMenuItem
            // 
            this.helpToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.aboutToolStripMenuItem});
            this.helpToolStripMenuItem.MergeIndex = 2;
            this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
            this.helpToolStripMenuItem.Size = new System.Drawing.Size(47, 21);
            this.helpToolStripMenuItem.Text = "Help";
            // 
            // aboutToolStripMenuItem
            // 
            this.aboutToolStripMenuItem.MergeIndex = 3;
            this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
            this.aboutToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.aboutToolStripMenuItem.Text = "About";
            this.aboutToolStripMenuItem.Click += new System.EventHandler(this.onClick);
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            this.openFileDialog1.FileOk += new System.ComponentModel.CancelEventHandler(this.onFileOk);
            // 
            // mDataKeyBox
            // 
            this.mDataKeyBox.FormattingEnabled = true;
            this.mDataKeyBox.Location = new System.Drawing.Point(6, 19);
            this.mDataKeyBox.MaxDropDownItems = 100;
            this.mDataKeyBox.Name = "mDataKeyBox";
            this.mDataKeyBox.Size = new System.Drawing.Size(114, 20);
            this.mDataKeyBox.TabIndex = 2;
            // 
            // mWhereBox
            // 
            this.mWhereBox.Controls.Add(this.mWhereArg);
            this.mWhereBox.Controls.Add(this.mDataWhereBox);
            this.mWhereBox.Location = new System.Drawing.Point(411, 29);
            this.mWhereBox.Name = "mWhereBox";
            this.mWhereBox.Size = new System.Drawing.Size(296, 108);
            this.mWhereBox.TabIndex = 9;
            this.mWhereBox.TabStop = false;
            this.mWhereBox.Text = "where";
            // 
            // mDataWhereBox
            // 
            this.mDataWhereBox.FormattingEnabled = true;
            this.mDataWhereBox.Location = new System.Drawing.Point(6, 20);
            this.mDataWhereBox.MaxDropDownItems = 100;
            this.mDataWhereBox.Name = "mDataWhereBox";
            this.mDataWhereBox.Size = new System.Drawing.Size(131, 20);
            this.mDataWhereBox.TabIndex = 4;
            // 
            // mWhereArg
            // 
            this.mWhereArg.Location = new System.Drawing.Point(157, 19);
            this.mWhereArg.Name = "mWhereArg";
            this.mWhereArg.Size = new System.Drawing.Size(96, 21);
            this.mWhereArg.TabIndex = 5;
            // 
            // mKVBox
            // 
            this.mKVBox.Controls.Add(this.textBox2);
            this.mKVBox.Controls.Add(this.comboBox2);
            this.mKVBox.Controls.Add(this.textBox1);
            this.mKVBox.Controls.Add(this.comboBox1);
            this.mKVBox.Controls.Add(this.mDataKeyBox);
            this.mKVBox.Controls.Add(this.mDataValue);
            this.mKVBox.Location = new System.Drawing.Point(13, 29);
            this.mKVBox.Name = "mKVBox";
            this.mKVBox.Size = new System.Drawing.Size(269, 108);
            this.mKVBox.TabIndex = 10;
            this.mKVBox.TabStop = false;
            this.mKVBox.Text = "Key-Value";
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Location = new System.Drawing.Point(6, 45);
            this.comboBox1.MaxDropDownItems = 100;
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(114, 20);
            this.comboBox1.TabIndex = 7;
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(136, 44);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(108, 21);
            this.textBox1.TabIndex = 8;
            // 
            // comboBox2
            // 
            this.comboBox2.FormattingEnabled = true;
            this.comboBox2.Location = new System.Drawing.Point(6, 71);
            this.comboBox2.MaxDropDownItems = 100;
            this.comboBox2.Name = "comboBox2";
            this.comboBox2.Size = new System.Drawing.Size(114, 20);
            this.comboBox2.TabIndex = 9;
            // 
            // textBox2
            // 
            this.textBox2.Location = new System.Drawing.Point(136, 69);
            this.textBox2.Name = "textBox2";
            this.textBox2.Size = new System.Drawing.Size(108, 21);
            this.textBox2.TabIndex = 10;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(826, 433);
            this.Controls.Add(this.mKVBox);
            this.Controls.Add(this.mWhereBox);
            this.Controls.Add(this.mUpdateBtn);
            this.Controls.Add(this.mDataView);
            this.Controls.Add(this.mTreeView);
            this.Controls.Add(this.mMenu);
            this.MainMenuStrip = this.mMenu;
            this.Name = "MainForm";
            this.Text = "SQLiteEditor";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.onFormClose);
            this.Load += new System.EventHandler(this.onFormLoad);
            ((System.ComponentModel.ISupportInitialize)(this.mDataView)).EndInit();
            this.mMenu.ResumeLayout(false);
            this.mMenu.PerformLayout();
            this.mWhereBox.ResumeLayout(false);
            this.mWhereBox.PerformLayout();
            this.mKVBox.ResumeLayout(false);
            this.mKVBox.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TreeView mTreeView;
        private System.Windows.Forms.DataGridView mDataView;
        private System.Windows.Forms.Button mUpdateBtn;
        private System.Windows.Forms.TextBox mDataValue;
        private System.Windows.Forms.MenuStrip mMenu;
        private System.Windows.Forms.ToolStripMenuItem menuItemFile;
        private System.Windows.Forms.ToolStripMenuItem menuItemOpen;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.ToolStripMenuItem helpToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem aboutToolStripMenuItem;
        private System.Windows.Forms.ComboBox mDataKeyBox;
        private System.Windows.Forms.GroupBox mWhereBox;
        private System.Windows.Forms.ComboBox mDataWhereBox;
        private System.Windows.Forms.TextBox mWhereArg;
        private System.Windows.Forms.GroupBox mKVBox;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.ComboBox comboBox2;
    }
}

