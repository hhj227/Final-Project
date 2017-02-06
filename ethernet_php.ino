#include <Ethernet.h>
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };  
byte ip[] = { 192, 168, 123, 152 };
byte gw[] = { 192, 168, 123, 254  };
byte subnet[] = { 255, 255, 255, 0 };
// numeric IP for Raspberry Web Server
byte server[] = { 192, 168, 123, 116 };

EthernetClient client;

int gasdata = 0;
int tempPin = 5;

void setup(){
  pinMode(tempPin, INPUT);
  Ethernet.begin(mac, ip, gw, subnet);
  Serial.begin(9600);               // Used for serial debugging
  Serial.println("Program running...");
}

void loop(){
  delay(1000);
  senddata();                                 // Data is sent every 5 seconds
}

void senddata(){
    gasdata = analogRead(tempPin);           //Reading analog data    
    delay(1000);                                    //Keeps the connection from freezing  
    
    if (client.connect(server, 80)) {
      Serial.print("Connected: ");
      client.print("GET http://192.168.137.17/index.php?gas=");
//      client.print("GET /index.php?gas=");
//      client.print("GET /?gas=");
      client.print(gasdata);
      client.println(" HTTP/1.1");
      client.println("Host: 192.168.123.116");
      client.println();
      Serial.println(gasdata);
      Serial.println();
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

