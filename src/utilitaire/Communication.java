package utilitaire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Communication {

	public static Gson gson = new Gson();

	public static final String demandeCreationMsg = "Demande creation message",
			demandeTousFils = "Demande tous les fils", demandeFil = "Demande get fil",
			demandeTousGroupes = "Demande tous les groupes";

	public static void log(String msg) {
		System.out.println(msg);
	}

	public static int envoyerMsg(BufferedWriter os, String msg) {
		try {
			os.write(msg);
			os.newLine();
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public static String lireMsg(BufferedReader is) {
		String responseLine;
		try {
			responseLine = is.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return responseLine;

	}

}
