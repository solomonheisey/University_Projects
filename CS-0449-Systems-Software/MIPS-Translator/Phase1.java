import java.util.LinkedList;
import java.util.List;

public class Phase1 {

    private static List<Instruction> tal = new LinkedList<>();

    public static List<Instruction> mal_to_tal(List<Instruction> mals) {

        for (int i = 0; i < mals.size(); i++) {
            Instruction instruction = mals.get(i);
            int id = instruction.instruction_id;

            if (id == 100) { //If BLT
                tal.add(new Instruction(8, 1, instruction.rs, instruction.rt, 0, 0,
                        0, 0, 0)); //Create SLT
                tal.add(new Instruction(6, 0, 1, 0, 0 , 0, 0,
                        0, instruction.branch_label)); //Create BNE
            }
            else if ((id == 1) || (id == 10) && (instruction.immediate > 32767)) {
                int upperImmediate = instruction.immediate >>> 16;
                int lowerImmediate = instruction.immediate << 16;
                tal.add(new Instruction(9, 0, 0, 1, upperImmediate, 0, 0,
                        0, 0)); //Create LUI
                tal.add(new Instruction(10, 0, 1, 1, lowerImmediate, 0, 0,
                        0, 0)); //Create ORI
                tal.add(new Instruction(2, instruction.rt, instruction.rs, 1, 0, 0,
                        0, 0, 0)); //Create ADDU
            }
            else if (id == 101) { //If BGE
                tal.add(new Instruction(8, 0, 0, 0, 0, 0, 0,
                        instruction.label_id, 0)); //Create SLT
                tal.add(new Instruction(5, 0, 0, 0, 0, 0, 0,
                        0, instruction.label_id)); //Create BEQ
            }
            else
                tal.add(instruction.copy());
        }
        return tal;
    }
}

