namespace CRCMD5校验
{
    partial class MainForm
    {
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
            if (disposing && (components != null))
            {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.thread = new System.ComponentModel.BackgroundWorker();
            this.openFileDialog = new System.Windows.Forms.OpenFileDialog();
            this.openBtn = new System.Windows.Forms.Button();
            this.inputRect = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // thread
            // 
            this.thread.WorkerReportsProgress = true;
            this.thread.WorkerSupportsCancellation = true;
            this.thread.DoWork += new System.ComponentModel.DoWorkEventHandler(this.onWorkStarted);
            this.thread.ProgressChanged += new System.ComponentModel.ProgressChangedEventHandler(this.onProgressChanged);
            this.thread.RunWorkerCompleted += new System.ComponentModel.RunWorkerCompletedEventHandler(this.onWorkCompleted);
            // 
            // openFileDialog
            // 
            this.openFileDialog.FileOk += new System.ComponentModel.CancelEventHandler(this.onDialogConfirm);
            // 
            // openBtn
            // 
            resources.ApplyResources(this.openBtn, "openBtn");
            this.openBtn.Name = "openBtn";
            this.openBtn.UseVisualStyleBackColor = true;
            this.openBtn.Click += new System.EventHandler(this.onClick);
            // 
            // inputRect
            // 
            this.inputRect.AllowDrop = true;
            this.inputRect.Cursor = System.Windows.Forms.Cursors.Arrow;
            resources.ApplyResources(this.inputRect, "inputRect");
            this.inputRect.Name = "inputRect";
            this.inputRect.ReadOnly = true;
            this.inputRect.DragDrop += new System.Windows.Forms.DragEventHandler(this.dragDrop);
            this.inputRect.DragEnter += new System.Windows.Forms.DragEventHandler(this.dragEnter);
            // 
            // MainForm
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.inputRect);
            this.Controls.Add(this.openBtn);
            this.Name = "MainForm";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.ComponentModel.BackgroundWorker thread;
        private System.Windows.Forms.OpenFileDialog openFileDialog;
        private System.Windows.Forms.Button openBtn;
        private System.Windows.Forms.TextBox inputRect;
    }
}

