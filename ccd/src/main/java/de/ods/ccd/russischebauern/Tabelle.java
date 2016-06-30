package de.ods.ccd.russischebauern;

import java.util.ArrayDeque;
import java.util.Deque;

public class Tabelle {

	private Deque<Zeile> stack;

	public Tabelle(int i, int j) {
		this.stack = erstellen(i, j);
	}

	Deque<Zeile> erstellen(int i, int j) {
		Deque<Zeile> stack = new ArrayDeque<Zeile>();
		Zeile zeile = new Zeile(i, j);
		stack.push(zeile);
		
		while(zeile.i >= 1){
			zeile = zeile.naechsteZeile();
			stack.push(zeile);
		}
		return stack;
	}

	int auswerten() {
		int ergebnis = 0;
		while(stack.isEmpty() == false){
			Zeile zeile = stack.pop();
			
			if(zeile.i % 2 != 0){
				ergebnis += zeile.j;
			}
		}
		
		return ergebnis;
	}

}
