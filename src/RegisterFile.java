import java.util.Arrays;

public class RegisterFile {
	
	int rs; //25-21 source
	int rt; //20-16 source or destination
	int rd; //15-11 destination, for writes
	int [] registers; //to keep track of 32 registers and their data
	String regWrite;
	
	
	public RegisterFile() {
		registers = new int[32]; //initialize array of registers
		//by default, all constituents are 0.
	}
	
	
	public void loadValueToRegister(int value, int register) {
		if(regWrite.equals("1")) {
			
			if (register!=0) {
				registers[register] = value;
				String disp = "Loaded value " + value + " to register " + register  + ".";
				System.out.println(disp);
				CPU.finalOutput+=(disp+"\n");
			}
			
			else {
				System.out.println("Cannot write to register 0. Value remains 0.");
				CPU.finalOutput+="Cannot write to register 0. Value remains 0.";
			}
			
		}
		
	}
	
	public int readValueAt(int value) {
		return registers[value];
	}
	
	public void showState() {
		System.out.println("Register File State");
		System.out.println(Arrays.toString(registers));
		System.out.println("-------------------------------------------------------------------------------");

		CPU.finalOutput+= ("Register File State\n"+Arrays.toString(registers)+ "\n-------------------------------------------------------------------------------\n");
	}
	
	public int[] readValues(int rs, int rt, int rd) {
		int[] ret = new int[2];
		ret[0] = readValueAt(rs);
		ret[1] = readValueAt(rt);
		return ret;
	}
	
	
	public void loadValues() {
		for (int i = 0; i<32;i++) {
			registers[i] = i+2;
		}
		
		registers[11] = registers[13];
		
		showState();
	}
	
	public void setRegWrite(String x) {
		this.regWrite = x;
		if(x.equals("1")) {
			String disp = "regWrite set successfully to 1";
			System.out.println(disp);
			CPU.finalOutput+=(disp+"\n");
		}
			
	}
	
}
