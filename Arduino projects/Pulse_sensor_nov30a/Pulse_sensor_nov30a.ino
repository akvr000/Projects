#define USE_ARDUINO_INTERRUPTS true
// Include necessary libraries
#include <PulseSensorPlayground.h>
 
// Constants
const int PULSE_SENSOR_PIN = 0;  // Analog PIN where the PulseSensor is connected
const int LED_PIN = 13;          // On-board LED PIN
const int THRESHOLD = 550;       // Threshold for detecting a heartbeat
 
// Create PulseSensorPlayground object
PulseSensorPlayground pulseSensor;
 
void setup() 
{
  // Initialize Serial Monitor
  Serial.begin(9600);
 
  // Configure PulseSensor
  pulseSensor.analogInput(PULSE_SENSOR_PIN);
  pulseSensor.blinkOnPulse(LED_PIN);
  pulseSensor.setThreshold(THRESHOLD);
 
  // Check if PulseSensor is initialized
  if (pulseSensor.begin()) 
  {
    Serial.println("PulseSensor object created successfully!");
  }
}
 
void loop() 
{
  // Get the current Beats Per Minute (BPM)
  int currentBPM = pulseSensor.getBeatsPerMinute();
 
  // Check if a heartbeat is detected
  if (pulseSensor.sawStartOfBeat()) 
  {
    Serial.println("♥ A HeartBeat Happened!");
    Serial.print("BPM: ");
    Serial.println(currentBPM);
  }
 
  // Add a small delay to reduce CPU usage
  delay(20);
}
/*const int pulsePin = A0;  // Pulse sensor analog pin
const int ledPin = 13;   // LED pin

int threshold = 550;  // Adjust this based on your sensor readings
int pulseCount = 0;   // Variable to store pulse count

void setup() {
  Serial.begin(9600);
  pinMode(ledPin, OUTPUT);
}

void loop() {
  int pulseValue = analogRead(pulsePin);

  if (pulseValue > threshold) {
    digitalWrite(ledPin, HIGH);  // Turn on LED if pulse detected
    if (pulseValue > threshold && pulseValue < (threshold + 100)) {
      // To avoid counting multiple pulses for a single beat
      pulseCount++;
      Serial.println("Pulse Detected! Count: " + String(pulseCount));
    }
  } else {
    digitalWrite(ledPin, LOW);  // Turn off LED if no pulse
  }

  delay(1000);  // Adjust delay based on your needs
}*/

