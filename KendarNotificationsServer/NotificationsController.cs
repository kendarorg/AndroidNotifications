using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.IO;
using System.Net;

namespace KendarNotificationsServer
{
    [Produces("application/json")]
    [Route("api/notifications")]
    public class NotificationsController : Controller
    {
        [HttpPost]
        [Route("")]
        public NotificationResult Send(NotificationModel messageData)
        {
            var result = "-1";
            var webAddr = "https://fcm.googleapis.com/fcm/send";
            var httpWebRequest = (HttpWebRequest)WebRequest.Create(webAddr);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Headers.Add(HttpRequestHeader.Authorization, "key=PROJECTSETTINGS->Cloud Messagings->Server Key");
            httpWebRequest.Method = "POST";
            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string strNJson = JsonConvert.SerializeObject(new NotificationMessage
                {
                    To = "/topics/ServiceNow",
                    Data = new NotificationData
                    {
                        Description = messageData.Description,
                        IncidentNo = messageData.IncidentNo,
                        ShortDesc = messageData.ShortDesc
                    },
                    Notification = new Notification
                    {
                        Title = "ServiceNow: Incident No." + messageData.IncidentNo,
                        Text = "Notification"
                    }
                });
                streamWriter.Write(strNJson);
                streamWriter.Flush();
            }

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                result = streamReader.ReadToEnd();
            }

            return new NotificationResult
            {
                Result = result
            };
        }
        [HttpGet]
        [Route("sanity")]
        public string Sanity()
        {
            return "OK";
        }
    }
}
