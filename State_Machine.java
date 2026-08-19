package Group5;

/*
 * Algorithm B:
 * Event Stack + State Machine
 */
public class State_Machine
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
     * ใช้เฉพาะตอน Undo
     *
     * หา State ก่อนหน้า
     */
    private State previousState(
            State state,
            Action action) {

        comparisonCount++;

        if (state == State.RECEIVED &&
                action == Action.CALL_RECEIVED) {

            return State.NEW;
        }

        comparisonCount++;

        if (state == State.ASSIGNED &&
                action == Action.TEAM_ASSIGNED) {

            return State.RECEIVED;
        }

        comparisonCount++;

        if (state == State.DISPATCHED &&
                action == Action.VEHICLE_DISPATCHED) {

            return State.ASSIGNED;
        }

        comparisonCount++;

        if (state == State.ON_SCENE &&
                action == Action.ARRIVED_AT_SCENE) {

            return State.DISPATCHED;
        }

        comparisonCount++;

        if (state == State.CLOSED &&
                action == Action.CASE_CLOSED) {

            return State.ON_SCENE;
        }

        return null;
    }

    /*
     * ตรวจสอบ Invariant
     *
     * ใช้สำหรับตรวจสอบความถูกต้องเท่านั้น
     * ไม่ได้ใช้เป็นวิธีหลักในการ Add Action
     */
    private boolean validateInvariant() {

        State state = State.NEW;

        for (Action action :
                data.eventBottomToTop()) {

            loopCount++;

            state =
                    transition(
                            state,
                            action);

            if (state == null) {
                return false;
            }
        }

        return state ==
                data.getCurrentState();
    }

    /*
     * เพิ่ม Action
     */
    @Override
    public boolean addAction(Action action) {

        if (action == null) {
            return false;
        }

        /*
         * Algorithm B:
         *
         * ใช้ currentState
         * ตรวจ State Machine โดยตรง
         */
        State nextState =
                transition(
                        data.getCurrentState(),
                        action);

        /*
         * กฎข้อ 5
         *
         * ห้าม Transition ผิดลำดับ
         */
        if (nextState == null) {
            return false;
        }

        /*
         * เพิ่ม Action
         */
        data.getEventStack().push(action);

        pushCount++;

        /*
         * Update currentState
         */
        data.setCurrentState(nextState);

        /*
         * กฎข้อ 3
         *
         * เพิ่ม Action ใหม่หลัง Redo
         * ต้องล้าง Redo Stack
         */
        data.getRedoStack().clear();

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
         * Event Stack ว่าง
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
         * หา State ก่อนหน้า
         */
        State previous =
                previousState(
                        data.getCurrentState(),
                        removed);

        /*
         * ถ้าหา State ไม่ได้
         * ให้คืน Action กลับ
         */
        if (previous == null) {

            data.getEventStack()
                    .push(removed);

            pushCount++;

            return false;
        }

        /*
         * ย้าย Action ไป Redo
         */
        data.getRedoStack().push(removed);

        pushCount++;

        /*
         * Update State
         */
        data.setCurrentState(previous);

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
         * กฎข้อ 2
         *
         * Redo 1 ครั้ง
         * ต้องคืน Action ล่าสุด
         * กลับ Event Stack
         */
        Action action =
                data.getRedoStack().peek();

        /*
         * ตรวจ Transition
         * จาก currentState โดยตรง
         */
        State nextState =
                transition(
                        data.getCurrentState(),
                        action);

        /*
         * Redo ไม่ได้
         */
        if (nextState == null) {
            return false;
        }

        /*
         * ย้าย Redo -> Event
         */
        data.getRedoStack().pop();

        popCount++;

        data.getEventStack().push(action);

        pushCount++;

        /*
         * Update State
         */
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
        return "Algorithm B: Event Stack + State Machine";
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