package mrhi.adventure.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mrhi.adventure.model.MyLog;
import mrhi.adventure.model.Player;
import mrhi.adventure.model.SendPacket;
import mrhi.adventure.model.Server;
import mrhi.adventure.model.game.Damage;
import mrhi.adventure.model.game.DropItem;
import mrhi.adventure.model.game.GameMap;
import mrhi.adventure.model.game.JItem;
import mrhi.adventure.model.game.JMob;
import mrhi.adventure.model.packet.MyPacket;
import mrhi.adventure.model.vo.AccountVO;
import mrhi.adventure.model.vo.AuthenticationVO;
import mrhi.adventure.model.vo.CharacterVO;
import mrhi.adventure.model.vo.ChatVO;
import mrhi.adventure.model.vo.IntegerVO;
import mrhi.adventure.model.vo.ItemVO;
import mrhi.adventure.model.vo.MapVO;
import mrhi.adventure.model.vo.MobVO;
import mrhi.adventure.model.vo.OtherCharacterVO;

public class ConnectionHandler implements Runnable{
	private Player player;

	public ConnectionHandler(Player player) {
		super();
		this.player = player;
	}

	@Override
	public void run() {
		AccountVO avo = null;
		CharacterVO cvo = null;
		MapVO mapvo = null;
		MobVO mobvo = null;
		ItemVO itemVO = null;
		ChatVO chatVO = null;
		IntegerVO ivo = null;
		OtherCharacterVO ocvo = null;
		AuthenticationVO authVO = null;

		try {
			while(true) {
				MyPacket packet = null;

				packet = (MyPacket)player.getConnectionManager().receive();
				String ip = player.getConnectionManager().getSocket().getInetAddress().getHostAddress();
				switch(packet.getType())
				{		

				case 10:
					MyLog.log(ip + " 회원가입 요청");
					avo = (AccountVO)packet.getSubObject();
					registerHandler(avo);
					break;

				case 12:
					MyLog.log(ip + " 로그인 요청");
					avo = (AccountVO)packet.getSubObject();
					loginHandler(avo);
					break;

				case 140: // 아이디찾기 이메일 전송요청
					MyLog.log(ip + " 아이디찾기 이메일 전송요청");
					avo = (AccountVO)packet.getSubObject();
					requestFindIDEmailHandler(avo);
					break;

				case 150: // 비번찾기 이메일 전송요청
					MyLog.log(ip + " 비번찾기 이메일 전송요청");
					avo = (AccountVO)packet.getSubObject();
					requestFindPWEmailHandler(avo);
					break;

				case 160: // 회원가입 이메일 전송요청
					MyLog.log(ip + " 회원가입 이메일 전송요청");
					avo = (AccountVO)packet.getSubObject();
					requestRegisterEmailHandler(avo);
					break;

				case 141:
					MyLog.log(ip + " 아이디찾기 인증 확인요청");
					authVO = (AuthenticationVO)packet.getSubObject();
					requestFindIDAuthHandler(authVO);
					break;

				case 151:
					MyLog.log(ip + " 비번찾기 인증 확인요청");
					authVO = (AuthenticationVO)packet.getSubObject();
					requestFindPWAuthHandler(authVO);
					break;

				case 161:
					MyLog.log(ip + " 가입 인증 확인요청");
					authVO = (AuthenticationVO)packet.getSubObject();
					requestRegisterAuthHandler(authVO);
					break;

				case 17:
					avo = (AccountVO)packet.getSubObject();
					changePassword(avo);
					break;

				case 20:
					MyLog.log(ip + " 케릭터 생성 요청");
					cvo = (CharacterVO)packet.getSubObject();
					createCharacterHandler(cvo);
					break;

				case 21:
					MyLog.log(ip + " 케릭터 delete 요청");
					cvo = (CharacterVO)packet.getSubObject();
					deleteCharacterHandler(this.player.getAccount(), cvo);
					break;

				case 22:
					MyLog.log(ip + " 케릭터 정보 요청");
					cvo = (CharacterVO)packet.getSubObject();
					requestCharacterHandler(cvo);
					break;
				case 24:
					// 다른사람 위치바꼈따!
					ocvo = (OtherCharacterVO)packet.getSubObject();
					notifyPositionHandler(ocvo);
					break;

				case 28:
					MyLog.log(ip + " 케릭터리스트 요청");
					requestCharListHandler();
					break;

				case 30:
					MyLog.log(ip + " 맵 정보 요청");
					mapvo = (MapVO)packet.getSubObject();
					requestMapHandler(mapvo);
					break;

				case 40:
					MyLog.log(ip + " attack mob");
					mobvo = (MobVO)packet.getSubObject();
					attackMobHandler(mobvo);
					break;

				case 44:
					MyLog.log(ip + " gather Item");
					itemVO = (ItemVO)packet.getSubObject();
					gainItemHandler(itemVO);
					break;
					
				case 47:
					MyLog.log(ip + " 아이템산다");
					itemVO = (ItemVO)packet.getSubObject();
					buyItems(itemVO);
					break;
					
				case 48:
					MyLog.log(ip + " 모든아이템가져와");
					getItem();
					break;
					
				case 49:
					MyLog.log(ip + " 아이템팔자");
					itemVO = (ItemVO)packet.getSubObject();
					saleItem(itemVO);
					break;

				case 50:
					chatVO = (ChatVO)packet.getSubObject();
					chatRecvHandler(chatVO);
					break;

				case 60:
					requestItemHandler();
					break;
					
				case 63:
					itemVO = (ItemVO)packet.getSubObject();
					upMoney(itemVO);
					break;
					
				case 64:
					MyLog.log(ip + " 돈내려가라");
					itemVO = (ItemVO)packet.getSubObject();
					downMoney(itemVO);
					break;

				case 70:
					ocvo = (OtherCharacterVO)packet.getSubObject();
					attackUserHandler(ocvo);
					break;

				case 80:
					System.out.println("스텟올려달래");
					ivo = (IntegerVO)packet.getSubObject();
					increaseStatHandler(ivo);
					break;
				}


			}
		} catch (IOException | ClassNotFoundException e) {
			Server.getInstance().getPlayerList().remove(this.player);
			try {
				e.printStackTrace();
				if(player.getAccount() != null && player.getCharacter() != null)
					Server.getInstance().getCharacterDAO().saveLocation(player.getCharacter());
				System.out.println(player.getConnectionManager().getSocket().getInetAddress().getHostAddress() + "님232이 종료하셨습니다.");
				disconnectCharacter(player);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	}

	private void increaseStatHandler(IntegerVO integerVO) {
		int stat = player.getCharacter().getChr_statPoint();
		if(stat>0) {
			player.getCharacter().setChr_statPoint(stat-1);
			switch(integerVO.getValue()) {
			case 1:
				player.getCharacter().setChr_str(player.getCharacter().getChr_str()+1);
				break;
			case 2:
				player.getCharacter().setChr_dex(player.getCharacter().getChr_dex()+1);
				break;
			case 3:
				player.getCharacter().setChr_int(player.getCharacter().getChr_int()+1);
				break;
			case 4:
				player.getCharacter().setChr_pro(player.getCharacter().getChr_pro()+1);
				break;
			}
			CharacterVO charVO = new CharacterVO();
			charVO.setChr_str(player.getCharacter().getChr_str());
			charVO.setChr_dex(player.getCharacter().getChr_dex());
			charVO.setChr_int(player.getCharacter().getChr_int());
			charVO.setChr_pro(player.getCharacter().getChr_pro());
			Server.getInstance().getGameManager().saveStat(player, charVO);
		}
	}

	private void attackUserHandler(OtherCharacterVO ocvo) {
		int mapid = player.getCharacter().getChr_mapid();
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);
		int damage = player.getCharacter().getDamage();
		for(Player player : gameMap.getPlayerList()) {
			if(ocvo.getChr_idx()==player.getCharacter().getChr_idx()) {
				if(0>player.getCharacter().getChr_hp()-damage)
					Server.getInstance().getGameManager().dieCharacter(player);
				else
					Server.getInstance().getGameManager().hurtCharacter(player, damage);
			}
		}
	}

	private void requestItemHandler() {
		List<JItem> itemList = Server.getInstance().getItemDAO().getItemList(player.getCharacter());
		for(JItem item : itemList) {
			Server.getInstance().addPacket(player, new MyPacket(61, item.getItemVO()));
		}

	}

	private void chatRecvHandler(ChatVO chatVO) {
		//운영자의 명령어
		if(player.getCharacter().getChr_grade()>0 && chatVO.getText().charAt(0)=='!') {
			try {
				adminCommand(chatVO.getText());
			} catch (Exception e) {
				ChatVO cvo = new ChatVO();
				cvo.setText("not found command");
				Server.getInstance().addPacket(player, new MyPacket(51, cvo));
			}
		} else { // 그냥 일반 대화
			int mapid = player.getCharacter().getChr_mapid();
			GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);

			ChatVO cvo = chatVO;
			System.out.println(cvo.getChr_idx() + " : " + cvo.getText());
			cvo.setChr_idx(player.getCharacter().getChr_idx());
//			Server.getInstance().getChatDAO().generateChatLog(cvo);
			Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(51, cvo));
		}
	}

	private void adminCommand(String cmd) throws Exception {
		List<String> command = new ArrayList<>();
		String tmp = cmd.substring(1);
		int index = 0;

		// 명령어 ' ' 공백별로 나눔
		while(true) {
			index = tmp.indexOf(' ');
			if(index==-1) {
				command.add(tmp.substring(0));
				break;
			} else {
				command.add(tmp.substring(0, index));
			}
			tmp = tmp.substring(index+1);
		}

		
		if(command.get(0).equals("createitem")) {
			int item_idx = Integer.parseInt(command.get(1));
			int map_idx = player.getCharacter().getChr_mapid();
			Server.getInstance().getGameManager().createItem(item_idx, map_idx, player.getCharacter().getChr_x(), player.getCharacter().getChr_y());
		
		} else if(command.get(0).equals("generatemob")) {
			int mob_idx = Integer.parseInt(command.get(1));
			int map_idx = player.getCharacter().getChr_mapid();
			Server.getInstance().getGameManager().generateMob(mob_idx, map_idx, player.getCharacter().getChr_x(), player.getCharacter().getChr_y());
		
		} else if(command.get(0).equals("setlevel")) {
			
			
		} else if(command.get(0).equals("hide")) {
			Server.getInstance().getGameManager().hidePlayer(player);
			
		} else if(command.get(0).equals("show")) {
			Server.getInstance().getGameManager().showPlayer(player);
			
		} else if(command.get(0).equals("ban")) {
			System.out.println("밴");

		} else if(command.get(0).equals("out")) {
			Server.getInstance().getGameManager().outPlayer(command.get(1));
			
		} else if(command.get(0).equals("ipban")) {
			System.out.println("ip밴");

		}
	}

	private synchronized void gainItemHandler(ItemVO itemVO) {
		Server.getInstance().getGameManager().gainItem(player, itemVO);
	}
	
	public void getItem() {
		List<JItem> itemList = Server.getInstance().getResourceDAO().getItemInfoList();
		for (JItem item : itemList)
		Server.getInstance().addPacket(player, new MyPacket(48, item.getItemVO()));
	}
	
	public void buyItems(ItemVO itemVO) {
		CharacterVO cvo = Server.getInstance().getCharacterDAO().getCharacter(player.getCharacter());
		ItemVO packetItemVO = Server.getInstance().getItemDAO().buyItems(cvo, itemVO);
		if (packetItemVO != null)
		Server.getInstance().addPacket(player, new MyPacket(47, packetItemVO));
	}
	
	public void saleItem(ItemVO itemVO) {
		ItemVO item = Server.getInstance().getItemDAO().itemDelete(itemVO);
		Server.getInstance().addPacket(player, new MyPacket(49, item));
	}
	
	public void downMoney(ItemVO itemVO) {
		CharacterVO characterVO = Server.getInstance().getCharacterDAO().buyItemsMoney(player.getCharacter(), itemVO);
		Server.getInstance().addPacket(player, new MyPacket(64, characterVO));
	}
	
	public void upMoney(ItemVO itemVO) {
		CharacterVO characterVO = Server.getInstance().getCharacterDAO().saleItemMoney(player.getCharacter(), itemVO);
		Server.getInstance().addPacket(player, new MyPacket(63, characterVO));
		System.out.println(characterVO.getChr_money());
	}

	private void deleteCharacterHandler(AccountVO accountVO, CharacterVO charVO) {
		Server.getInstance().getGameManager().deleteCharacter(accountVO, charVO);
	}

	private void requestCharListHandler() throws IOException {
		List<CharacterVO> charList = Server.getInstance().getCharacterDAO().getCharacterList(player.getAccount());
		for(CharacterVO charVO : charList)
			Server.getInstance().addPacket(player, new MyPacket(29, charVO));
	}

	private void attackMobHandler(MobVO mobVO) throws IOException {
		int mapid = player.getCharacter().getChr_mapid();
		int damage = player.getCharacter().getDamage();
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);

		JMob mob = gameMap.getMob(mobVO);
		if(mob!=null) {
			if(mob.getMob_hp()>damage) {
				mob.hurt(new Damage(player, damage));
				mob.setMob_hp(mob.getMob_hp()-damage);
			} else {
				mob.hurt(new Damage(player, mob.getMob_hp()));
				mob.setMob_hp(0);
			}

			if(mob.getMob_hp()==0) 
			{
				//경험치나눠주기
				for(Damage dam : mob.getDamageList()) {
					int exp = (int)(mob.getMob_exp()*((double)dam.getDamage()/mob.getMob_maxHp()));
					dam.getPlayer().getCharacter().setChr_exp(dam.getPlayer().getCharacter().getChr_exp()+exp);

					//레벨업
					if(dam.getPlayer().getCharacter().getChr_exp()>=Server.getInstance().getGame().getExpMap().get(dam.getPlayer().getCharacter().getChr_level())) {
						dam.getPlayer().getCharacter().setChr_exp(0);
						dam.getPlayer().getCharacter().setChr_statPoint(dam.getPlayer().getCharacter().getChr_statPoint()+1);
						dam.getPlayer().getCharacter().setChr_skillPoint(dam.getPlayer().getCharacter().getChr_skillPoint()+1);
						dam.getPlayer().getCharacter().setChr_level(dam.getPlayer().getCharacter().getChr_level()+1);
					}

					//이미있는걸 안보내고 새로만들어서 보내는 이유는 네트워크 통신이 과부하 걸릴까봐 이렇게 했는데;
					CharacterVO charVO = new CharacterVO();
					charVO.setChr_level(dam.getPlayer().getCharacter().getChr_level());
					charVO.setChr_exp(dam.getPlayer().getCharacter().getChr_exp());
					charVO.setChr_skillPoint(dam.getPlayer().getCharacter().getChr_skillPoint());
					charVO.setChr_statPoint(dam.getPlayer().getCharacter().getChr_statPoint());

					Server.getInstance().addPacket(dam.getPlayer(), new MyPacket(62, charVO));
					Server.getInstance().getCharacterDAO().saveState(dam.getPlayer().getCharacter());
				}
				//몹뒤졌다
				Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(41, mob.getMobVO()));

				gameMap.removeMob(mob);

				//아이템떨구기
				for(DropItem dropItem : Server.getInstance().getItemDAO().getDropItem(mob)) {
					if(Math.random()*10000<dropItem.getProbability())
						Server.getInstance().getGameManager().createItem(dropItem.getItem_idx(), mapid, mob.getX(), mob.getY());
				}
			}
		}
	}

	private void disconnectCharacter(Player player) throws IOException{
		if(player.getCharacter()!=null) {
			Server.getInstance().getGameManager().leaveMap(player);
		}
	}

	private void notifyPositionHandler(OtherCharacterVO infoCharacter) throws IOException {
		int mapid = infoCharacter.getChr_mapid();
		GameMap gameMap = Server.getInstance().getGame().getExistMap().get(mapid);

		// 서버에 있는 자기자신의 객체의 위치를 바꾸는 코드
		player.getCharacter().updateCharacter(infoCharacter);
		Server.getInstance().addPacket(gameMap.getPlayerList(), new MyPacket(25, this.player.getCharacter().makeInfoCharacter()));
		
	}

	// synchronize 한이유는 createMap을 두개생성해서 겹쳐버릴수가 있다.
	private synchronized void requestMapHandler(MapVO gameMap) throws IOException {
		Map<Integer, GameMap> existMap = Server.getInstance().getGame().getExistMap();
		if(!existMap.containsKey(gameMap.getMap_idx()))
			Server.getInstance().getGame().createMap(gameMap.getMap_idx());
		Server.getInstance().getGameManager().joinMap(player, gameMap.getMap_idx());
	}

	private void requestCharacterHandler(CharacterVO characterVO) throws IOException {
		CharacterVO charVO = characterVO;
		charVO.setAcc_idx(player.getAccount().getAcc_idx());
		charVO = Server.getInstance().getCharacterDAO().getCharacter(charVO);

		if(charVO!=null)
		{
			player.setCharacter(charVO);
			Server.getInstance().addPacket(player, new MyPacket(23, charVO));
		}
	}

	private void createCharacterHandler(CharacterVO characterVO) {
		CharacterVO charVO = characterVO;
		charVO.setAcc_idx(player.getAccount().getAcc_idx());
		Server.getInstance().getCharacterDAO().createCharacter(charVO);
	}

	public void loginHandler(AccountVO accountVO) throws IOException
	{
		AccountVO avo = Server.getInstance().getAccountDAO().login(accountVO);
		if(avo != null)
		{
			// 새로접속한 계정이 기존 접속자랑 같은경우
			for(Player player : Server.getInstance().getPlayerList()) {
				if(player.getAccount()!=null && player.getAccount().getAcc_idx()==avo.getAcc_idx())
					player.getConnectionManager().getSocket().close();
			}
			Server.getInstance().addPacket(player, new MyPacket(13, avo));
			player.setAccount(avo);
		}
	}

	public void registerHandler(AccountVO accountVO)
	{
		Server.getInstance().getAccountDAO().createAccount(accountVO);
	}

	public void requestFindIDEmailHandler(AccountVO accountVO) throws IOException {
		Server.getInstance().geteMailSender().sendAuthEMail(accountVO);
	}

	public void requestFindPWEmailHandler(AccountVO accountVO) {
		//이메일받아오기
		AccountVO avo = Server.getInstance().getAccountDAO().getId(accountVO);
		Server.getInstance().geteMailSender().sendAuthEMail(avo);
		//		Server.getInstance().getSendPacketQueue().add(new SendPacket(player, new MyPacket()));
	}

	public void requestFindIDAuthHandler(AuthenticationVO authenticationVO) throws IOException {
		AccountVO accountVO = new AccountVO();
		accountVO.setAcc_email(authenticationVO.getEmail());
		if(authenticationVO.getAuthNumber().equals(Server.getInstance().geteMailSender().getAuthMap().get(authenticationVO.getEmail()))){
			List<AccountVO> avo = Server.getInstance().getAccountDAO().findIDList(accountVO);
			for (int i = 0; i < avo.size(); i++) 
				Server.getInstance().addPacket(player, new MyPacket(142, avo.get(i)));
		}
	}

	public void requestFindPWAuthHandler(AuthenticationVO authenticationVO) {
		//정보추가
		AccountVO accountVO = new AccountVO();
		accountVO.setAcc_id(authenticationVO.getId());

		System.out.println("--------------------------");
		for(String str : Server.getInstance().geteMailSender().getAuthMap().keySet())
		{
			System.out.println(str + " : " + Server.getInstance().geteMailSender().getAuthMap().get(str));
		}
		System.out.println("--------------------------");

		System.out.println("auth id" + authenticationVO.getId());
		System.out.println("auth num" + authenticationVO.getAuthNumber());
		if(authenticationVO.getAuthNumber().equals(Server.getInstance().geteMailSender().getAuthMap().get(authenticationVO.getId()))){
			Server.getInstance().addPacket(player, new MyPacket(152, accountVO));
		}
	}

	public void requestRegisterEmailHandler(AccountVO accountVO) {
		Server.getInstance().geteMailSender().sendAuthEMail(accountVO);
	}

	public void requestRegisterAuthHandler(AuthenticationVO authenticationVO) {
		AccountVO accountVO = new AccountVO();
		accountVO.setAcc_email(authenticationVO.getEmail());
		System.out.println(authenticationVO.getAuthNumber());
		System.out.println(authenticationVO.getEmail());
		System.out.println(Server.getInstance().geteMailSender().getAuthMap().get(authenticationVO.getEmail()));
		if(authenticationVO.getAuthNumber().equals(Server.getInstance().geteMailSender().getAuthMap().get(authenticationVO.getEmail()))){
			AuthenticationVO authVO = new AuthenticationVO();
			authVO.setConfirm(true);
			System.out.println("시발보냈따");
			Server.getInstance().addPacket(player, new MyPacket(162, authVO));
		}
	}

	public void changePassword(AccountVO accountVO) {
		try {
			Server.getInstance().getAccountDAO().changePassword(accountVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

