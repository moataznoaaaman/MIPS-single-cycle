import java.util.Hashtable;

public class Execute {
	
	/*
	 * I'm not really sure where to add the pc+4 to the branch address, in the memory stage or the execution stage? Since I 
	 * take the branch address as input to the MemAccess method ,do I update the pc to pc+4+branch address here according to 
	 * branch and zero flag and take the pc+4 as input to MemAccess as well or is this wrong?? ccorrect
	 */

	//1) You should multiply by 4 the sign extended value.

	//2) You use the sign extended value in the execute. 
	
	//TAKE CARE: JAVA DOES NOT SUPPORT 2S COMPLEMENT!
	
	/*
	 * By looking at the data-path figure shown above, the PC+4 is the output from the instruction fetch stage and you will need to use this value 
	 * in the execute stage in order to add it to the Branch Address (The adder found above the ALU in the execute stage). 
	 * In the execute method, you will handle the implementation of the ALU as well as the Branch Address which needs the PC+4 (required as an input).
	 * 
	 *  do method ALU control in Execute class ?


	 */
	
	//find the ALU 4 bits operation here

	ALU alu;
	int PC;
	
	public Execute() {
		alu = new ALU();
	}
	
	public Hashtable<String, Object> Execute(String ALUop, String ALUsrc, int readData1, int readData2, String signExtend, int PCby4, String funct){
		/*
		 * output: ALU result, zero flag, branchAddressResult, readdata2, PCby4
		 * 
		 */
		String display = "Execute Stage: \n";

		this.PC = PCby4;
		Hashtable<String, Object> ret = new Hashtable<String, Object>();
	
		
		//deal with the immediate
		int number = CPU.twosComplementConvert(signExtend);
		
		int ALUnum = number; //to be used as immediate for ALU.
		
		//in case of branch address - we need not worry about the 32nd bit. Java's range is signed, just like our application. Please not that
		//java uses 2s complement.
		int branchAdd = number << 2; //shift the number obtained by 2 bits.
		
		
	    //determine ALU operands	
		
		int operand1 = readData1;
		int operand2 = (ALUsrc=="0")? readData2 : ALUnum;
		
		
		//do ALUControl
		String ALUoperation = ALUControl(ALUop, funct);
		
		//find ALU result.
		int result = alu.ALUEvaluator(ALUoperation,operand1,operand2);
		int zflag = alu.getZFlag();
		
		//find branching result:
		
		int branchResult = PC + branchAdd;
		
		
		/*
		 * output: ALU result, zero flag, branchAddressResult, readdata2, PCby4
		 * 
		 */
		
		ret.put("ALUresult", result);
		ret.put("ZFlag", zflag);
		ret.put("branchAddressResult", branchResult);
		ret.put("PCby4",PC);
		
		//inputs: String ALUop, String ALUsrc, int readData1, int readData2, String signExtend, int PCby4, String funct
		display+="Inputs of Execute: (A) ALUop: " + ALUop + " | (B) ALUsrc: " + ALUsrc + " | (C) readData1: " +  readData1
				+ " | (D) readData2: " + readData2 + " | (E) signExtend: " + signExtend + " | (F) PC+by4: " +  PCby4 + " | (G) funct: " + funct 
				+"\nOutputs of Execute: (A) ALUresult: " + ret.get("ALUresult") + " | (B) Zlfag: " + ret.get("ZFlag") + " | (C) branchAddressResult: "
				+ ret.get("branchAddressResult") +  " | (D) PC+by4: " + ret.get("PCby4") + 
				"\n-------------------------------------------------------------------------------";
		
		System.out.println(display);
		CPU.finalOutput+=(display+"\n");
		
		return ret;
	}
	
	
	public String ALUControl(String ALUOp, String funct) {
		
		String op = "";
		if(ALUOp.equals("00")||ALUOp.equals("01")) {
			switch(ALUOp) {
				case "00": op = "0010";break;
				case "01": op = "0110";break;
				default: System.out.println("ALU control error: ALUOp incorrectly set."); CPU.finalOutput+="ALU control error: ALUOp incorrectly set.\n";
						op = null;
			}
		}
		
		else {
			//so OP is 10
			if(ALUOp.equals("10")) {
				switch(funct) {
					case "100000":op="0010";break;
					case "100010":op="0110";break;
					case "100100":op="0000";break;
					case "100101":op="0001";break;
					case "101010":op="0111";break;
					default:System.out.println("ALU control error: funct incorrectly set."); CPU.finalOutput+="ALU control error: funct incorrectly set.\n";
					op = null;
				}
			}
		}
		
		return op;
		
	}
	
	
	public static void main(String[] args) {
		
//		String binaryString = Integer.toBinaryString(2);
//		String withLeadingZeros = String.format("%8s", binaryString).replace(' ', '0');
//		//System.out.println(withLeadingZeros);
//		
//		int num = -2147483647;
//		num = num <<2;
//		
//		
//		System.out.println(Integer.toBinaryString(-2147483647));

	}
}
