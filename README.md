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
