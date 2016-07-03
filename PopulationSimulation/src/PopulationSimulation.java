import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class PopulationSimulation {

	static final String EXIT = "exit";
	static final String RETURN = "";
	static final String SET_BRITH_RATE = "set birth rate";
	static final String SET_DEATH_RATE = "set death rate";
	static final String SET_MARRIAGE_RATE = "set marriage rate";
	static final String SHOW = "show";
	static final int INITIAL_YEAR = 20;
	static final String DETAIL_OUTPUT = "detail.txt";
	
	static PrintStream out = System.out;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Population population = new Population(INITIAL_YEAR);
		population.printSummary(System.out);
		PrintStream out = new PrintStream(new FileOutputStream(new File(DETAIL_OUTPUT), true));
		population.writeDetail(out);
		while(true){
			System.out.print("input command: ");
			String input = sc.nextLine().trim();
			String inputLowerCase = input.toLowerCase();
			if( input.equalsIgnoreCase(EXIT) ){
				out.println("Simulation ends.");
				break;
			}else if( inputLowerCase.startsWith(SET_BRITH_RATE) ){
				int lastSpaceIndex = input.lastIndexOf(' ');
				float rate = Float.parseFloat(input.substring(lastSpaceIndex));
				float prevRate = population.getBirthProb();
				population.setBirthProb(rate);
				System.out.println("change birth rate: "+prevRate + " to " + rate);
			}else if( inputLowerCase.startsWith(SET_DEATH_RATE) ){
				float rate = Float.parseFloat(input.substring(input.lastIndexOf(' ')));
				float prevRate = population.getDeathProb();
				population.setDeathProb(rate);
				System.out.println("change death rate: "+prevRate+ " to "+ rate);
			}else if( inputLowerCase.startsWith(SET_MARRIAGE_RATE) ){
				float rate = Float.parseFloat(input.substring(input.lastIndexOf(' ')));
				float prevRate = population.getMarriageProb();
				population.setMarriageProb(rate);
				System.out.println("change marriage rate: "+prevRate+" to "+rate);
			}else if( inputLowerCase.startsWith(SHOW) ){
				String fullName = input.substring(SHOW.length()+1);				
				Person p = population.find(fullName);
				if( p != null ){
					p.showDetail(System.out, population.getThisYear());
				}else{
					System.out.println("No result");
				}
				System.out.println("\n");
			}else if( input.equals(RETURN) ){
				population.simulateNextYear();
				System.out.println("simulate " + population.getThisYear());
				population.printSummary(System.out);
				population.writeDetail(out);
			}else{
				System.out.println("Wrong command.");
			}
		}
		sc.close();
	}

}
