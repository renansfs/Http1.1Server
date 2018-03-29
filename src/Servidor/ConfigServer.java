package Servidor;

public class ConfigServer {
	private int port;
	private int dir;
	private String user;
	private String pass;
	
	/*Classe que armazena as configuracoes do servidor*/
	public ConfigServer(int port, int dir, String user, String pass){
		this.port = port;
		this.dir = dir;
		this.user = user;
		this.pass = pass;
		
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public int getPort() {
		return port;
	}

	public int getDir() {
		return dir;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}
	
}
