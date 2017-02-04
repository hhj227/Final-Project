#include <Ethernet.h>
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };  
byte ip[] = { 192, 168, 83, 12 };
byte gw[] = { 192, 168, 83, 1  };
byte subnet[] = { 255, 255, 255, 0 };
// numeric IP for Raspberry Web Server
byte server[] = { 192, 168, 83, 13 };

int data = 0;
int tempPin = 5;  // In This case the temperature is taken from pin 2 and sent to a sql server

void setup()
{
  pinMode(tempPin, INPUT);
  Serial.begin(9600);               // Used for serial debugging
}

void loop()
{
  Serial.println("Program running...");
  delay(5000);
  senddata();                                 // Data is sent every 5 seconds
}

void senddata()
{
    data = analogRead(tempPin);           //Reading analog data
    
    Ethernet.begin(mac, ip, gw, subnet);
    EthernetClient client;
    Serial.println();
    Serial.println("Forbinder?");
    delay(1000);                                    //Keeps the connection from freezing
    
    if (client.connect(server, 80)) {
      Serial.print("Connected: ");
      client.print("GET http://192.168.137.17/script.php?=gas");
      client.print(data);
      client.println(" HTTP/1.1");
      client.println("Host: 192.168.83.13");
      client.println();
      Serial.println(data);
    }
      
    else{
      Serial.println("Connection unsuccesfull");
    }

    //stop client
    client.stop();
    while(client.status() != 0)
    {
     delay(5);
    }
}

