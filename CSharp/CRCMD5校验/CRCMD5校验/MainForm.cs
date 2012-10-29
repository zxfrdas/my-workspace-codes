using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Security.Policy;
using System.Security.Cryptography;
using System.IO;
using System.Threading;

namespace CRCMD5校验
{
    public partial class MainForm : Form
    {
        private const int BUFFER_LENGTH = 1024 * 1024;
        private String filePath;
        private Boolean bIsDialogShown;
        private HashAlgorithm hash;
        private FileStream fs;
        private byte[] buffer;

        public MainForm()
        {
            InitializeComponent();
        }

        private void onProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            workProgress.Maximum = (int)fs.Length;
            workProgress.Value = e.ProgressPercentage;
        }

        private void onWorkStarted(object sender, DoWorkEventArgs e)
        {
            int offset = 0;
            hash = MD5.Create();
            buffer = new byte[BUFFER_LENGTH];
            fs = File.Open(filePath, FileMode.Open);
            mWorker.ReportProgress((int)fs.Position);
            while (fs.Position + buffer.Length < fs.Length && !mWorker.CancellationPending) {
                fs.Read(buffer, 0, buffer.Length);
                hash.TransformBlock(buffer, 0, buffer.Length, buffer, 0);
                mWorker.ReportProgress((int)fs.Position);
                offset = (int) fs.Length - (int) fs.Position;
            }
            fs.Read(buffer, 0, buffer.Length);
            hash.TransformFinalBlock(buffer, 0, offset);
            mWorker.ReportProgress((int)fs.Position);
        }

        private void onWorkCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            fs.Flush();
            fs.Dispose();
            openBtn.Enabled = true;
            outRect.Text = byteArrayToHexString(hash.Hash);
        }

        private void onDialogConfirm(object sender, CancelEventArgs e)
        {
            if (openFileDialog.Equals(sender))
            {
                bIsDialogShown = false;
                workInit(((OpenFileDialog)sender).FileName);
            }
        }

        private void onClick(object sender, EventArgs e)
        {
            openFileDialog.ShowDialog();
            bIsDialogShown = true;
        }

        private void dragDrop(object sender, DragEventArgs e)
        {
            foreach (String str in (String[])e.Data.GetData(DataFormats.FileDrop)) {
                workInit(str);
            }
        }

        private void dragEnter(object sender, DragEventArgs e)
        {
            if (!mWorker.IsBusy && !bIsDialogShown) {
                if (e.Data.GetDataPresent(DataFormats.FileDrop)) {
                    e.Effect = DragDropEffects.Link;
                } else {
                    e.Effect = DragDropEffects.None;
                }
            } else {
                e.Effect = DragDropEffects.None;
            }
        }

        private void workInit(String path)
        {
            filePath = path;
            inputRect.Text = filePath;
            openBtn.Enabled = false;
            mWorker.RunWorkerAsync();
        }

        private String byteArrayToHexString(byte[] values)
        {
            StringBuilder sb = new StringBuilder();
            foreach (byte value in values) {
                sb.AppendFormat("{0:X2}", value);
            }
            return sb.ToString();
        } 

    }
}
