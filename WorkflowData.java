package Group5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class WorkflowData {

    private final Deque<Action> eventStack =
            new ArrayDeque<>();

    private final Deque<Action> redoStack =
            new ArrayDeque<>();

    private State currentState = State.NEW;

    public Deque<Action> getEventStack() {
        return eventStack;
    }

    public Deque<Action> getRedoStack() {
        return redoStack;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        currentState = state;
    }

    // Event Stack จาก Bottom -> Top
    public List<Action> eventBottomToTop() {

        List<Action> result =
                new ArrayList<>();

        eventStack.descendingIterator()
                .forEachRemaining(result::add);

        return result;
    }

    // Redo Stack จาก Top -> Bottom
    public List<Action> redoTopToBottom() {

        return new ArrayList<>(redoStack);
    }

    public void reset() {

        eventStack.clear();
        redoStack.clear();

        currentState = State.NEW;
    }
}