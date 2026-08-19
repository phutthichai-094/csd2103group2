package Group5;

import java.util.Iterator;

/*
 * Algorithm A: Event Stack
 *
 * ตรวจสอบ Action ใหม่จากข้อมูลใน Event Stack
 */
public class Event_Stack
        implements WorkflowAlgorithm {

    private final WorkflowData data =
            new WorkflowData();

    private long pushCount;
    private long popCount;
    private long comparisonCount;
    private long loopCount;

    /*
     * Transition Table
     */
    private State transition(
            State state,
            Action action) {

        comparisonCount++;

        if (state == State.NEW &&
                action == Action.CALL_RECEIVED) {

            return State.RECEIVED;
        }

        comparisonCount++;

        if (state == State.RECEIVED &&
                action == Action.TEAM_ASSIGNED) {

            return State.ASSIGNED;
        }

        comparisonCount++;

        if (state == State.ASSIGNED &&
                action == Action.VEHICLE_DISPATCHED) {

            return State.DISPATCHED;
        }

        comparisonCount++;

        if (state == State.DISPATCHED &&
                action == Action.ARRIVED_AT_SCENE) {

            return State.ON_SCENE;
        }

        comparisonCount++;

        if (state == State.ON_SCENE &&
                action == Action.CASE_CLOSED) {

            return State.CLOSED;
        }

        return null;
    }

    /*
     * สร้าง State ปัจจุบันจาก Event Stack
     *
     * เริ่มจาก NEW
     * แล้วอ่าน Event จาก Bottom -> Top
     */
    private State replayEventStack() {

        State state = State.NEW;

        Iterator<Action> iterator =
                data.getEventStack()
                        .descendingIterator();

        while (iterator.hasNext()) {

            loopCount++;

            Action action =
                    iterator.next();

            State nextState =
                    transition(
                            state,
                            action);

            if (nextState == null) {
                return null;
            }

            state = nextState;
        }

        return state;
    }

    /*
     * ตรวจสอบ Invariant
     *
     * State ที่ได้จาก Event Stack
     * ต้องตรงกับ currentState
     */
    private boolean validateInvariant() {

        State replayedState =
                replayEventStack();

        return replayedState != null
                && replayedState
                == data.getCurrentState();
    }

    /*
     * เพิ่ม Action ใหม่
     */
    @Override
    public boolean addAction(Action action) {

        if (action == null) {
            return false;
        }

        /*
         * Algorithm A:
         * หา State จาก Event Stack
         */
        State currentState =
                replayEventStack();

        if (currentState == null) {
            return false;
        }

        /*
         * ตรวจ Transition
         */
        State nextState =
                transition(
                        currentState,
                        action);

        /*
         * Transition ผิดลำดับ
         */
        if (nextState == null) {
            return false;
        }

        /*
         * เพิ่ม Action เข้า Event Stack
         */
        data.getEventStack().push(action);

        pushCount++;

        /*
         * กฎข้อ 3
         *
         * เพิ่ม Action ใหม่หลัง Redo
         * ต้องล้าง Redo Stack
         */
        data.getRedoStack().clear();

        /*
         * Update State
         */
        data.setCurrentState(nextState);

        /*
         * กฎข้อ 4
         */
        return validateInvariant();
    }

    /*
     * Undo
     */
    @Override
    public boolean undo() {

        /*
         * กฎ:
         * Event Stack ว่าง -> Undo ไม่ได้
         */
        if (data.getEventStack().isEmpty()) {
            return false;
        }

        /*
         * เอา Action ล่าสุดออก
         */
        Action removed =
                data.getEventStack().pop();

        popCount++;

        /*
         * ย้ายไป Redo Stack
         */
        data.getRedoStack().push(removed);

        pushCount++;

        /*
         * สร้าง State ใหม่จาก Event Stack
         */
        State state =
                replayEventStack();

        if (state == null) {
            return false;
        }

        data.setCurrentState(state);

        /*
         * กฎข้อ 4
         */
        return validateInvariant();
    }

    /*
     * Redo
     */
    @Override
    public boolean redo() {

        /*
         * ไม่มี Redo
         */
        if (data.getRedoStack().isEmpty()) {
            return false;
        }

        /*
         * Action ล่าสุดของ Redo Stack
         */
        Action action =
                data.getRedoStack().peek();

        /*
         * หา State จาก Event Stack
         */
        State currentState =
                replayEventStack();

        if (currentState == null) {
            return false;
        }

        /*
         * ตรวจว่า Redo ได้หรือไม่
         */
        State nextState =
                transition(
                        currentState,
                        action);

        if (nextState == null) {
            return false;
        }

        /*
         * Redo 1 ครั้ง
         * เอา Action ล่าสุดกลับ Event Stack
         */
        data.getRedoStack().pop();

        popCount++;

        data.getEventStack().push(action);

        pushCount++;

        data.setCurrentState(nextState);

        /*
         * กฎข้อ 4
         */
        return validateInvariant();
    }

    @Override
    public boolean isInvariantValid() {
        return validateInvariant();
    }

    @Override
    public State getCurrentState() {
        return data.getCurrentState();
    }

    @Override
    public WorkflowData getData() {
        return data;
    }

    @Override
    public long getPushCount() {
        return pushCount;
    }

    @Override
    public long getPopCount() {
        return popCount;
    }

    @Override
    public long getComparisonCount() {
        return comparisonCount;
    }

    @Override
    public long getLoopCount() {
        return loopCount;
    }

    @Override
    public String getName() {
        return "Algorithm A: Event Stack";
    }

    @Override
    public void reset() {

        data.reset();

        pushCount = 0;
        popCount = 0;
        comparisonCount = 0;
        loopCount = 0;
    }
}