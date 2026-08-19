package Group5;

public interface WorkflowAlgorithm {

    boolean addAction(Action action);

    boolean undo();

    boolean redo();

    boolean isInvariantValid();

    State getCurrentState();

    WorkflowData getData();

    long getPushCount();

    long getPopCount();

    long getComparisonCount();

    long getLoopCount();

    String getName();

    void reset();
}