package mrhi.adventure.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mrhi.adventure.model.vo.AccountVO;
import mrhi.adventure.model.vo.CharacterVO;
import mrhi.adventure.model.vo.ItemVO;

public class CharacterDAO extends DataDAO {

	private String nameCheck_sql = "select * from t_character where chr_name=?";
	private String createCharacter_sql = "insert into t_character values (default, ?, ?, 1, 1, 0, ?, 0, 4, 4, 4, 4, 200, 200, 200, 200, 10, 0, 0, 1000, 370, 270, 0)";
	private String deleteCharacter_sql = "delete from t_character where acc_idx=? and chr_idx=?";
	private String getCharacterList_sql = "select * from t_character where acc_idx=?";
	private String getCharacter_sql = "select * from t_character where acc_idx=? and chr_idx=?";
	private String saveState_sql = "update t_character set chr_exp=?, chr_level=?, chr_skillpoint=?, chr_statpoint=? where chr_idx=?";
	private String saveStat_sql = "update t_character set chr_str=?, chr_dex=?, chr_int=?, chr_pro=?, chr_statpoint=? where chr_idx=?";
	private String locationSave_sql = "update t_character set chr_x=?, chr_y=?, chr_mapid=? where chr_idx=?";
	private String saleItemMoney_sql = "update t_character set chr_money=? where chr_idx=?";
	private String moneyCheck_sql = "select chr_money from t_character where chr_idx=?";

	public CharacterDAO() { }

	public void saveLocation(CharacterVO charVO) {
		executeUpdate(locationSave_sql, charVO.getChr_x(), charVO.getChr_y(), charVO.getChr_mapid(), charVO.getChr_idx());		
	}

	public void saveState(CharacterVO charVO) {
		executeUpdate(saveState_sql, charVO.getChr_exp(), charVO.getChr_level(), charVO.getChr_skillPoint(), charVO.getChr_statPoint(), charVO.getChr_idx());
	}
	public void saveStat(CharacterVO charVO) {
		executeUpdate(saveStat_sql, charVO.getChr_str(), charVO.getChr_dex(), charVO.getChr_int(), charVO.getChr_pro(), charVO.getChr_statPoint(), charVO.getChr_idx());
	}

	public void createCharacter(CharacterVO charVO) {
		rs = executeQuery(nameCheck_sql, charVO.getChr_name());
		try {
			if(rs.next())
			{
				System.out.println("중복 아이디 존재!");
				return;
			}
			executeUpdate(createCharacter_sql, charVO.getAcc_idx(), charVO.getChr_name(), charVO.getChr_job());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
	}

	public void deleteCharacter(AccountVO accountVO, CharacterVO charVO) {
		executeUpdate(deleteCharacter_sql, accountVO.getAcc_idx(), charVO.getChr_idx());
	}

	public List<CharacterVO> getCharacterList(AccountVO accountVO)
	{
		CharacterVO cvo = null;
		List<CharacterVO> retList = new ArrayList<>(); 
		rs = executeQuery(getCharacterList_sql, accountVO.getAcc_idx());
		try {
			while(rs.next())
			{
				cvo = new CharacterVO();
				cvo.setChr_idx(rs.getInt("chr_idx"));
				cvo.setAcc_idx(rs.getInt("acc_idx"));
				cvo.setChr_name(rs.getString("chr_name"));
				cvo.setChr_type(rs.getInt("chr_type"));
				cvo.setDirection(4);
				cvo.setChr_level(rs.getInt("chr_level"));
				cvo.setChr_exp(rs.getInt("chr_exp"));
				cvo.setChr_job(rs.getInt("chr_job"));
				cvo.setChr_money(rs.getInt("chr_money"));
				cvo.setChr_str(rs.getInt("chr_str"));
				cvo.setChr_dex(rs.getInt("chr_dex"));
				cvo.setChr_int(rs.getInt("chr_int"));
				cvo.setChr_pro(rs.getInt("chr_pro"));
				cvo.setChr_hp(rs.getInt("chr_hp"));
				cvo.setChr_mp(rs.getInt("chr_mp"));
				cvo.setChr_maxHp(rs.getInt("chr_maxhp"));
				cvo.setChr_maxMp(rs.getInt("chr_maxmp"));
				cvo.setChr_speed(rs.getInt("chr_speed"));
				cvo.setChr_statPoint(rs.getInt("chr_statpoint"));
				cvo.setChr_skillPoint(rs.getInt("chr_skillpoint"));
				cvo.setChr_mapid(rs.getInt("chr_mapid"));
				cvo.setChr_x(rs.getInt("chr_x"));
				cvo.setChr_y(rs.getInt("chr_y"));
				cvo.setChr_grade(rs.getInt("chr_grade"));
				retList.add(cvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retList;
	}

	public CharacterVO getCharacter(CharacterVO charVO)
	{
		CharacterVO retCVO = null;
		rs = executeQuery(getCharacter_sql, charVO.getAcc_idx(), charVO.getChr_idx());
		try {
			if(rs.next())
			{
				retCVO = new CharacterVO();
				retCVO.setChr_idx(rs.getInt("chr_idx"));
				retCVO.setAcc_idx(rs.getInt("acc_idx"));
				retCVO.setChr_name(rs.getString("chr_name"));
				retCVO.setChr_type(rs.getInt("chr_type"));
				retCVO.setDirection(4);
				retCVO.setChr_level(rs.getInt("chr_level"));
				retCVO.setChr_exp(rs.getInt("chr_exp"));
				retCVO.setChr_job(rs.getInt("chr_job"));
				retCVO.setChr_money(rs.getInt("chr_money"));
				retCVO.setChr_str(rs.getInt("chr_str"));
				retCVO.setChr_dex(rs.getInt("chr_dex"));
				retCVO.setChr_int(rs.getInt("chr_int"));
				retCVO.setChr_pro(rs.getInt("chr_pro"));
				retCVO.setChr_hp(rs.getInt("chr_hp"));
				retCVO.setChr_mp(rs.getInt("chr_mp"));
				retCVO.setChr_maxHp(rs.getInt("chr_maxhp"));
				retCVO.setChr_maxMp(rs.getInt("chr_maxmp"));
				retCVO.setChr_speed(rs.getInt("chr_speed"));
				retCVO.setChr_statPoint(rs.getInt("chr_statpoint"));
				retCVO.setChr_skillPoint(rs.getInt("chr_skillpoint"));
				retCVO.setChr_mapid(rs.getInt("chr_mapid"));
				retCVO.setChr_x(rs.getInt("chr_x"));
				retCVO.setChr_y(rs.getInt("chr_y"));
				retCVO.setChr_grade(rs.getInt("chr_grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retCVO;
	}

	public CharacterVO saleItemMoney(CharacterVO charVO, ItemVO itemVO) {
		CharacterVO characterVO = null;
		rs = executeQuery(moneyCheck_sql, charVO.getChr_idx());
		try {
			if (rs.next()) {
				characterVO = new CharacterVO();
				characterVO.setChr_money(rs.getInt("chr_money"));
			}
			int money = characterVO.getChr_money() + (itemVO.getItem_value()-itemVO.getItem_value()/5);
			executeUpdate(saleItemMoney_sql, money, charVO.getChr_idx());
			characterVO.setChr_money(money);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return characterVO;
	}

	public CharacterVO buyItemsMoney(CharacterVO charVO, ItemVO itemVO) {
		CharacterVO characterVO = null;
		rs = executeQuery(moneyCheck_sql, charVO.getChr_idx());
		try {
			if (rs.next()) {
				characterVO = new CharacterVO();
				characterVO.setChr_money(rs.getInt("chr_money"));
			}
			int money = characterVO.getChr_money() - itemVO.getItem_value();
			executeUpdate(saleItemMoney_sql, money, charVO.getChr_idx());
			characterVO.setChr_money(money);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return characterVO;
	}

}
