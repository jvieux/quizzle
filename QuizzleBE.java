import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

//BackEnd
public class QuizzleBE {
	Scanner scan;
	HashMap<String,String> qPair = new HashMap<>();
	ArrayList<String> quest = new ArrayList<>();
	ArrayList<String> ans = new ArrayList<>();
	Random r = new Random();
	int count = 0;
	String qName;
	String aName;
	
	public boolean loadFile(String fileName) {
		boolean worked = true;
		count = 0;
		try {
			scan = new Scanner(new File(fileName));
			
		}
		catch(Exception e) {
			System.out.println("Exception e = " + e);
			return false;
		}
		while(scan.hasNextLine()) {
			String s = scan.nextLine();
			int iend = s.indexOf(",");
			String question = s.substring(0,iend);
			String answer = s.substring(iend+1);
			if(count == 0) {
				qName = question;
				aName = answer;
				count++;
			}
			else {
				qPair.put(question, answer);
				quest.add(question);
				ans.add(answer);
			}
			//System.out.println("question: " + question + " answer: " + answer);
		}
		return worked;
		
	}
	
	public String pickQuestion() {
		//i know this isn't the most efficient way and i should have just made an array of the 5 and then cycled through those but right now I am too deep into the method I was using before realizing this could create doubles so this is what I am doing
		String qq = null;
		boolean noRepeat = false;
		ArrayList<String> tbd = new ArrayList<>();
		boolean alreadyUsed = false;
		
		while(!noRepeat) {
			int chosen = r.nextInt(quest.size()) ;
			for(int k = 0; k<tbd.size(); k++) {
				if(tbd.get(k)==quest.get(chosen)) {
					alreadyUsed=true;
				}
			}
			if(!alreadyUsed) {
				qq = quest.get(chosen);
				tbd.add(qq);
				noRepeat = true;
			}
		}
		
		//String qq = quest.get(chosen);
		return qq;
	}
	
	public String getAnswer(String question) {
		String ans = qPair.get(question);
		return ans;
	}
	
	public ArrayList<String> getAnsOptions(String question){
		ArrayList<String> tbd = new ArrayList<>();
		boolean alreadyUsed = false;
		
		tbd.add(getAnswer(question));
		//System.out.println(tbd.get(0));
		
		while(tbd.size()<4){
			int b = r.nextInt(ans.size());
			for(int k = 0; k<tbd.size(); k++) {
				if(tbd.get(k)==ans.get(b)) {
					alreadyUsed=true;
				}
			}
			if(!alreadyUsed && ans.get(b) != getAnswer(question)) {
				//alreadyUsed=fal;
				tbd.add(ans.get(b));
			}
			alreadyUsed=false;
			//System.out.println(tbd.get(i+1));
		}
		
		Collections.shuffle(tbd);
		
		return tbd;
	}
	
	
	public boolean isRight(String clicked, String question) {
		if(clicked == getAnswer(question)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getQCat() {
		return qName;
	}
	
	public String getACat() {
		return aName;
	}
	
	
}
