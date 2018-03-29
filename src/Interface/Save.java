package Interface;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Save {
	private String CRLF = "\r\n";
	
	public void save(boolean mode, String port, String tipo, String user, String pass){
		
		try{
			FileWriter fileWriter = new FileWriter("./htdocs/config.txt");
			PrintWriter writer = new PrintWriter(fileWriter);
			writer.write(type(mode, port, tipo, user, pass));
			writer.close();
			
		}catch(FileNotFoundException e){
			System.err.println("Erro: Nao foi encontrado o arquivo de listagem.");		
		} catch (IOException e) {
			System.err.println("Erro na Listagem");
		}
	}
	
	/*Configuracao do arquivo*/
	public String type(boolean mode, String port, String tipo, String user, String pass){
		String config;
		if(!mode){
			config = "8888" + CRLF
				  + "1" + CRLF
				  + "admin" + CRLF
				  + "1234" + CRLF;
			return config;
		}else{
			config = port + CRLF
					  + tipo + CRLF
					  + user + CRLF
					  + pass + CRLF;
			return config;
		}
	}
}
