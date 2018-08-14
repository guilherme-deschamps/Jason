

import cartago.*;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Term;
import jason.asSyntax.parser.ParseException;

public class vetor extends Artifact {
	
	void init() {
		
		String[] teste = new String[] {"a", "5", "7"};
		//"[a,5,7,q,2,9]"
		String usable = "[";
		
		for(int i=0; i<teste.length; i++) {
			if(i==(teste.length-1)) {
				usable += teste[i];
			} else {
				usable += teste[i] + ",";
			}
			System.out.println(usable);
		}
		usable += "]";
		
		try {
			defineObsProperty("mesa", ASSyntax.parseTerm(usable));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

