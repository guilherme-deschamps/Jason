// CArtAgO artifact code for project buraco

package inc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.parser.ParseException;
import cartago.*;
import cartago.manual.syntax.Literal;
import cartago.manual.syntax.Term;

public class Baralho extends Artifact {
	
	public static void main(String[] args){
		Baralho b = new Baralho();
		b.init();
	}
	
		private static final int QTD_CARTAS = 52;
		private static final int NAIPES = 4;
		private static final int CARTA_POR_NAIPE = 13; 
		
		int jogador;
		String[] baralho = new String[52];
		Random random = new Random();
		String aux;
		int aux2;
		int n;
		ArrayList<String> mesa = new ArrayList<String>(); /* Define automaticamente o tamanho do vetor */
		ArrayList<String> maoj1 = new ArrayList<String>();
		ArrayList<String> maoj2 = new ArrayList<String>();
		boolean removeCarta = false;
		
		void init() {
			
			// Embaralhando as cartas
			
			for(int i=0; i<52; i++) {
				baralho[i] = reconhecer(i);
			}
			
			for(int i=0; i<52; i++) {
				aux = baralho[i];
				aux2 = random.nextInt(52);
				baralho [i] = baralho[aux2];
				baralho[aux2] = aux;
			}
			
			// Colocar as cartas na mesa
			
			  for(int i=0; i<7; i++) {
				  mesa.add(0,baralho[i]);
			  }
			  
			  String s = "[";
			  for(int i=0; i<7; i++) {
				  if(i!=6) {
					  s += "\"" + baralho[i] + "\"" + ",";
				  } else {
					  s += "\"" + baralho[i] + "\"";
				  }
			  }
			  //s = s.substring( 0, s.length()-1 ); Tira a vírgula da última casa.
			  s += "]";
			  try {
				defineObsProperty("mesa", ASSyntax.parseTerm(s));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			  aux2 = 6;
			
			  
			// Definir primeiro jogador
			
			jogador = random.nextInt(2) + 1;
			try {
				defineObsProperty("player", ASSyntax.parseLiteral("jogador" + jogador));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@OPERATION
		public void proximo() {			
			if(jogador==1) { jogador=2;	} else { jogador=1; }
			removeObsProperty("player");
			try {
				defineObsProperty("player", ASSyntax.parseLiteral("jogador" + jogador));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		// Jogada
		
		@OPERATION
		public void jogar() {
			aux2++;
			
			if(removeCarta) {
				removeObsProperty("carta");
				removeObsProperty("maoj1");
				removeObsProperty("maoj2");
			}
			
			defineObsProperty("carta", baralho[aux2]);
			if(maoj1.size()==0) {
				defineObsProperty("maoj1", "");
			} else {
				String s = "[\"" + maoj1.get(0) + "\"]";
				try {
					defineObsProperty("maoj1", ASSyntax.parseTerm(s));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			if(maoj2.size()==0) {
				defineObsProperty("maoj2", ""); 
			} else {
				String s = "[\"" + maoj2.get(0) + "\"]";
				try {
					defineObsProperty("maoj2", ASSyntax.parseTerm(s));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
			removeCarta = true;
		}
		
		@OPERATION
		public void pegarCarta(String carta, int j) {
			if(j==0) {
				maoj1.add(0,carta);
			} else {
				maoj2.add(0,carta);
			}
		}
		
		@OPERATION
		public void again(int j) {
			if(j==0) {jogador=1;} else {jogador=2;}
			removeObsProperty("player");
			try {
				defineObsProperty("player", ASSyntax.parseLiteral("jogador" + jogador));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("");
		}
		
		@OPERATION
		public void pegarDaMesa(String carta, int j){
			String s;
			for(int i=0; i<mesa.size(); i++) {
				if (mesa.get(i).equals(carta)) {
					if(j==0) {
						maoj1.add(0,mesa.get(i));
					} else {
						maoj2.add(0,mesa.get(i));
					}
					mesa.remove(mesa.get(i));
				}
			}
			if(j==0) {
				maoj1.add(0,carta);
				removeObsProperty("maoj1");
				s = "[\"" + maoj1.get(0) + "\"]";
				try {
					defineObsProperty("maoj1", ASSyntax.parseTerm(s));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			} else {
				maoj2.add(0,carta);
				removeObsProperty("maoj2");
				s = "[\"" + maoj2.get(0) + "\"]";
				try {
					defineObsProperty("maoj2", ASSyntax.parseTerm(s));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			s = escreverVetor(mesa);
			removeObsProperty("mesa");
			try {
				defineObsProperty("mesa", ASSyntax.parseTerm(s));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@OPERATION
		public void pegarMao(String carta, int j) {
			if(j==0) {
				maoj1.addAll(0, maoj2);
				maoj2.removeAll(maoj2);
				maoj1.add(0, carta);
				removeObsProperty("maoj2");
				defineObsProperty("maoj2", "");
				System.out.println("");
				System.out.println("Peguei as cartas do Jogador 2.");
				System.out.println("Jogador 1 tem " + maoj1.size() + " cartas.");
				System.out.println("");
			} else{
				maoj2.addAll(0,maoj1);
				maoj1.removeAll(maoj1);
				maoj2.add(0, carta);
				removeObsProperty("maoj1");
				defineObsProperty("maoj1", "");
				System.out.println("");
				System.out.println("Peguei as cartas do Jogador 1.");
				System.out.println("Jogador 2 tem " + maoj2.size() + " cartas.");
				System.out.println("");
			}
			
		}
		
		private String escreverVetor(ArrayList<String> vetor) {
			String s;
			s = "[";
			for(int i=0; i<vetor.size(); i++) {
				if(i<(vetor.size()-1)) {
					s += "\"" + vetor.get(i) + "\"" + ","; 
				} else {
					s += "\"" + vetor.get(i) + "\"";
				}
			}
			s += "]";
			return s;
		}
		
		@OPERATION
		public void devolverCarta(String carta) {
			mesa.add(0,carta);
			removeObsProperty("mesa");
			String s = escreverVetor(mesa);
			try {
				defineObsProperty("mesa", ASSyntax.parseTerm(s));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("");
		}
		
		private static String reconhecer(int n) {
			
			switch (n % 13) {
				case 0: return "A";		
				case 1: return "2";		
				case 2: return "3";		
				case 3: return "4";		
				case 4: return "5";		
				case 5: return "6";		
				case 6: return "7";		
				case 7: return "8";		
				case 8: return "9";		
				case 9: return "10";		
				case 10: return "J";		
				case 11: return "Q";		
				case 12: return "K";	
				default: return "";
			}
		
		}
}

