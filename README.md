# Battle Royale Project

This repository contains a simple console-based Battle Royale game written in Java. The project fulfills the requirements described in `Final Project.pdf`.

## Requirements
- Java 17 or higher

## Compile and Run

```
javac -d out $(find src -name "*.java")
java -cp out battleroyale.game.Main
```

The game loads available characters and tools from `characters.txt` and `tools.txt`. If these files are missing, default values are used.

## Using IntelliJ

1. Open IntelliJ and choose **File → Open...**, then select the root directory of this repository. IntelliJ will detect the Gradle configuration and import the project.
2. In the Project view, navigate to `src/battleroyale/game/Main.java`.
3. Right‑click `Main.java` and choose **Run 'Main.main()'** to start the game from the IDE.
