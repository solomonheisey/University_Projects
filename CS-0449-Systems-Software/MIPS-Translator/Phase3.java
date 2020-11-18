import java.lang.Integer;
import java.util.LinkedList;
import java.util.List;

public class Phase3 {

    private static List<Integer> binaryOutput = new LinkedList<>();

    public static List<Integer> translate_instructions(List<Instruction> tals) { //Method searches for non-pseudo
        for ( int i = 0; i < tals.size(); i++ ) { // instructions and creates binary output based on instruction id
            Instruction currentInstruction = tals.get(i); //blt and bge are not included since they are always pseudo
            int id = currentInstruction.instruction_id;

            if(id == 1){ //if addiu
                int num = currentInstruction.immediate ^ (currentInstruction.rt << 16);
                int num2 = currentInstruction.rs << 21;
                int num3 = (33 << 26) ^ (num ^ num2);
                String tempHex = Integer.toHexString(num3);
                binaryOutput.add(Integer.parseInt(tempHex, 16)); //radix 16 because hexidecimal
            }
            else if (id == 2) //if addu
                RTYPE(33, currentInstruction); //addu is a r-type instruction
            else if(id == 3) //if or
                RTYPE(37,currentInstruction);
            else if(id == 5){ //if beq
                int num = currentInstruction.immediate ^ (currentInstruction.rt << 16);
                int num5 = num ^ (currentInstruction.rs << 21);
                int num3 = (4 << 26) ^ num5;
                String tempHex = Integer.toHexString(num3);
                binaryOutput.add(Integer.parseInt(tempHex, 16));
            }
            else if(id == 6){ //if bne
                int immediateBit = currentInstruction.immediate ^ 0xFFFF0000;
                int num = immediateBit ^ (currentInstruction.rt << 16);
                int num2 = currentInstruction.rs << 21;
                int num5 = num ^ num2;
                int num3 = (5 << 26) ^ num5;
                String tempHex = Integer.toHexString(num3);
                binaryOutput.add(Integer.parseInt(tempHex, 16));
            }
            else if(id == 8) //if slt
                RTYPE(42,currentInstruction); //slt is a r-type instruction
            else if(id == 9){ //if lui
                int num = currentInstruction.immediate ^ (currentInstruction.rt << 16);
                int num2 = currentInstruction.rs << 21;
                int num5 = num ^ num2;
                int num3 = (15 << 26) ^ num5;
                String tempHex = Integer.toHexString(num3);
                binaryOutput.add(Integer.parseInt(tempHex, 16));
            }
            else{ //if ori
                int num = 0 ^ (currentInstruction.rt << 16);
                int num2 = currentInstruction.rs << 21;
                int num5 = num ^ num2;
                int num3 = (13 << 26) ^ num5;
                String tempHex = Integer.toHexString(num3);
                binaryOutput.add(Integer.parseInt(tempHex, 16));
            }
        }
        return binaryOutput;
    }

    public static void RTYPE(int function, Instruction currentInstruction){
        int num6 = (currentInstruction.rd << 11) ^ function;
        int num7 = currentInstruction.rt << 16;
        int num = num6 ^ num7;
        int num2 = currentInstruction.rs << 21;
        int num5 = num ^ num2;
        String tempHex = Integer.toHexString(num5);
        binaryOutput.add(Integer.parseInt(tempHex, 16));
    }
}

