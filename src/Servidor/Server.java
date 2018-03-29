package Servidor;
import java.net.*;
import java.io.*;

/*Servidor principal de Threads*/
public class Server implements Runnable{
	/*Socket do Servidor e Configuracao do Server*/
	private ServerSocket  socketServer;
	private ConfigServer  config;
	
	/*Construtor que cria um Socket com Host e Porta*/
	Server(ConfigServer config) throws SocketException, IOException{
		System.out.println("Servidor Online \n");
		this.config = config;
		socketServer = new ServerSocket(this.config.getPort());
	}
	
	/*Encerra o servidor*/
	public void close( ) throws IOException {
		socketServer.close( );
		Thread.interrupted();
	}
	/*Cria as Threads e distribui*/
	@Override
	public void run() {
		synchronized (this) {
			Thread.currentThread();
		}
		while(!socketServer.isClosed()){
			try {
				
				Thread exec = new Thread(new MySocket(socketServer.accept(), config));
				exec.start();
				System.out.printf("Thread %d em execucao \n", exec.getId());
			} catch(SocketException e){
				System.err.println("Servidor encerrado");
				
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
