using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Net;
using System.Text;
using System.Windows.Forms;

namespace GetSessionkey
{
    public partial class FrmGetSessionKey : Form
    {
        /// <summary>
        /// 授权码
        /// </summary>
        public string AuthrizeCode = "";
        //private string url = "http://open.taobao.com/authorize/?appkey=21408568";
        private String url = "http://container.api.taobao.com/container?appkey=21408568";
        private String url2 = "https://oauth.taobao.com/authorize?response_type=token&client_id=21408568";

        public FrmGetSessionKey()
        {
            InitializeComponent();
        }

        /// <summary>
        /// 获取HTML页面内制定Key的Value内容
        /// </summary>
        /// <param name="html"></param>
        /// <param name="key"></param>
        /// <returns></returns>
        public string GetHiddenKeyValue(string html, string key)
        {
            string str = html.Substring(html.IndexOf(key));
            str = str.Substring(str.IndexOf("value") + 7);
            int eindex1 = str.IndexOf("'");
            int eindex2 = str.IndexOf("\"");
            int eindex = eindex2;
            if (eindex1 >= 0 && eindex1 < eindex2)
            {
                eindex = eindex1;
            }
            return str.Substring(0, eindex);
        }

        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            if (e.Url.AbsoluteUri == url)
            {
                AuthrizeCode = GetHiddenKeyValue(this.wbGetSessionkey.DocumentText, "autoInput");
                if (!string.IsNullOrEmpty(AuthrizeCode) && AuthrizeCode.IndexOf("TOP-") >= 0)
                {
                    this.DialogResult = DialogResult.OK;
                    this.Close();
                }
            }
        }

        private void FrmGetSessionKey_Load(object sender, EventArgs e)
        {
            wbGetSessionkey.Navigate(url);
        }

        private void beforNavigate(object sender, WebBrowserNavigatingEventArgs e)
        {
            Console.WriteLine(e.Url.OriginalString);
            string cookieStr = wbGetSessionkey.Document.Cookie;
            HttpWebRequest myHttpWebRequest = (HttpWebRequest)HttpWebRequest.Create(e.Url.ToString());
            HttpWebResponse myHttpWebResponse = (HttpWebResponse)myHttpWebRequest.GetResponse();
            myHttpWebRequest.AllowAutoRedirect = false;
            myHttpWebRequest.Headers.Add(HttpRequestHeader.Cookie, cookieStr);
            myHttpWebRequest.Referer = "http://container.api.taobao.com/container";
            myHttpWebRequest.UserAgent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; TheWorld)";

            string videoUrl = myHttpWebResponse.GetResponseHeader("Location");
            Console.WriteLine(videoUrl);
        }

        private void Navigated(object sender, WebBrowserNavigatedEventArgs e)
        {
            Console.WriteLine(e.Url.AbsoluteUri);
        }

    }
}
