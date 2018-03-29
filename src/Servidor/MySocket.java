package Servidor;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.StringTokenizer;


public class MySocket implements Runnable {
	/*Configuracoes do Servidor*/
	private ConfigServer config;
	
	/*Socket*/
	private Socket socket;
	private String CRLF = "\r\n";
	
	/*Buffer de leitura e saida*/
	private BufferedReader input;
	private DataOutputStream output; 
	
	/*Entrada e saida de dados*/
	private InputStream inStream;
    private OutputStream outStream;
   
    /*Dados sobre a autenticacao*/
	private boolean auth = false;
	
	/*Socket usado
	 *  inStream = entrada de dados
	 *  outStream= saida de dados*/
	
	public MySocket(Socket socket, ConfigServer config) throws IOException{
		this.socket = socket;
		this.config = config;
		
		try {	
			inStream = socket.getInputStream();
		    input =  new BufferedReader(new InputStreamReader(inStream));
	        
		    outStream = socket.getOutputStream();
		    output = new DataOutputStream(outStream);
	        
	        
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	/*Executa a thread no servidor*/
	@Override
	public void run() {  
			String fileName = null;
			try {	
				fileName = baseFile();		
				send(fileName);
				
			} catch(FileNotFoundException e){
					/*Caso o arquivo solicitado nao seja encontrado*/
					try {
						sendMessageNotFound(fileName);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Thread.interrupted();
			} catch (IOException e) {
				System.err.println("Erro na tentativa de enviar a mensagem ao cliente");
				Thread.interrupted();
			} catch (InterruptedException e) {
				Thread.interrupted();
			}
	}
	/*Recebe a mensagem*/
	public String baseFile() throws IOException, InterruptedException{
		StringTokenizer token;
		String message;
		String fileName = null;
		String temp;
		int count = 0;
		
		message = this.receiveMessage();
		token = new StringTokenizer(message);
		
		System.out.println("Mensagem Recebida: "+message);
		
		
		while(token.hasMoreTokens()){
			temp = token.nextToken();
			if(count == 1) fileName = temp;
			
			/*Verifica a autenticacao*/
			if(temp.equals("Basic")){
				temp = token.nextToken();
				byte[] valueDecoded= Base64.getDecoder().decode(temp);
				if(auth(valueDecoded)) this.auth = true;
			}
			count ++;
		}
		return fileName;
	}
	
	/*Autentica*/
	private boolean auth(byte [] auth){
		if((this.config.getUser()+":"+this.config.getPass()).equals(new String(auth))) return true;
		return false;
	}
	
	/*Classe que escolhe o tipo de retorno*/
	public void send(String fileName) throws InterruptedException, IOException{
			File file = new File("./htdocs"+fileName);
			System.out.println(" ARQUIVAO "+file.getName());
			if(!file.isFile() && !file.isDirectory()) sendMessageNotFound(fileName);
			else if(file.getName().equals("config.txt") && !this.auth){
				sendNotAuthorizedMessage(fileName);
			}
			else if(file.isFile()  || content(fileName) ){
				sendMessage(file, 200);
				
			}else if(!fileName.equals("/") && !file.isFile() && !content(fileName) && !this.auth){
				sendNotAuthorizedMessage(fileName);
					
			}else if(file.isDirectory() && config.getDir() == 1){
				String[] lista = file.list();
				Listagem.lista(lista, file.getPath());
				file = new File("./htdocs/listagem.html");
				sendMessage(file, 200);
					
			}else if(file.isDirectory() && config.getDir() == 2){
				file = new File("./htdocs"+fileName+"index.html");
				sendMessage(file, 200);
			}else if(file.isDirectory() && config.getDir() == 3) sendMessageNotFound(fileName);
			
			
			this.close();
		
	}
	
	/*Envia a mensagem*/
	public void sendMessageNotFound(String fileName) throws IOException {
		File file = new File("./htdocs/404/index.html");
		System.out.println("Arquivo solicitado \""+fileName+"\" nao encontrado - Erro 404 gerado");
		sendMessage(file, 404);
		
	}
	
	/*Mensagem de nao autorizado*/
	public void sendNotAuthorizedMessage(String file) throws IOException{
		String notAuthorized = "HTTP 401 Unauthorizes status" + CRLF
							+  "WWW-Authenticate: Basic realm=\"User Visible Reaml\""+CRLF
							+  "Content-Length: 0" + CRLF;
		
		requestLog(contentType(file), 501, notAuthorized);
		
		output.writeBytes(notAuthorized);
		output.flush();
		output.close();

	}
	
	/*Envia o conteudo WEB para o Browser*/
	public void sendMessage(File file, int msg) throws IOException{
		
		FileInputStream fis = null;
		int r = 0;
		
		String content = contentType( file.getName() );
		String response = "HTTP/1.1 "+msg+" "+ CRLF;
		response+= "Content-type: " +
				    content + CRLF;
		response+= "Server: Servidor de HTTP 1.1 "+CRLF;
		response+= "Connection: close "+CRLF;
		response+= "Content-Length: "+ file.length()+" "+CRLF;
		response+= CRLF;
		
		fis = new FileInputStream(file);
			
		while((r = fis.read()) != -1){
			response+= (char) r;
		}
		
		requestLog(content, msg, response);
		output.writeBytes(response);
		output.flush();
		fis.close();
	}	
	
	public void requestLog(String content, int msg, String response) throws UnsupportedEncodingException{
		ZoneId zone = ZoneId.of("Brazil/East");
		LocalTime time = LocalTime.now(zone);
		/*Salva no arquivo de LOG*/
		String solicitacao = "Endereco de Origem: "+socket.getInetAddress().getHostAddress().toString() + CRLF
				 + "Tipo da solicitação: "+msg+ CRLF
				 + "Porta de Origem:  "+socket.getPort()+ CRLF
				 + "Horario: " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + CRLF
				 + "Conteudo: "+content+ CRLF
				 + "Quantidade de bytes: " + response.getBytes("UTF-8").length * 8 + CRLF+CRLF;
		
		Log.save(solicitacao);
	}
	
	/*Recebe a mensagem*/
	public String receiveMessage( ) throws IOException {	
		String message = input.readLine() + CRLF;
		while(input.ready()){
			message+=input.readLine() + CRLF;
		}
		
		return message;
	}
	
	/*Fecha todas as entradas e saidas de dados*/
	public void close() throws InterruptedException, IOException{
		System.out.printf("Thread %d foi encerrada \n \n", Thread.currentThread().getId());
		input.close();
		output.close();
		inStream.close();
		outStream.close();
		Thread.sleep(1000);
		socket.close();
	}
	
	
	/*Verifica o tipo de conteudo*/
	private static String contentType(String fileName){
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		else if(fileName.endsWith(".css")) {
			return "text/css";
		}
		else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		else if(fileName.endsWith(".gif")) {
			return "image/gif";
		}else if(fileName.endsWith(".png")) {
			return "image/png";
		}else{
			return "application/octet-stream";
		}
	}
	/*Verifica qual o tipo de conteudo*/
	private static boolean content(String fileName){
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return true;
		}
		else if(fileName.endsWith(".css")) {
			return true;
		}
		else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return true;
		}
		else if(fileName.endsWith(".gif")) {
			return true;
		}else if(fileName.endsWith(".png")) {
			return true;
		}else{
			return false;
		}
	}

}
