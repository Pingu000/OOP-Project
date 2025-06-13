package battleroyale.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class BattleLog {
    private final Deque<String> logs = new ArrayDeque<>();
    private final int capacity;

    public BattleLog(int capacity) {
        this.capacity = capacity;
    }

    public void add(String entry) {
        if (logs.size() >= capacity) {
            logs.removeFirst();
        }
        logs.addLast(entry);
    }

    public List<String> getAll() {
        return logs.stream().collect(Collectors.toList());
    }
}
