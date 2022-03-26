import java.util.Arrays;

import java.util.Hashtable;

public class InstructionFetch {
	
	//We need an instruction fetch component + an adder component here.
	
	//phase tasks:
	/*
	 * for all instructions:
	 * 1. fetch the instruction
	 * 2. increment PC by 4
	 */
	
	private InstructionMemory IM;
	public int PC;
	

	public InstructionFetch() {
		IM = new InstructionMemory(256); //set up a 256-word instruction memory.
		IM.loadProgram();
		PC = IM.getPC();
	}
	
	
	//TODO: REQUIRED FUNCTIONS: and justify indexing 
	
	public int getPC() {
		return PC;
	}
	
	public void setPC(int x) {
		this.PC = x;
		IM.setPC(x);
	}
	
	public Hashtable<String, Object> InstFetch(int PCaddress){
		/*
		 * INPUTS: the address of instruction (PC) 
		 * OUTPUTS: instruction, PC+4
		 */
		System.out.println("Instruction fetch stage:");
		CPU.finalOutput+="Instruction fetch stage:\n";
		
		String instruction = IM.getInstruction(); //gets instruction @ PC address.
		ProgCount(); //increments PC by 4 in both memory and for the stage.
		
		Hashtable<String,Object> output = new Hashtable<String,Object>();
		output.put("Instruction", instruction);
		output.put("PCby4", PC);
		
		
		////////////////////////////////////////////////////////
		
		System.out.println("Inputs of InstFetch: (A) PCaddress: " + PCaddress + "\nOutputs of InstFetch: (A) Instruction: " +
		(String)output.get("Instruction")+ " | (B) PC+by4: " + output.get("PCby4"));
		System.out.println("-------------------------------------------------------------------------------");
		CPU.finalOutput+=("Inputs of InstFetch: (A) PCaddress: " + PCaddress + "\nOutputs of InstFetch: (A) Instruction: " +
				(String)output.get("Instruction")+ " | (B) PC+by4: " + output.get("PCby4") + "\n-------------------------------------------------------------------------------\n");
		
		return output;
	}
	
	public void ProgCount() {
		/*
		 * increments PC by 4 - use as helper for instfetch
		 */
		PC+=4;
		IM.setPC(PC);
		
	}
	
	public boolean done() {
		return IM.done();
	}
	
	//TODO: print everything in binary!
	
	
	

}
