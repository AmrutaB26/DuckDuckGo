import java.io.*;
import java.util.*;

public class keywordcounter {

	public static void main(String[] args) throws IOException,FileNotFoundException,IllegalArgumentException {
		// Read filename input from command line arguments and create BufferedReader object for it
		String fileName ="";
		if (args.length > 0) {
			fileName = args[0];
        }
		else {
			throw new IllegalArgumentException("Invalid input format - Please enter a file location");
		}
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		//Create a BufferedWriter object to write output to output_file.txt
		file = new File("output_file.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		int noNodes = 0;
		String st = "";
		boolean flag = true;
		try {
			
			//Hash Table to store keyword as the key and a pointer to a Node corresponding to that keyword in fibonacci heap
			Hashtable<String,Node> table = new Hashtable<String,Node>();
			FibonacciHeap fibHeap = new FibonacciHeap();
			String array[];
			
			//Read input from input file line by line
			while ((st = br.readLine()) != null) {
				array = st.split(" ");
				if(array.length == 1) {
					
					//Stop execution of the program when "stop" appears in the input 
					if(array[0].equals("stop")) {
						break;
					}
					else {
						try {
							
							//Read number of queries and perform remove max for each query 
							int queries = Integer.parseInt(array[0]);
							Node stack[] = new Node[queries];
							
							//Throw error if number of queries is greater than 20
							if(queries > 20)
								throw new IllegalArgumentException("Invalid input format query integer > 20 ");
							if(!flag) {
								writer.newLine();
							}
							else
								flag = false;
							for(int i = 1; i <= queries ; i++) {
								Node removedElem = fibHeap.removeMax();
								if(removedElem != null) {
									
									//Store all removed Nodes in a stack for inserting later
									stack[i-1] = removedElem;
									String key = getKey(table,removedElem);
									
									//Write the removed max keyword in output file
									if(i != (queries > noNodes ? noNodes : queries))
										writer.write(key+",");
									else
										writer.write(key);
								}
								else
									throw new IllegalArgumentException("Number of queries exceed number of keywords");
							}
							
							//Insert the removed keywords again in fibonacci heap
							for(int i =0; i < stack.length; i++) {
								if(stack[i] != null)
								fibHeap.insert(stack[i]);
							}
							
						}
						catch(NumberFormatException e) {
							System.out.println(e.getMessage() + " Invalid input format - number of queries is not a valid integer");
							return;
						}
						catch(IOException e) {
							System.out.println(e.getMessage());
							return;
						}
					}
				}
				else if(array.length == 2 && array[0].startsWith("$")) {
					try {
						int increment = Integer.parseInt(array[1]);
						
						//Throw an exception if increment is negative
						if(increment < 0)
							throw new NumberFormatException("Increment less than 0");
						String key = array[0].substring(1);
						
						//Call increase key method of fibonacci heap if the keyword is already present in the table
						if(table.containsKey(key)) {
							fibHeap.increaseKey(table.get(key), increment);
						}
						else {
							
							//Insert new Node in the fibonacci heap and store the corresponding pointer in hash table
							Node newNode = new Node(increment);
							noNodes++;
							fibHeap.insert(newNode);
							table.put(key, newNode);
						}
					}
					catch(NumberFormatException e) {
						System.out.println(e.getMessage() + " Invalid input format");
						return;
					}
				}
				else {
					throw new IllegalArgumentException("Invalid input format - keyword doesn't start with $ or more than 2 arguments in a line");
				}
			} 
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			
			//Close Buffered writer and reader
			if (writer != null) {
	            try {
	                writer.close();
	            } catch (IOException e) {
	            	System.out.println(e);
	            }
	        }
			if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	            	System.out.println(e);
	            }
	        }
		}
	}
	
	//Get key from Hash Table for a particular Fibonacci heap Node
	private static String getKey(Hashtable<String,Node> table,Node removedElem) {
		for(Map.Entry<String, Node> entry: table.entrySet()){
            if(removedElem == entry.getValue()){
                return entry.getKey();
            }
        }
		return null;
	}

}
