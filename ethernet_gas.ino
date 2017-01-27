#include <SPI.h>
#include <Ethernet.h>

//가스감지센서: 
int smokeA0 = A5;
int smoke_count=0;
int smoke_before=0;

// Enter a MAC address for your controller below.
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
//IPAddress server(74,125,232,128);  // numeric IP for Google (no DNS)
char server[] = "www.google.com";    // name address for Google (using DNS)

// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(192, 168, 0, 177);

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
EthernetClient client;

void setup() {
  
  pinMode(smokeA0, INPUT);
  Serial.begin(9600);
  
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // start the Ethernet connection:
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // try to congifure using IP address instead of DHCP:
    Ethernet.begin(mac, ip);
  }
  // give the Ethernet shield a second to initialize:
  delay(1000);
  Serial.println("connecting...");

  // if you get a connection, report back via serial:
  if (client.connect(server, 80)) {
    Serial.println("connected");
    // Make a HTTP request:
    client.println("GET /search?q=arduino HTTP/1.1");
    client.println("Host: www.google.com");
    client.println("Connection: close");
    client.println();
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
}

void loop() {
  
  int analogSensor = analogRead(smokeA0);
  if(analogSensor>100){
    if(smoke_before==0){
       smoke_before=1;
    }
    smoke_count++;
  }
  else{
    smoke_before=1;
    smoke_count=0;
  }
  // if there are incoming bytes available
  // from the server, read them and print them:
  if (smoke_count<10) {
    Serial.print(smoke_count);
    Serial.print(smoke_before);
    Serial.print(",  Pin A0: ");
    Serial.println(analogSensor);
    delay(1000);
  }

  // if the server's disconnected, stop the client:
  else{
    Serial.println();
    Serial.println("disconnecting.");
    client.stop();
    // do nothing forevermore:
    while (true);
  }
}

