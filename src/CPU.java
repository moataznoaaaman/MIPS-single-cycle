import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class CPU {
	
	public static String finalOutput = ""; //write this to a file.

	/*
	 * some important notes:
	 * 1. you see all stages because this is the single cycle implementation - that is, we pass by each stage, choosing only what we need as per 
	 * the control units. 
	 * 2. the format used is binary instructions as we see it, the opcode being to the left, and the funct to the right. Therefore, negative indexing
	 * (MIPS goes from 31 to 0) was not used since Java doesn't support it, and the range was "shifted" by 32 bits to be from 0 to 31.
	 * 3. please see the finalOutput.txt file. It is written when you run this class. Check the output from there, not from the console. It's neater.
	 * 
	 */
	
	
	
	public static void main(String[] args) {
		
		InstructionFetch IF = new InstructionFetch();
		InstructionDecode ID = new InstructionDecode();
		Execute EXEC = new Execute();
		MemoryAccess M = new MemoryAccess(IF);
		WriteBack WB = new WriteBack(ID);
		
		int i = 0;
		
		while(!IF.done()) { //TODO: handle branching! (setPC should take care of that)
			
			
			//test cases:
			//make sure all added instructions are indeed added and PC is corrected.
			//program:
			/*
			 * 000000 01001 01010 01000 00000 100000 add 
			 * 000000 01000 00011 00010 00000 100010 sub 
			 * 000100 01011 01101 0000000000010000 beq
			 * 100011 00001 00011 0000000000000100 lw
			 * 101011 00001 00011 0000000000000100 sw
			 *TODO: ADD these: and, or, slt 
			 */
			
			
			
			//clock cycle?
			System.out.println("\n\n-------------------------------------------------------------------------------\nClock Cycle " + i);
			CPU.finalOutput+=("\n\n-------------------------------------------------------------------------------\nClock Cycle " + i + "\n");
			i++;
			
			
			
			
			
			//instruction fetch.
			Hashtable<String, Object> IFresult = IF.InstFetch(IF.getPC());
			//outputs
			String instruction = (String) IFresult.get("Instruction");
			int IF_PC = (Integer) IFresult.get("PCby4");
			
			
			
			
			
			
			//instruction decode.
			Hashtable<String,Object> IDresult = ID.InstDecode(instruction,IF_PC);
			//outputs
			Hashtable<String,String> signals = (Hashtable)IDresult.get("signals");
			int readdata1 = (Integer)IDresult.get("readdata1");
			int readdata2 =(Integer)IDresult.get("readdata2");
			int ID_PC   =(Integer)IDresult.get("PCby4"); 
			String funct = (String)IDresult.get("funct"); 
			String extended = (String)IDresult.get("extended");
			String rt = (String)IDresult.get("rt");
			String rd = (String)IDresult.get("rd");
			//discrete signals
			String regDst = (String) signals.get("RegDst");
			String Branch = (String)signals.get("Branch");
			String MemRead = (String)signals.get("MemRead");
			String MemtoReg = (String)signals.get("MemtoReg");
			String ALUOp = (String)signals.get("ALUOp");
			String MemWrite = (String)signals.get("MemWrite");
			String ALUSrc = (String)signals.get("ALUSrc");
			String RegWrite = (String)signals.get("RegWrite");
			
			
			
			
			
			//execute.
			Hashtable<String,Object> EXECresult = EXEC.Execute(ALUOp,ALUSrc,readdata1,readdata2,extended, ID_PC,funct);
			//outputs
			int ALUresult = (Integer) EXECresult.get("ALUresult");
			int ZFlag = (Integer) EXECresult.get("ZFlag");
			int branchAddressResult = (Integer) EXECresult.get("branchAddressResult");
			int EXEC_PC = (Integer) EXECresult.get("PCby4");
			
			
			
			
			
			
			
			//Memory Access.
			Hashtable<String, Integer> Mresult = M.MemAccess(ALUresult, readdata2, extended, ZFlag, branchAddressResult, 
					 MemWrite,  MemRead,  Branch);
			//outputs
			ALUresult = (Integer) Mresult.get("ALUresult");
			int ReadDataFromMem = (Integer) Mresult.get("ReadDataFromMem");
			
			
			
			
			
			
			
			//Write Back.
			int writtenData = WB.writeBack(ALUresult, ReadDataFromMem,  MemtoReg, regDst, RegWrite, rt, rd);
			
		}
		
		writefile("final_output.txt", finalOutput);
		
	}

	
	public static void writefile(String name, String data) {
		try {
			BufferedWriter BW = new BufferedWriter(new FileWriter(name));
			BW.write(data);
			BW.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	public static int twosComplementConvert(String x) {
		if(x.charAt(0)=='0') {
			return Integer.parseInt(x,2);
		}
		
		else {
			//step one: invert.
			String temp = "";
			for (int i = 0; i<32;i++) {
				if((x.charAt(i))=='1') {
					temp+= "0";
				}
				
				else {
					temp+="1";
				}
			}
			
			//no it's no longer negative.
			//step 2: add 1.
			int add = Integer.parseInt(temp,2);
			int temp2 = add+1;
			
			//step 3: mult by -1
			int answer = temp2*-1;
			
			return answer;
		}
		
		
		
	}
	
}
