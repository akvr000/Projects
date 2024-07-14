
/*int trigPin = 6;
int echoPin = 7;
int Buzzer = 13;
void setup()
{
Serial.begin(9600);
pinMode(Buzzer, OUTPUT);
pinMode(trigPin, OUTPUT);
pinMode(echoPin, INPUT);
}
void loop() {
long duration, distance;
digitalWrite(trigPin,HIGH);
delayMicroseconds(1000);
digitalWrite(trigPin, LOW);
duration=pulseIn(echoPin,
HIGH);
distance =(duration/2)/29.1;
delay(10);
Serial.print(distance);
Serial.println("CM");
if((distance<=60))
{
digitalWrite(Buzzer, HIGH);
}
else if(distance>30)
{
digitalWrite(Buzzer, LOW);
}
}*/

/*const int trigPin = 9;
const int echoPin = 10;
const int buzzerPin = 11;
const int safeDistance = 30; // Adjust this based on your needs

void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(buzzerPin, OUTPUT);
}

void loop() {
  long duration, distance;
  
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;

  Serial.println(distance);

  if (distance < safeDistance) {
    digitalWrite(buzzerPin, HIGH);
  } else {
    digitalWrite(buzzerPin, LOW);
  }

  delay(1000); // Adjust delay based on your needs
}*/

const int trigPin = 9;
const int echoPin = 10;
const int ledPin = 11;
const int safeDistance = 80; // Adjust this based on your needs

void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(ledPin, OUTPUT);
}

void loop() {
  long duration, distance;
  
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;

  Serial.println(distance);

  if (distance < safeDistance) {
    digitalWrite(ledPin, HIGH); // LED on if too close
  } else {
    digitalWrite(ledPin, LOW); // LED off if at a safe distance
  }

  delay(1000); // Adjust delay based on your needs
}


