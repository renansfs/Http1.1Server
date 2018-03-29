package Servidor;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*Classe que grava o arquivo de log*/
public class Log {
	private static String fileName = "log.txt";
	
	public static void save(String solicitacao){
		try{
			FileWriter fileWriter = new FileWriter("./htdocs/"+fileName, true);
			PrintWriter writer = new PrintWriter(fileWriter);
			writer.write(solicitacao);
			writer.close();
		}catch(FileNotFoundException e){
			System.err.println("Erro: Nao foi encontrado o arquivo de log.");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
