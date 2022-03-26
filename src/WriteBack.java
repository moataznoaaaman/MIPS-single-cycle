
public class WriteBack {
	
	public InstructionDecode ID;
	
	public WriteBack(InstructionDecode ID) {
		this.ID = ID;
	}
	
	public int writeBack(int ALUResult, int ReadData, String memToReg, String regDst, String regWrite, String rt, String rd) {
		int writeData = -1; //meaning no data was written
		//output will be the data to be written into the write register.
		System.out.println("Write Back Stage:");
	    CPU.finalOutput+="Write Back Stage: \n";
		String display="";
		
		if(regWrite.equals("1")) {
			
			if(memToReg.equals("1")) {
				if(regDst.equals("0")) {
					ID.setRegWrite("1");
					ID.writeToRF(ReadData, Integer.parseInt(rt,2));
					writeData = ReadData;
					ID.setRegWrite("0");
				}
			}
			
			else if (memToReg.equals("0")) {
				if(regDst.equals("1")) {
					ID.setRegWrite("1");
					ID.writeToRF(ALUResult, Integer.parseInt(rd,2));
					writeData = ALUResult;
					ID.setRegWrite("0");

				}
			}
			
			else {
				display+="No data was written back to the register file.\n";
		    }
		}
			
		else {
				display+="No data was written back to the register file.\n";
		}
		
		
		
		
		display+=("Inputs of WriteBack: (A) ALUResult: " + ALUResult + " | (B) ReadDataFromMem: " + ReadData + " | (C) memToReg: " + memToReg
				+ " | (D) regDst: " + regDst + " | (E) regWrite: " + regWrite + "\nOutputs of WriteBack: (A) DataWritten: " + writeData +
				"\n-------------------------------------------------------------------------------");
		
		System.out.println(display);
		CPU.finalOutput+=(display+"\n");
		
		return writeData;
		
		
	}

}
