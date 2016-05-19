package de.ods.ccd.questionnaire;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import de.ods.ccd.questionnaire.domain.Aufgabe;

@Service
public class QuestionnaireService {

	@Value("classpath:questionnaire.txt")
	private Resource inputfile;

	List<Aufgabe> start() throws IOException {
		List<String> zeilen = Files.readAllLines(inputfile.getFile().toPath(), Charset.forName("UTF-8"));
		List<Aufgabe> aufgaben = erstelleAufgaben(zeilen);
		return ergeanzeWeissNicht(aufgaben);
	}

	private List<Aufgabe> ergeanzeWeissNicht(List<Aufgabe> aufgaben) {
		for (Aufgabe aufgabe : aufgaben) {
			aufgabe.ergaenzeAntwortmoeglichkeit("Weiß nicht");
		}
		return aufgaben;
	}

	private List<Aufgabe> erstelleAufgaben(List<String> zeilen) {
		List<Aufgabe> parseAufgaben = parseAufgaben(zeilen);
		
		return parseAufgaben;
	}

	private List<Aufgabe> parseAufgaben(List<String> zeilen) {
		List<Aufgabe> aufgaben = new ArrayList<>();

		Aufgabe aktuelleAufgabe = null;
		for (int zeilennummer = 0; zeilennummer < zeilen.size(); zeilennummer++) {
			String zeile = zeilen.get(zeilennummer);
			if (zeile.startsWith("?")) {
				if(aktuelleAufgabe != null){
					aufgaben.add(aktuelleAufgabe);
				}
				aktuelleAufgabe = new Aufgabe();
				
				aktuelleAufgabe.setFrage(fragezeichenAnsEndeSetzen(zeile));
			} else if (zeile.startsWith("*")) {
				aktuelleAufgabe.setRichtigeAntwort(zeilennummer);
				aktuelleAufgabe.ergaenzeAntwortmoeglichkeit(zeile);
			} else {
				aktuelleAufgabe.ergaenzeAntwortmoeglichkeit(zeile);
			}
		}

		if(aktuelleAufgabe != null){
			aufgaben.add(aktuelleAufgabe);
		}
		
		return aufgaben;
	}

	private String fragezeichenAnsEndeSetzen(String zeile) {
		return zeile.substring(1) + "?";
	}

}
