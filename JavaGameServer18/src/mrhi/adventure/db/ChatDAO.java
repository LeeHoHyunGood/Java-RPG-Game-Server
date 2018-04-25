package mrhi.adventure.db;

import mrhi.adventure.model.vo.ChatVO;

public class ChatDAO extends DataDAO{
	private String chatLog_sql = "insert into log_chat values (default, current_timestamp, ?, ?)";
	
	public void generateChatLog(ChatVO chatVO) {
		executeUpdate(chatLog_sql, chatVO.getChr_idx(), chatVO.getText());
	}
}
