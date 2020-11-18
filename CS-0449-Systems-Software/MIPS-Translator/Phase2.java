import java.util.LinkedList;
import java.util.List;

public class Phase2 {

    private static LinkedList<LinkedList<Integer>> mappings = new LinkedList<>();
    private static LinkedList<Instruction> resolvedTals = new LinkedList<>();

    public static List<Instruction> resolve_addresses(List<Instruction> unresolved, int first_pc) {

        int PC = first_pc;

        for (int i = 0; i < unresolved.size(); i++) { //PASS 1
            LinkedList<Integer> map = new LinkedList<>();
            Instruction currentUnresolved = unresolved.get(i);
            if (currentUnresolved.label_id != 0) { //If there is a label
                map.add(first_pc);
                map.add(currentUnresolved.label_id);
            }
            first_pc += 4;
            if (map.size() != 0) { // If instruction is a branch
                mappings.add(map);
            }
        }
        for (int i = 0; i < unresolved.size(); i++) { //PASS 2
            Instruction current = unresolved.get(i);
            if (current.instruction_id == 5 || current.instruction_id == 100 || //If instruction is a blt, beq, bge, or bne
                    current.instruction_id == 101 || current.instruction_id == 6) { //Every non-zero branch_label instruction
                for (int j = 0; j < mappings.size(); j++) {
                    LinkedList<Integer> currentMapping = mappings.get(j);
                    if (currentMapping.get(1) == current.branch_label) {
                        int immAddr = (currentMapping.get(0) - PC) / 4 - 1;
                        Instruction newInstruction = new Instruction(current.instruction_id, current.rd, current.rs,
                                current.rt, immAddr, current.jump_address, current.shift_amount, current.label_id,
                                current.branch_label);
                        current = newInstruction;
                    }
                }
            }
            PC += 4;
            resolvedTals.add(current);
        }
        return resolvedTals;
    }

}