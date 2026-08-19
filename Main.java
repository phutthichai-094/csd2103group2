package Group5;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    // Algorithm เริ่มต้น
    private static WorkflowAlgorithm algorithm =
            new State_Machine();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println(
                "==============================================");

        System.out.println(
                "       EMERGENCY WORKFLOW - GROUP 5");

        System.out.println(
                "==============================================");

        while (running) {

            showMenu();

            int choice =
                    readInt("เลือกเมนู: ");

            System.out.println();

            switch (choice) {

                case 1:
                    addAction();
                    break;

                case 2:
                    undoAction();
                    break;

                case 3:
                    redoAction();
                    break;

                case 4:
                    resetSystem();
                    break;

                case 5:
                    changeAlgorithm();
                    break;

                case 6:
                    testingMenu();
                    break;

                case 0:

                    running = false;

                    System.out.println(
                            "ออกจากโปรแกรมเรียบร้อย");

                    break;

                default:

                    System.out.println(
                            "กรุณาเลือกเมนู 0 - 6");
            }

            if (running) {
                showStatus();
            }

            System.out.println();
        }

        scanner.close();
    }

    // =====================================================
    // MAIN MENU
    // =====================================================

    private static void showMenu() {

        System.out.println();
        System.out.println(
                "==============================================");

        System.out.println(
                "              EMERGENCY WORKFLOW");

        System.out.println(
                "==============================================");

        System.out.println(
                "Algorithm : "
                        + getAlgorithmName());

        System.out.println(
                "Current State : "
                        + algorithm.getCurrentState());

        System.out.println(
                "----------------------------------------------");

        System.out.println(
                "1. เพิ่ม Action");

        System.out.println(
                "2. Undo");

        System.out.println(
                "3. Redo");

        System.out.println(
                "4. Reset");

        System.out.println(
                "5. เปลี่ยน Algorithm");

        System.out.println(
                "6. ทดสอบและวิเคราะห์");

        System.out.println(
                "0. ออกจากโปรแกรม");

        System.out.println(
                "==============================================");
    }

    // =====================================================
    // 1. เพิ่ม ACTION
    // =====================================================

    private static void addAction() {

        System.out.println(
                "==============================================");

        System.out.println(
                "                 เพิ่ม Action");

        System.out.println(
                "==============================================");

        System.out.println(
                "Current State : "
                        + algorithm.getCurrentState());

        System.out.println();

        System.out.println(
                "1. CALL_RECEIVED");

        System.out.println(
                "2. TEAM_ASSIGNED");

        System.out.println(
                "3. VEHICLE_DISPATCHED");

        System.out.println(
                "4. ARRIVED_AT_SCENE");

        System.out.println(
                "5. CASE_CLOSED");

        System.out.println(
                "0. ยกเลิก");

        System.out.println();

        int choice =
                readInt("เลือก Action: ");

        if (choice == 0) {

            System.out.println(
                    "ยกเลิกการเพิ่ม Action");

            return;
        }

        Action action =
                convertAction(choice);

        if (action == null) {

            System.out.println(
                    "เลือก Action ไม่ถูกต้อง");

            return;
        }

        boolean success =
                algorithm.addAction(action);

        if (success) {

            System.out.println();
            System.out.println(
                    "เพิ่ม Action สำเร็จ");

            System.out.println(
                    "Action : "
                            + action);

            System.out.println(
                    "Current State ใหม่ : "
                            + algorithm.getCurrentState());

        } else {

            System.out.println();
            System.out.println(
                    "ไม่สามารถเพิ่ม Action ได้");

            System.out.println(
                    "ห้าม Transition ผิดลำดับ");

            System.out.println(
                    "Event Stack และ Redo Stack "
                            + "ไม่ถูกเปลี่ยน");
        }
    }

    // =====================================================
    // 2. UNDO
    // =====================================================

    private static void undoAction() {

        boolean success =
                algorithm.undo();

        if (success) {

            System.out.println(
                    "UNDO สำเร็จ");

            System.out.println(
                    "Action ล่าสุดถูกย้ายจาก "
                            + "Event Stack -> Redo Stack");

        } else {

            System.out.println(
                    "UNDO ไม่สำเร็จ");

            System.out.println(
                    "Event Stack ไม่มี Action");
        }
    }

    // =====================================================
    // 3. REDO
    // =====================================================

    private static void redoAction() {

        boolean success =
                algorithm.redo();

        if (success) {

            System.out.println(
                    "REDO สำเร็จ");

            System.out.println(
                    "Action ล่าสุดถูกย้ายจาก "
                            + "Redo Stack -> Event Stack");

        } else {

            System.out.println(
                    "REDO ไม่สำเร็จ");

            System.out.println(
                    "Redo Stack ไม่มี Action "
                            + "หรือ Transition ไม่ถูกต้อง");
        }
    }

    // =====================================================
    // 4. RESET
    // =====================================================

    private static void resetSystem() {

        algorithm.reset();

        System.out.println(
                "==============================================");

        System.out.println(
                "                    RESET");

        System.out.println(
                "==============================================");

        System.out.println(
                "Event Stack ถูกล้าง");

        System.out.println(
                "Redo Stack ถูกล้าง");

        System.out.println(
                "Current State = NEW");
    }

    // =====================================================
    // 5. เปลี่ยน ALGORITHM
    // =====================================================

    private static void changeAlgorithm() {

        System.out.println(
                "==============================================");

        System.out.println(
                "              เปลี่ยน Algorithm");

        System.out.println(
                "==============================================");

        System.out.println(
                "Algorithm ปัจจุบัน : "
                        + getAlgorithmName());

        System.out.println();

        System.out.println(
                "1. Algorithm A - Event Stack");

        System.out.println(
                "2. Algorithm B - Event Stack + State Machine");

        System.out.println(
                "0. ยกเลิก");

        System.out.println();

        int choice =
                readInt("เลือก Algorithm: ");

        switch (choice) {

            case 1:

                algorithm =
                        new Event_Stack();

                System.out.println(
                        "เปลี่ยนเป็น Algorithm A สำเร็จ");

                System.out.println(
                        "ระบบ Reset เพื่อป้องกัน "
                                + "ข้อมูล Algorithm เดิมปะปน");

                break;

            case 2:

                algorithm =
                        new State_Machine();

                System.out.println(
                        "เปลี่ยนเป็น Algorithm B สำเร็จ");

                System.out.println(
                        "ระบบ Reset เพื่อป้องกัน "
                                + "ข้อมูล Algorithm เดิมปะปน");

                break;

            case 0:

                System.out.println(
                        "ยกเลิก");

                break;

            default:

                System.out.println(
                        "เลือก Algorithm ไม่ถูกต้อง");
        }
    }

    // =====================================================
    // 6. TESTING MENU
    // =====================================================

    private static void testingMenu() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println(
                    "==============================================");

            System.out.println(
                    "             TEST & ANALYSIS");

            System.out.println(
                    "==============================================");

            System.out.println(
                    "Algorithm : "
                            + getAlgorithmName());

            System.out.println();

            System.out.println(
                    "1. Test Cases 10 ข้อ");

            System.out.println(
                    "2. แสดง Operation Count");

            System.out.println(
                    "3. Performance Test");

            System.out.println(
                    "0. กลับเมนูหลัก");

            System.out.println();

            int choice =
                    readInt("เลือกเมนู: ");

            switch (choice) {

                case 1:
                    runAllTests();
                    break;

                case 2:
                    showOperationCount();
                    break;

                case 3:
                    runPerformanceTest();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println(
                            "กรุณาเลือก 0 - 3");
            }
        }
    }

    // =====================================================
    // OPERATION COUNT
    // =====================================================

    private static void showOperationCount() {

        System.out.println(
                "==============================================");

        System.out.println(
                "              OPERATION COUNT");

        System.out.println(
                "==============================================");

        System.out.println(
                "Algorithm : "
                        + getAlgorithmName());

        System.out.println(
                "Push : "
                        + algorithm.getPushCount());

        System.out.println(
                "Pop : "
                        + algorithm.getPopCount());

        System.out.println(
                "Comparisons : "
                        + algorithm.getComparisonCount());

        System.out.println(
                "Loops : "
                        + algorithm.getLoopCount());

        System.out.println(
                "Event Stack Size : "
                        + algorithm.getData()
                        .getEventStack()
                        .size());

        System.out.println(
                "Redo Stack Size : "
                        + algorithm.getData()
                        .getRedoStack()
                        .size());

        System.out.println(
                "==============================================");
    }

    // =====================================================
    // TEST CASES
    // =====================================================

    private static void runAllTests() {

        System.out.println(
                "==============================================");

        System.out.println(
                "             TEST CASES 1 - 10");

        System.out.println(
                "==============================================");

        runTestsFor(
                new Event_Stack());

        System.out.println();

        runTestsFor(
                new State_Machine());
    }

    private static void runTestsFor(
            WorkflowAlgorithm a) {

        int passed = 0;

        System.out.println();
        System.out.println(
                a.getName());

        // -------------------------------------------------
        // Test 1
        // Workflow ถูกต้องตั้งแต่เริ่มจนจบ
        // -------------------------------------------------

        a.reset();

        boolean test1 =
                addFullWorkflow(a)
                &&
                a.getCurrentState()
                        == State.CLOSED
                &&
                a.isInvariantValid();

        passed += printTest(1, test1);

        // -------------------------------------------------
        // Test 2
        // เพิ่ม Action ผิดลำดับ
        // -------------------------------------------------

        a.reset();

        boolean test2 =
                !a.addAction(
                        Action.TEAM_ASSIGNED)
                &&
                a.getCurrentState()
                        == State.NEW
                &&
                a.getData()
                        .getEventStack()
                        .isEmpty();

        passed += printTest(2, test2);

        // -------------------------------------------------
        // Test 3
        // ปิด Case ก่อนเกิดเหตุ
        // -------------------------------------------------

        a.reset();

        boolean test3 =
                !a.addAction(
                        Action.CASE_CLOSED)
                &&
                a.getCurrentState()
                        == State.NEW;

        passed += printTest(3, test3);

        // -------------------------------------------------
        // Test 4
        // Undo หนึ่งครั้ง
        // -------------------------------------------------

        a.reset();

        a.addAction(
                Action.CALL_RECEIVED);

        a.addAction(
                Action.TEAM_ASSIGNED);

        boolean test4 =
                a.undo()
                &&
                a.getCurrentState()
                        == State.RECEIVED
                &&
                a.getData()
                        .getRedoStack()
                        .size()
                        == 1;

        passed += printTest(4, test4);

        // -------------------------------------------------
        // Test 5
        // Undo หลายครั้ง
        // ต้องมี 2 Action ใน Redo
        // -------------------------------------------------

        a.reset();

        a.addAction(
                Action.CALL_RECEIVED);

        a.addAction(
                Action.TEAM_ASSIGNED);

        a.addAction(
                Action.VEHICLE_DISPATCHED);

        boolean test5 =
                a.undo()
                &&
                a.undo()
                &&
                a.getData()
                        .getRedoStack()
                        .size()
                        == 2
                &&
                a.getCurrentState()
                        == State.RECEIVED;

        passed += printTest(5, test5);

        // -------------------------------------------------
        // Test 6
        // Redo บางส่วน
        // -------------------------------------------------

        boolean test6 =
                a.redo()
                &&
                a.getData()
                        .getRedoStack()
                        .size()
                        == 1
                &&
                a.getData()
                        .getEventStack()
                        .size()
                        == 2
                &&
                a.getCurrentState()
                        == State.ASSIGNED;

        passed += printTest(6, test6);

        // -------------------------------------------------
        // Test 7
        // เพิ่ม Action ใหม่หลัง Redo
        // ต้องล้าง Redo
        // -------------------------------------------------

        boolean test7 =
                a.addAction(
                        Action.VEHICLE_DISPATCHED)
                &&
                a.getData()
                        .getRedoStack()
                        .isEmpty()
                &&
                a.getCurrentState()
                        == State.DISPATCHED;

        passed += printTest(7, test7);

        // -------------------------------------------------
        // Test 8
        // Undo จน Stack ว่าง
        // -------------------------------------------------

        a.reset();

        a.addAction(
                Action.CALL_RECEIVED);

        boolean test8 =
                a.undo()
                &&
                !a.undo()
                &&
                a.getData()
                        .getEventStack()
                        .isEmpty()
                &&
                a.getCurrentState()
                        == State.NEW;

        passed += printTest(8, test8);

        // -------------------------------------------------
        // Test 9
        // Redo เมื่อไม่มีรายการ
        // -------------------------------------------------

        a.reset();

        boolean test9 =
                !a.redo()
                &&
                a.getCurrentState()
                        == State.NEW;

        passed += printTest(9, test9);

        // -------------------------------------------------
        // Test 10
        // เพิ่ม Action หลังปิด Case
        // -------------------------------------------------

        a.reset();

        addFullWorkflow(a);

        boolean test10 =
                !a.addAction(
                        Action.CALL_RECEIVED)
                &&
                a.getCurrentState()
                        == State.CLOSED
                &&
                a.getData()
                        .getEventStack()
                        .size()
                        == 5;

        passed += printTest(10, test10);

        System.out.println(
                "----------------------------------------------");

        System.out.println(
                "ผลการทดสอบ : "
                        + passed
                        + "/10 PASS");
    }

    // =====================================================
    // FULL WORKFLOW
    // =====================================================

    private static boolean addFullWorkflow(
            WorkflowAlgorithm a) {

        return a.addAction(
                    Action.CALL_RECEIVED)

                &&
                a.addAction(
                    Action.TEAM_ASSIGNED)

                &&
                a.addAction(
                    Action.VEHICLE_DISPATCHED)

                &&
                a.addAction(
                    Action.ARRIVED_AT_SCENE)

                &&
                a.addAction(
                    Action.CASE_CLOSED);
    }

    private static int printTest(
            int number,
            boolean pass) {

        System.out.println(
                "Test "
                        + number
                        + " : "
                        + (pass
                        ? "PASS"
                        : "FAIL"));

        return pass ? 1 : 0;
    }

    // =====================================================
    // PERFORMANCE TEST
    // =====================================================

    private static void runPerformanceTest() {

        int[] sizes = {
                100,
                1000,
                10000,
                50000
        };

        int rounds = 5;

        System.out.println(
                "==============================================");

        System.out.println(
                "             PERFORMANCE TEST");

        System.out.println(
                "==============================================");

        System.out.println(
                "ทดสอบทั้งหมด "
                        + rounds
                        + " รอบ");

        System.out.println();

        System.out.printf(
                "%-8s %-38s %-15s %-10s %-10s %-15s %-10s%n",
                "n",
                "Algorithm",
                "Average(ns)",
                "Push",
                "Pop",
                "Comparisons",
                "Loops"
        );

        System.out.println(
                "--------------------------------------------------------------------------------");

        for (int n : sizes) {

            performanceTest(
                    new Event_Stack(),
                    n,
                    rounds);

            performanceTest(
                    new State_Machine(),
                    n,
                    rounds);
        }
    }

    private static void performanceTest(
            WorkflowAlgorithm a,
            int n,
            int rounds) {

        long totalTime = 0;

        long totalPush = 0;
        long totalPop = 0;
        long totalComparison = 0;
        long totalLoop = 0;

        for (int round = 0;
             round < rounds;
             round++) {

            a.reset();

            /*
             * Warm-up
             */
            for (int i = 0; i < 10; i++) {

                executeWorkflow(a);

                a.reset();
            }

            a.reset();

            long startTime =
                    System.nanoTime();

            for (int i = 0;
                 i < n;
                 i++) {

                executeWorkflow(a);

                a.reset();
            }

            long endTime =
                    System.nanoTime();

            totalTime +=
                    endTime - startTime;

            totalPush +=
                    a.getPushCount();

            totalPop +=
                    a.getPopCount();

            totalComparison +=
                    a.getComparisonCount();

            totalLoop +=
                    a.getLoopCount();
        }

        long averageTime =
                totalTime / rounds;

        long averagePush =
                totalPush / rounds;

        long averagePop =
                totalPop / rounds;

        long averageComparison =
                totalComparison / rounds;

        long averageLoop =
                totalLoop / rounds;

        System.out.printf(
                "%-8d %-38s %-15d %-10d %-10d %-15d %-10d%n",
                n,
                a.getName(),
                averageTime,
                averagePush,
                averagePop,
                averageComparison,
                averageLoop
        );
    }

    // =====================================================
    // EXECUTE WORKFLOW
    // =====================================================

    private static void executeWorkflow(
            WorkflowAlgorithm a) {

        a.addAction(
                Action.CALL_RECEIVED);

        a.addAction(
                Action.TEAM_ASSIGNED);

        a.addAction(
                Action.VEHICLE_DISPATCHED);

        a.addAction(
                Action.ARRIVED_AT_SCENE);

        a.addAction(
                Action.CASE_CLOSED);

        /*
         * Undo 2 ครั้ง
         */
        a.undo();
        a.undo();

        /*
         * Redo 1 ครั้ง
         */
        a.redo();

        /*
         * เพิ่ม Action ใหม่
         *
         * หลัง Redo บางส่วน
         * Redo Stack ต้องถูกล้าง
         */
        a.addAction(
                Action.VEHICLE_DISPATCHED);
    }

    // =====================================================
    // STATUS
    // =====================================================

    private static void showStatus() {

        WorkflowData data =
                algorithm.getData();

        System.out.println();

        System.out.println(
                "==============================================");

        System.out.println(
                "                    STATUS");

        System.out.println(
                "==============================================");

        System.out.println(
                "Algorithm : "
                        + getAlgorithmName());

        System.out.println(
                "Current State : "
                        + data.getCurrentState());

        System.out.println();

        showEventStack(data);

        System.out.println();

        showRedoStack(data);

        System.out.println(
                "==============================================");
    }

    // =====================================================
    // EVENT STACK
    // =====================================================

    private static void showEventStack(
            WorkflowData data) {

        List<Action> events =
                data.eventBottomToTop();

        System.out.println(
                "Event Stack:");

        System.out.println(
                "TOP");

        if (events.isEmpty()) {

            System.out.println(
                    "  [ว่าง]");

        } else {

            for (int i = events.size() - 1;
                 i >= 0;
                 i--) {

                System.out.println(
                        "  "
                                + events.get(i));
            }
        }

        System.out.println(
                "BOTTOM");
    }

    // =====================================================
    // REDO STACK
    // =====================================================

    private static void showRedoStack(
            WorkflowData data) {

        List<Action> redo =
                data.redoTopToBottom();

        System.out.println(
                "Redo Stack:");

        System.out.println(
                "TOP");

        if (redo.isEmpty()) {

            System.out.println(
                    "  [ว่าง]");

        } else {

            for (Action action : redo) {

                System.out.println(
                        "  "
                                + action);
            }
        }

        System.out.println(
                "BOTTOM");
    }

    // =====================================================
    // ACTION CONVERTER
    // =====================================================

    private static Action convertAction(
            int choice) {

        switch (choice) {

            case 1:
                return Action.CALL_RECEIVED;

            case 2:
                return Action.TEAM_ASSIGNED;

            case 3:
                return Action.VEHICLE_DISPATCHED;

            case 4:
                return Action.ARRIVED_AT_SCENE;

            case 5:
                return Action.CASE_CLOSED;

            default:
                return null;
        }
    }

    // =====================================================
    // ALGORITHM NAME
    // =====================================================

    private static String getAlgorithmName() {

        if (algorithm instanceof Event_Stack) {

            return "A - Event Stack";

        } else {

            return "B - Event Stack + State Machine";
        }
    }

    // =====================================================
    // INPUT VALIDATION
    // =====================================================

    private static int readInt(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "กรุณาป้อนตัวเลขเท่านั้น");
            }
        }
    }
}