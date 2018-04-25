package mrhi.adventure.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import mrhi.adventure.control.ConnectionHandler;
import mrhi.adventure.control.MobGenManager;
import mrhi.adventure.control.SendHandler;
import mrhi.adventure.db.AccountDAO;
import mrhi.adventure.db.CharacterDAO;
import mrhi.adventure.db.ChatDAO;
import mrhi.adventure.db.ItemDAO;
import mrhi.adventure.db.ResourceDAO;
import mrhi.adventure.model.packet.MyPacket;

public class Server {

	private static Server theInstance = new Server();
	private List<Player> playerList = new LinkedList<>();
	private AccountDAO accountDAO = new AccountDAO();
	private CharacterDAO characterDAO = new CharacterDAO();
	private ItemDAO itemDAO = new ItemDAO();
	private ChatDAO chatDAO = new ChatDAO();
	private ResourceDAO resourceDAO = new ResourceDAO();
	private Game game;
	private GameManager gameManager = new GameManager();
	private EMailSender eMailSender = new EMailSender();
	private SendHandler sendHandler;

	public static Server getInstance() {
		return theInstance;
	}

	private Server() {
		game = new Game();
		ServerThread st = new ServerThread();
		Thread sThread = new Thread(st);
		sThread.start();
		
		sendHandler = new SendHandler();
		new Thread(new MobGenManager()).start();
		new Thread(sendHandler).start();
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}	

	public void addPacket(Player player, MyPacket packet) {
		this.sendHandler.addPacket(player, packet);
	}
	
	public void addPacket(List<Player> players, MyPacket packet) {
		this.sendHandler.addPacket(players, packet);
	}
	
	public SendHandler getSendHandler() {
		return sendHandler;
	}

	public void setSendHandler(SendHandler sendHandler) {
		this.sendHandler = sendHandler;
	}

	public void setPlayerList(List<Player> mySessionList) {
		this.playerList = mySessionList;
	}

	public ChatDAO getChatDAO() {
		return chatDAO;
	}

	public void setChatDAO(ChatDAO chatDAO) {
		this.chatDAO = chatDAO;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public CharacterDAO getCharacterDAO() {
		return characterDAO;
	}

	public void setCharacterDAO(CharacterDAO characterDAO) {
		this.characterDAO = characterDAO;
	}
	
	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public EMailSender geteMailSender() {
		return eMailSender;
	}

	public void seteMailSender(EMailSender eMailSender) {
		this.eMailSender = eMailSender;
	}

	public ResourceDAO getResourceDAO() {
		return resourceDAO;
	}

	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}


	public class ServerThread implements Runnable {

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(21212);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true)
			{
				try {
					System.out.println("연결된 사람 수 : " + playerList.size());
					Socket socket = serverSocket.accept();
					Player ms = new Player(socket);
					playerList.add(ms);
					ConnectionHandler msr = new ConnectionHandler(ms);
					Thread sThread = new Thread(msr);
					sThread.start();
					System.out.println(socket.getInetAddress().getHostAddress() + " 소켓 연결 성공!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void main(String[] args) {
		Server.getInstance();
	}
}
