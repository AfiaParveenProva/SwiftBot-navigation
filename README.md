# 🤖 SwiftBot Navigation — QR Code Controlled Raspberry Pi Robot

A Java-based navigation program for a Raspberry Pi-powered SwiftBot robot, where movement commands (forward, backward, left, right, retrace) are executed by scanning QR codes with the robot's camera, with LED underlight feedback, automatic photo capture, and command logging features.

---

## 📖 About the Project

This program controls a **SwiftBot robot** (named *Poniyo Neer*) running on a **Raspberry Pi**. Instead of typing commands manually, the robot reads instructions by **scanning QR codes** through its onboard camera. After each movement, it automatically captures a photo of what is in front of it.

The project was built as part of a university robotics programming assignment, demonstrating real-time hardware control, image processing, and command logging in Java.

---

## ✨ Features

- 📷 **QR code scanning** — point a QR code at the robot's camera to issue commands
- 🚗 **Movement control** — forward, backward, left turn, right turn
- 🔁 **Retrace** — replay the last N movements automatically
- 📝 **Command logging** — save a timestamped history of all commands to a text file
- 💡 **LED underlight feedback** — colour-coded lights show the robot's current action
- 📸 **Automatic photo capture** — takes a 144×144 image after every movement
- 🔘 **Physical button controls** — buttons on the robot trigger additional features
- ✅ **Input validation** — duration (0–6s) and speed (0–100) are validated before execution

---

## 🛠️ Technologies Used

Java : Core programming language

SwiftBot API : Robot hardware control (motors, camera, underlights, buttons) 

Raspberry Pi : Hardware platform running the program 

BufferedReader : Keyboard input handling 

ArrayList : Command history / retrace memory 

Java ImageIO : Image capture and saving 

Java FileWriter : Log file writing 

Java SimpleDateFormat : Timestamping log entries 

---

## 🎮 Commands

> Commands are encoded into QR codes and scanned by the robot. Press `8` on the keyboard to trigger a scan.

| Command | Syntax | Description |
|---|---|---|
| Forward | `F [duration] [speed]` | Move forward for up to 6 seconds |
| Backward | `B [duration] [speed]` | Move backward for up to 6 seconds |
| Right | `R [duration] [speed]` | Turn right 90° then move forward |
| Left | `L [duration] [speed]` | Turn left 90° then move forward |
| Retrace | `T [steps]` | Replay the last N movement commands |
| Write Log | `W` | Save timestamped command history to `W.txt` |

**Parameter constraints:**
- `duration` — in seconds, must be between 0 and 6
- `speed` — must be between 0 and 100

**Example:** `F 3 50` moves the robot forward for 3 seconds at speed 50.

---

## 🔘 Button Controls

Physical buttons on the SwiftBot trigger additional features without needing a QR code:

**A** : Display how many times each command has been executed 

**B** : Display the most frequently used command 

**X** : Terminate / exit the program 

**Y** : Display the total duration the program has been running 

---

## 💡 Underlight Feedback

The robot's LED underlights change colour to indicate its current state:

🔵 Teal Blue : Scanning for a QR code 

🟢 Green : Moving forward or turning 

🔴 Red : Moving backward or turning left 

🟣 Purple/Yellow : Writing log file (W command) 

🟡 Yellow/Purple : Invalid command received 

---

## ▶️ How to Run

### Prerequisites
- Raspberry Pi with SwiftBot hardware
- Java Development Kit (JDK) installed
- SwiftBot API library on the classpath

### Steps
1. Clone this repository onto your Raspberry Pi:
   ```bash
   git clone https://github.com/your-username/swiftbot-navigation.git
   ```
2. Compile the program:
   ```bash
   javac -cp .:swiftbot.jar Assignment_3.java
   ```
3. Run the program:
   ```bash
   java -cp .:swiftbot.jar Assignment_3
   ```
4. Press `8` and hit Enter to begin scanning a QR code.

---

## 📁 Project Structure

```
swiftbot-navigation/
│
├── Assignment_3.java      # Main program file
├── W.txt                  # Generated command log file (created at runtime)
└── README.md              # Project documentation
```

---

## 👩‍💻 Author

**Afia**  
BSc Computer Science — Brunel University London
