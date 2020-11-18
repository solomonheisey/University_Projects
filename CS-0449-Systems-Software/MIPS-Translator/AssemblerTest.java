
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class AssemblerTest {
    private static int MARS_TEXT_SEGMENT_START = 0x00400000;

    private static void testHelper(List<Instruction> input, Instruction[] expectedP1, Instruction[] expectedP2, Integer[] expectedP3) {
        // Phase 1
        List<Instruction> tals = Phase1.mal_to_tal(input);
        assertArrayEquals(expectedP1, tals.toArray());

        // Phase 2
        List<Instruction> resolved_tals = Phase2.resolve_addresses(tals, MARS_TEXT_SEGMENT_START);
        assertArrayEquals(expectedP2, resolved_tals.toArray());

        // Phase 3
        List<Integer> translated = Phase3.translate_instructions(resolved_tals);
        assertArrayEquals(expectedP3, translated.toArray());
    }

    @Test
    public void test1() {
        // test 1
        List<Instruction> input = new LinkedList<Instruction>();
        // label1: addu $t0, $zero, $zero
        input.add(new Instruction(2, 8, 0, 0, 0, 0, 0, 1, 0));
        // addu $s0, $s7, $t4
        input.add(new Instruction(2,16,23,12,0,0,0,0,0));
        // blt  $s0,$t0,label1
        input.add(new Instruction(100,0,16,8,0,0,0,0,1));
        // addiu $s1,$s2,0xF00000
        input.add(new Instruction(1, 0, 18, 17, 0xF00000, 0, 0, 0, 0));

        // Phase 1
        Instruction[] phase1_expected = {
                new Instruction(2,8,0,0,0,0,0,1,0), // label1: addu $t0, $zero, $zero
                new Instruction(2, 16, 23,12,0,0,0,0,0), // addu $s0, $s7, $t4
                new Instruction(8, 1,16,8,0,0,0,0,0),  // slt $at,$s0,$t0
                new Instruction(6, 0,1,0,0,0,0,0,1),     // bne $at,$zero,label1
                new Instruction(9, 0,0,1,0x00F0,0,0,0,0), // lui $at, 0x00F0
                new Instruction(10,0,1,1,0x0000,0,0,0,0), // ori $at, $at 0x0000
                new Instruction(2,17,18,1,0,0,0,0,0) // addu $s1,$s2,$at
        };

        // Phase 2
        Instruction[] phase2_expected = {
                new Instruction(2,8,0,0,0,0,0,1,0),
                new Instruction(2,16,23,12,0,0,0,0,0),
                new Instruction(8,1,16,8,0,0,0,0,0),
                new Instruction(6,0,1,0,0xfffffffc,0,0,0,1),
                new Instruction(9,0,0,1,0x00F0,0,0,0,0),
                new Instruction(10,0,1,1,0x0000,0,0,0,0),
                new Instruction(2,17,18,1,0,0,0,0,0)
        };

        // Phase 3
        Integer[] phase3_expected = {
                // HINT: to get these, type the input program into MARS, assemble, and copy the binary values into your test case
                0x00004021,
                0x02ec8021,
                0x0208082a,
                0x1420fffc,
                0x3c0100f0,
                0x34210000,
                0x02418821
        };


        testHelper(input,
                phase1_expected,
                phase2_expected,
                phase3_expected);
    }

    @Test
    public void test2() {

    }
}