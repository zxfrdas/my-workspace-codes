﻿namespace GetSessionkey
{
    partial class FrmGetSessionKey
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
            this.wbGetSessionkey = new System.Windows.Forms.WebBrowser();
            this.SuspendLayout();
            // 
            // wbGetSessionkey
            // 
            this.wbGetSessionkey.Dock = System.Windows.Forms.DockStyle.Fill;
            this.wbGetSessionkey.Location = new System.Drawing.Point(0, 0);
            this.wbGetSessionkey.MinimumSize = new System.Drawing.Size(20, 20);
            this.wbGetSessionkey.Name = "wbGetSessionkey";
            this.wbGetSessionkey.Size = new System.Drawing.Size(918, 523);
            this.wbGetSessionkey.TabIndex = 0;
            this.wbGetSessionkey.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser1_DocumentCompleted);
            this.wbGetSessionkey.Navigated += new System.Windows.Forms.WebBrowserNavigatedEventHandler(this.Navigated);
            this.wbGetSessionkey.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.beforNavigate);
            // 
            // FrmGetSessionKey
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(918, 523);
            this.Controls.Add(this.wbGetSessionkey);
            this.Name = "FrmGetSessionKey";
            this.Text = "获取淘宝Top SessionKey";
            this.Load += new System.EventHandler(this.FrmGetSessionKey_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.WebBrowser wbGetSessionkey;
    }
}