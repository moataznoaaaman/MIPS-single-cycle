import java.util.Arrays;

public class DataMemory {

	int[] dataAddresses;
	
	public DataMemory(int size){
		dataAddresses = new int[size*4];
	}
	
	public void writeData(int data, int memAddress) {
		dataAddresses[memAddress] = data;
		System.out.println("Data " + data + " was successfully written to address " + memAddress +".");
		CPU.finalOutput+=("Data " + data + " was successfully written to address " + memAddress +"."+"\n");
		//System.out.println(Arrays.toString(dataAddresses));
	}
	
	public int readData(int memAddress) {
		return dataAddresses[memAddress];
	}
	
	public void loadValues() {
		for(int i = 0; i<dataAddresses.length;i++) {
			dataAddresses[i] = i + 3;
		}
		
		showState();
	}
	
	public void showState() {
		System.out.println("Data Memory state:");
		System.out.println(Arrays.toString(dataAddresses));
		System.out.println("-------------------------------------------------------------------------------");
		CPU.finalOutput+=("Data Memory state: " +"\n"+Arrays.toString(dataAddresses) + "\n----------"
				+ "---------------------------------------------------------------------\n");
	}
	
	
}
