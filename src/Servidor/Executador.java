package Servidor;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class Executador implements Runnable {
	private Server HTTP;
	private ConfigServer config;
	
	/*Inicia o servidor e as configuracoes*/
	public void start(){
		try{
			config();
			HTTP = new Server(config);
			HTTP.run();
			
		}catch(SocketException e){
			System.err.println("Servidor Encerrado");
		}catch(IOException e){ 
			System.err.println("IOException");
		}
	}
	
	/*Encerra o servidor fechando o socket*/
	public void stop(){
			try {
				HTTP.close();
				Thread.interrupted();
			}catch(SocketException e){
				System.err.println("Socket Encerrado");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Servidor foi parado.");
			}
	}
	
	/*Configura o servidor*/
	public void config() {
		try {
			String s=null, user = null, pass = null;
			int port=0, type=0;
			
			FileReader reader = new FileReader("htdocs/config.txt");
			BufferedReader buffReader = new BufferedReader(reader);
			int count = 0;
			
			while((s = buffReader.readLine()) != null){
				
				if(count == 0) port = Integer.parseInt(s);
				else if(count == 1) type = Integer.parseInt(s);
				else if(count == 2) user = s;
				else if(count == 3) pass = s;
		        count++;
		    }
		
			config = new ConfigServer(port, type, user, pass);
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Config.txt Nao encontrado.");
		} catch(IOException e){
			JOptionPane.showMessageDialog(null, "IOException.");
		}
		
	}
	/*Executa a Thread do servidor*/
	@Override
	public void run() {
		start();
	}
}
