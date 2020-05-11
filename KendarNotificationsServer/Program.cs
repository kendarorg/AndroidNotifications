using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;
using Newtonsoft.Json;
using System;
using System.IO;
using System.Net;

namespace KendarNotificationsServer
{
    class Program
    {
        static void Main(string[] args)
        {
            var host = WebHost
                .CreateDefaultBuilder()
                .UseKestrel()
                .UseStartup<WebStartup>()
                .UseUrls("http://*:5006")
                .Build();
            host.Start();

            while (true)
            {
                if ("quit" == Console.ReadLine())
                {
                    break;
                }
            }
        }
    }
}
