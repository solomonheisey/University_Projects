
public class Instruction {
    int instruction_id;   // id indicating the instruction
    int rd;            // register number
    int rs;            // register number
    int rt;            // register number
    int immediate;     // immediate, may use up to 32 bits
    int jump_address;  // jump address  (not used, so it is always 0)
    int shift_amount;  // shift amount (not used, so it is always 0)
    int label_id;      // 0=no label on this line; nonzero is a unique id
    int branch_label;  // label used by branch or jump instructions

    public Instruction(int instruction_id, int rd, int rs, int rt, int immediate, int jump_address, int shift_amount, int label_id, int branch_label) {
        this.instruction_id = instruction_id;
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
        this.jump_address = jump_address;
        this.shift_amount = shift_amount;
        this.label_id = label_id;
        this.branch_label = branch_label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        if (instruction_id != that.instruction_id) return false;
        if (rd != that.rd) return false;
        if (rs != that.rs) return false;
        if (rt != that.rt) return false;
        if (immediate != that.immediate) return false;
        if (jump_address != that.jump_address) return false;
        if (shift_amount != that.shift_amount) return false;
        if (label_id != that.label_id) return false;
        return branch_label == that.branch_label;

    }

    @Override
    public int hashCode() {
        int result = instruction_id;
        result = 31 * result + rd;
        result = 31 * result + rs;
        result = 31 * result + rt;
        result = 31 * result + immediate;
        result = 31 * result + jump_address;
        result = 31 * result + shift_amount;
        result = 31 * result + label_id;
        result = 31 * result + branch_label;
        return result;
    }

    public Instruction copy() {
        return new Instruction(instruction_id, rd, rs, rt, immediate, jump_address, shift_amount, label_id, branch_label);
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "instruction_id=" + instruction_id +
                ", rd=" + rd +
                ", rs=" + rs +
                ", rt=" + rt +
                ", immediate=" + immediate +
                ", jump_address=" + jump_address +
                ", shift_amount=" + shift_amount +
                ", label_id=" + label_id +
                ", branch_label=" + branch_label +
                '}';
    }
}
