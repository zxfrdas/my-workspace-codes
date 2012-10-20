using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CRCMD5校验
{
    public partial class MainForm : Form
    {
        private String filePath;

        public MainForm()
        {
            InitializeComponent();
        }

        private void onProgressChanged(object sender, ProgressChangedEventArgs e)
        {

        }

        private void onWorkStarted(object sender, DoWorkEventArgs e)
        {

        }

        private void onWorkCompleted(object sender, RunWorkerCompletedEventArgs e)
        {

        }

        private void onDialogConfirm(object sender, CancelEventArgs e)
        {
            if (openFileDialog.Equals(sender))
            {
                filePath = ((OpenFileDialog)sender).FileName;
                inputRect.Text = filePath;
            }
        }

        private void onClick(object sender, EventArgs e)
        {
            openFileDialog.ShowDialog();
        }

        private void dragDrop(object sender, DragEventArgs e)
        {
            foreach (String str in (String[])e.Data.GetData(DataFormats.FileDrop)) {
                MessageBox.Show(str);
            }
        }

        private void dragEnter(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(DataFormats.FileDrop)) {
                e.Effect = DragDropEffects.Link;
            } else {
                e.Effect = DragDropEffects.None;
            }
        }
    }
}
