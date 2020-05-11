using Newtonsoft.Json;

namespace KendarNotificationsServer
{
    public class NotificationData
    {
        public string ShortDesc { get; set; }
        public long IncidentNo { get; set; }
        public string Description { get; set; }
    }
    public class Notification
    {
        public Notification()
        {
            Sound = "default";
        }
        [JsonProperty("title")]
        public string Title { get; set; }
        [JsonProperty("text")]
        public string Text { get; set; }
        [JsonProperty("sound")]
        public string Sound { get; set; }
    }
    public class NotificationMessage
    {
        [JsonProperty("to")]
        public string To { get; set; }
        [JsonProperty("data")]
        public NotificationData Data { get; set; }
        [JsonProperty("notification")]
        public Notification Notification { get; set; }

    }
}
