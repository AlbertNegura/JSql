package exercise2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Randomnames {
	private int n;
	private List<String> list = new ArrayList<>();
	
	//string of available characters for names
	private final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//random number generation
	private final java.util.Random rand = new java.util.Random();
	//ensure distinct names
	private final Set<String> ids = new HashSet<>();
	
	//constructor with parameter for number of names to be generates
	public Randomnames(int number){
		this.n = number;
		populateList();
	}
	
	//generate names for entire list
	private void populateList(){
		for(int i = 0; i<n; i++)
			list.add(generateValue());
	}
	
	//generate new random name using the list of random characters
	private String generateValue(){
		StringBuilder builder = new StringBuilder();
	    while(builder.toString().length() == 0) {
	        int length = rand.nextInt(5)+5;
	        for(int i = 0; i < length; i++) {
	            builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
	        }
	        if(ids.contains(builder.toString())) {
	            builder = new StringBuilder();
	        }
	    }
	    return builder.toString();
	}
	
	//return the generated list of names
	public List<String> getNames(){
		return list;
	}
	
	//testing case
	public static void main(String[] args){
		Randomnames rd = new Randomnames(1000);
		int i = 2;
		for(String s:rd.getNames()){
			System.out.println((i++) + " : " + s);
		}
	}
}
