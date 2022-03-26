import java.util.Hashtable;

public class InstructionDecode {
	
	private RegisterFile RF;
	public int PC; 
	
	public InstructionDecode() {
		RF = new RegisterFile();
		RF.loadValues();
	}
	
	
	
	public Hashtable<String,Object> InstDecode(String instructionFetched, int PC){
		/*
		 * input: the instruction
		 * output: control signals, readdata1 (as int), readdata2 (as int), PC passed on from instruction fetched (getter)
		 */
		
		//first step: decode all parts. 
		System.out.println("Instruction decode stage:");
		CPU.finalOutput+="Instruction decode stage:\n";
		this.PC = PC;
		
		String opcode = instructionFetched.substring(0,6);
		String rs = instructionFetched.substring(6,11);
		String rt = instructionFetched.substring(11,16);
		String rd = instructionFetched.substring(16,21);
		String shamt = instructionFetched.substring(21,26);
		String funct = instructionFetched.substring(26,32);
		String immNotExtend = instructionFetched.substring(16,32);
		
		
		Hashtable<String,String> signals = ContUnit(opcode);
		
		int[] readdata_1_2 = RF.readValues(Integer.parseInt(rs,2),Integer.parseInt(rt,2),Integer.parseInt(rd,2));
		String extended = SignExtend(immNotExtend);
		
		
		Hashtable<String,Object> ret = new Hashtable<String, Object>();
		ret.put("signals", signals);
		ret.put("readdata1", readdata_1_2[0]);
		ret.put("readdata2", readdata_1_2[1]);
		ret.put("PCby4",PC);
		ret.put("funct", funct);
		ret.put("extended", extended);
		ret.put("rt",rt);
		ret.put("rd",rd);
		
		
		System.out.println("Inputs of InstDecode: (A) instruction: " + instructionFetched + " | (B) PC+by4: " + PC +"\nOutputs of InstDecode:"
				+ "(A) control signals: refer to contUnit output (the helper) | (B) ReadData1: " + ret.get("readdata1") + " | (C) ReadData2: "
				+ret.get("readdata2") + " | (D) PC: " + PC + " | (E) for sign extension, check SignExtend."+
				" | (F) rt: " + rt + " (G) rd: "+ rd +"\n-------------------------------------------------------------------------------");
		CPU.finalOutput+=("Inputs of InstDecode: (A) instruction: " + instructionFetched + " | (B) PC+by4: " + PC +"\nOutputs of InstDecode:"
				+ "(A) control signals: refer to contUnit output (the helper) | (B) ReadData1: " + ret.get("readdata1") + " | (C) ReadData2: "
				+ret.get("readdata2") + " | (D) PC: " + PC + " | (E) for sign extension, check SignExtend."+
				" | (F) rt: " + rt + " (G) rd: "+ rd +"\n-------------------------------------------------------------------------------\n");
		
		return ret;
		
	}
	
	
	
	
	//helpers
	
	//but this one is called independently
	public String SignExtend(String immNotExtend) {
		/**input 16 bit immiedaite
		 * output: sign extend
		 */
		String extended = (immNotExtend.charAt(0)=='0')?("0000000000000000"+immNotExtend) : ("1111111111111111"+immNotExtend) ;
		
		
		System.out.println("Inputs of SignExtend: (A) immediate (16 bits): " + immNotExtend + "\nOutputs of SignExtend: (A) immediate (32 bits): "
				+ extended + "\n-------------------------------------------------------------------------------");
		CPU.finalOutput+=("Inputs of SignExtend: (A) immediate (16 bits): " + immNotExtend + "\nOutputs of SignExtend: (A) immediate (32 bits): "
				+ extended + "\n-------------------------------------------------------------------------------\n");
		return extended;
	}
	
	public Hashtable<String,String> ContUnit(String opcode){
		/*
		 * input: opcode from the instdecode
		 * output: all 8 ALU control signals
		 */
		
		Hashtable<String,String> signals = new Hashtable<String,String>();
		
		switch(opcode) {
		    //R TYPE
			case "000000": 
				signals.put("RegDst","1");
				signals.put("Branch","0");
				signals.put("MemRead","0");
				signals.put("MemtoReg","0");
				signals.put("ALUOp","10");
				signals.put("MemWrite","0");
				signals.put("ALUSrc","0");
				signals.put("RegWrite","1");
				break;
			
			//LW
			case "100011":
				signals.put("RegDst","0");
				signals.put("Branch","0");
				signals.put("MemRead","1");
				signals.put("MemtoReg","1");
				signals.put("ALUOp","00");
				signals.put("MemWrite","0");
				signals.put("ALUSrc","1");
				signals.put("RegWrite","1");
				break; 
			
			//SW
			case "101011": 
				signals.put("RegDst","x");
				signals.put("Branch","0");
				signals.put("MemRead","0");
				signals.put("MemtoReg","x");
				signals.put("ALUOp","00");
				signals.put("MemWrite","1");
				signals.put("ALUSrc","1");
				signals.put("RegWrite","0");
				break;
				
			//BEQ
			case "000100": 
				signals.put("RegDst","x");
				signals.put("Branch","1");
				signals.put("MemRead","0");
				signals.put("MemtoReg","x");
				signals.put("ALUOp","01");
				signals.put("MemWrite","0");
				signals.put("ALUSrc","0");
				signals.put("RegWrite","0");
				break;
			
			default: CPU.finalOutput+="invalid opcode"; System.out.println("invalid opcode");return null;
		}
		
		System.out.println("Inputs of ContUnit: " + "(A) opcode: " + opcode + "\nOutputs of ContUnit: (A) RegDst: " + signals.get("RegDst") + 
				" | (B) Branch: " + signals.get("Branch") + " | (C) MemRead: " + signals.get("MemRead") + " | (D) MemToReg: " + signals.get("MemtoReg")
				+ " | (E) ALUOp: " + signals.get("ALUOp") + " | (F) MemWrite: " + signals.get("MemWrite") + " | (G) ALUSrc: " + signals.get("ALUSrc")+
				" | (H) RegWrite: " + signals.get("RegWrite") + "\n-------------------------------------------------------------------------------");
		
		CPU.finalOutput+=("Inputs of ContUnit: " + "(A) opcode: " + opcode + "\nOutputs of ContUnit: (A) RegDst: " + signals.get("RegDst") + 
				" | (B) Branch: " + signals.get("Branch") + " | (C) MemRead: " + signals.get("MemRead") + " | (D) MemToReg: " + signals.get("MemtoReg")
				+ " | (E) ALUOp: " + signals.get("ALUOp") + " | (F) MemWrite: " + signals.get("MemWrite") + " | (G) ALUSrc: " + signals.get("ALUSrc")+
				" | (H) RegWrite: " + signals.get("RegWrite") + "\n-------------------------------------------------------------------------------\n");
		
		return signals;
		
	}
	
	public void writeToRF(int val, int reg) {
		RF.loadValueToRegister(val,reg);
	}
	
	public void setRegWrite(String x) {
		RF.setRegWrite(x);
	}
	
	public static void main(String [] args) {
	}
}
