package Servidor;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*Essa classe lista os diretorios disponiveis*/
public class Listagem {
	private static String CRLF = "\r\n";
	/*Cria um HTML que lista todos os diretorios disponiveis*/
	public static void lista(String[] list, String path){
		String temp = path.replace("htdocs","");
		
		String listagem = " <!DOCTYPE html>" + CRLF
				+ "<HTML>" + CRLF
				+ "<body>" + CRLF
				+ "<h2>Diretorios e Arquivos: </h2>" + CRLF
				+ "<ul>" + CRLF;
				for(int i = 0; i< list.length; i++){
					listagem+= "<li><a href=\"/"+temp+"/"+list[i]+"\">"+list[i]+"</a></li>" + CRLF;
				}
				listagem+= "</ul>" +  CRLF
						+ "</body>" + CRLF
						+ "</html>" + CRLF
						+ CRLF;
		try{
			FileWriter fileWriter = new FileWriter("./htdocs/listagem.html");
			PrintWriter writer = new PrintWriter(fileWriter);
			writer.write(listagem);
			writer.close();
		}catch(FileNotFoundException e){
			System.err.println("Erro: Nao foi encontrado o arquivo de listagem.");		
		} catch (IOException e) {
			System.err.println("Erro na Listagem");
		}
		
	}
}
