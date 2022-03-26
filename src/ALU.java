import java.util.Scanner;

public class ALU {
	
	private int zflag = 0;
	private int output = 0;
	
	public ALU() {
		
	}
	
	
	public int ALUEvaluator (String Op,int Operand1, int Operand2){
		String opname ="";		
		switch(Op) {
		
		case "0000":
			opname = "AND";
			output = ANDOp(Operand1,Operand2);
			break;
		case "0001":
			opname = "OR";
			output = OROp(Operand1, Operand2);
			break;
		case "0010":
			opname = "ADD";
			output = addOp(Operand1,Operand2);
			break;
		case "0110":
			opname = "SUB";
			output = subOp(Operand1,Operand2);
			break;
		case "0111":
			opname = "SLT";
			output = sltOp(Operand1,Operand2);
			break;
		case "1100":
			opname = "NOR";
			output = NOR(Operand1,Operand2);
			break;
			
		default: 
			System.out.println("Illegal operation."); return -1;
		}
		
		
		
		
		//display
		
		System.out.println("Input: \n1st Operand: "  + Operand1 + "\n2nd Operand: " + Operand2 + "\nOperation: " + Op);
		System.out.println("\nOutput: \nOperation Name: " + opname + "\n1st Operand: "  + Operand1 + "\n2nd Operand: " + Operand2 
				+"\nOutput: " + output + "\nZ-Flag Value: " + zflag);
		
		return output;
		
	}
	
	public int getZFlag() {
		return zflag;
	}

	
	//calculation helpers
	
	public int ANDOp(int a, int b) {
		int temp = a & b;
		zflag = (temp==0)? 1:0;
		return temp;
	}
	
	public int OROp(int a, int b) {
		int temp = a | b;
		zflag = (temp==0)? 1:0;
		return temp;
	}
	
	public int NOR(int a , int b) {
		int temp = (~(a|b));
		zflag = (temp==0)? 1:0;
		return temp;
	}
	
	public int sltOp(int a, int b) {
		int temp = 0;
		if(a<b) {
			temp = 1;
		}
		else {
			temp = 0;
		}
		
		zflag = (temp==0)? 1:0;

		return temp;
		
	}
	
	public int addOp(int a, int b) {
		int temp = (a+b);
		zflag = (temp==0)? 1:0;
		return temp;
	}
	
	public int subOp(int a, int b) {
		int temp = (a-b);
		zflag = (temp==0)? 1:0;
		return temp;
	}
	
}
