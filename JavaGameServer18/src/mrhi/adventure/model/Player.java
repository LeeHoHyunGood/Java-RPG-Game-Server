package mrhi.adventure.model;

import java.io.IOException;
import java.net.Socket;

import mrhi.adventure.control.ConnectionManager;
import mrhi.adventure.db.AccountDAO;
import mrhi.adventure.db.CharacterDAO;
import mrhi.adventure.model.vo.AccountVO;
import mrhi.adventure.model.vo.CharacterVO;

public class Player {
	private ConnectionManager connectionManager;
	private AccountVO account;
	private CharacterVO character;
	
	public Player(Socket socket) {
		super();
		this.connectionManager = new ConnectionManager(socket);
	}
	
	public void disconnect() {
		try {
			this.connectionManager.getSocket().close();
		} catch (IOException e) {
			System.out.println("강제 연결종료");
		}
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setPConnectionManager(ConnectionManager packetManager) {
		this.connectionManager = packetManager;
	}

	public AccountVO getAccount() {
		return account;
	}

	public void setAccount(AccountVO account) {
		this.account = account;
	}

	public CharacterVO getCharacter() {
		return character;
	}

	public void setCharacter(CharacterVO character) {
		this.character = character;
	}
	
}
