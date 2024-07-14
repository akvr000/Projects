int flameSensorPin = A0;
int ledPin = 13;

void setup() {
  pinMode(flameSensorPin, INPUT);
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  int flameValue = analogRead(flameSensorPin);
  Serial.print("Flame Value: ");
  Serial.println(flameValue);

  if (flameValue > 500) {
    digitalWrite(ledPin, HIGH);
    Serial.println("Fire detected!");
  } else {
    digitalWrite(ledPin, LOW);
  }

  delay(500);
}


 
