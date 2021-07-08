package jp.co.excite_software.edu.address_book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddressBook {
	static private String AB_UUID = "ab.uuid";
	static private String NAME = "ab.name";
	static private String KANA = "ab.kana";
	static private String ADDRESS = "ab.address";
	static private String MEMO = "ab.memo";
	static private String REGISTERD_DATETIME;
	static private String UPDATE_DATETIME;
	static private String MA_UUID;
	static private String MA_BOOK;
	static private String MA_SORT_ORDER;
	static private String MA_MAIL_ADDRESS = "ma.mail_address";
	static private String MA_REGISTERED_DATETIME;
	static private String MA_UPDATE_DATETIME;
	static private String PN_UUID;
	static private String PN_BOOK_UUID;
	static private String PN_SORT_ORDER;
	static private String PN_PHONE_NUMBER = "pn.phone_number";
	static private String PN_REGISTERED_DATETIME;
	static private String PN_UPDATE_DATETIME;
	static private String dbUrl = "jdbc:mysql://localhost:3306/learning?useUnicode=true&characterEncoding=utf8";
	static private String dbUser = "root";
	static private String dbPassword = "excite";

	List<Address> addressList = new ArrayList<Address>();

	String sql = "select * from ADDRESS_BOOK as ab left outer join MAIL_ADDRESS as ma on ab.uuid = ma.book_uuid left outer join PHONE_NUMBER pn on ab.uuid = pn.book_uuid order by ab.uuid asc, ma.SORT_ORDER asc,pn.SORT_ORDER asc";

	public List<Address> getAll() {
		connection();
		try (
				Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();) {
			//Tolistメソッドで一覧用に成形
			toList(rs);
			return addressList;
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	private List<Address> toList(ResultSet rs) {

		List<String> mailAddressList = new ArrayList<String>();
		List<String> phoneNumberList = new ArrayList<String>();

		try {
			//リストの要素番号
			int i = 0;
			int comparisonIndex = 0;
			while (rs.next()) {
				String uuid = rs.getString(AB_UUID);
				String name = rs.getString(NAME);
				String kana = rs.getString(KANA);
				String streetAddress = rs.getString(ADDRESS);
				String memo = rs.getString(MEMO);
				//一行前と比較
				if (i < 1) {
					mailAddressList.add(rs.getString(MA_MAIL_ADDRESS));
					phoneNumberList.add(rs.getString(PN_PHONE_NUMBER));
					//そのままモデルに詰める
					Address address = new Address(uuid, name, kana, mailAddressList, phoneNumberList, streetAddress,
							memo);
					addressList.add(address);
				} else {
					String previousUuid = addressList.get(comparisonIndex).getUuid();
					if (!uuid.equals(previousUuid)) {
						mailAddressList.add(rs.getString(MA_MAIL_ADDRESS));
						phoneNumberList.add(rs.getString(PN_PHONE_NUMBER));
						//そのままモデルに詰める
						Address address = new Address(uuid, name, kana, mailAddressList, phoneNumberList, streetAddress,
								memo);
						addressList.add(address);
						comparisonIndex++;
					}
				}
				i++;
			}
			return addressList;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public Address get(String uuid) {

		String sql = "select * from ADDRESS_BOOK ab where ab.uuid = '" + uuid + "'";
		connection();
		try (
				Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();) {
			List<String> mailAddressList = getMailAddressList(conn, uuid);
			List<String> phoneNumberList = getPhoneNumberList(conn, uuid);

			//要素数を3に合わせる。
			while (mailAddressList.size() < 4) {
				mailAddressList.add("");
			}
			while (phoneNumberList.size() < 4) {
				phoneNumberList.add("");
			}

			//初期化
			String name = null;
			String kana = null;
			String streetAddress = null;
			String memo = null;

			//電話番号、メールアドレス以外を取得
			while (rs.next()) {
				name = rs.getString(NAME);
				kana = rs.getString(KANA);
				streetAddress = rs.getString(ADDRESS);
				memo = rs.getString(MEMO);
			}
			Address address = new Address(uuid, name, kana, mailAddressList, phoneNumberList, streetAddress,
					memo);

			return address;
		} catch (SQLException e) {
			e.printStackTrace();
			Address address = null;
			return address;
		}
	}

	private List<String> getMailAddressList(Connection conn, String uuid) {
		String sql = "Select ma.mail_address from MAIL_ADDRESS ma where ma.book_uuid ='" + uuid + "'";
		try (PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();) {
			//リストに結果を格納
			List<String> mailAddressList = new ArrayList<String>();
			while (rs.next()) {
				mailAddressList.add(rs.getString(MA_MAIL_ADDRESS));
			}
			return mailAddressList;
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	private List<String> getPhoneNumberList(Connection conn, String uuid) {
		String sql = "Select pn.phone_number from PHONE_NUMBER pn where pn.book_uuid = '" + uuid + "'";
		try (PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();) {
			//リストに結果を格納
			List<String> phoneNumberList = new ArrayList<String>();
			while (rs.next()) {
				phoneNumberList.add(rs.getString(PN_PHONE_NUMBER));
			}
			return phoneNumberList;
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	//削除
	public void delete(String uuid) {
		String sql = "delete from ADDRESS_BOOK where uuid = '" + uuid + "'";
		connection();
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pStmt = conn.prepareStatement(sql);) {
			try {
				int rs = pStmt.executeUpdate();
				//TODO rsが0の時エラーメッセージ
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//登録
	public Address add(Address address) {
		String addressBookSql = "INSERT INTO ADDRESS_BOOK(UUID,NAME,KANA,ADDRESS,MEMO,REGISTERED_DATETIME,UPDATED_DATETIME) VALUES(?,?,?,?,?,?,?)";
		String mailAddressSql = "INSERT INTO MAIL_ADDRESS(UUID,BOOK_UUID,SORT_ORDER,MAIL_ADDRESS,REGISTERED_DATETIME,UPDATED_DATETIME) VALUES(?,?,?,?,?,?)";
		String phoneNumberSql = "INSERT INTO PHONE_NUMBER(UUID,BOOK_UUID,SORT_ORDER,PHONE_NUMBER,REGISTERED_DATETIME,UPDATED_DATETIME) VALUES(?,?,?,?,?,?)";

		String name = address.getName();
		String kana = address.getKana();
		List<String> mailAddressList = address.getMailAddressList();
		List<String> phoneNumberList = address.getPhoneNumberList();
		String Address = address.getAddress();
		String memo = address.getMemo();

		//UUIDを生成
		UUID uuid = UUID.randomUUID();

		//現在時刻を生成
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String currentTime = sdf.format(date);

		//戻り値の生成
		Address addaddress = new Address(uuid.toString(), name, kana, mailAddressList, phoneNumberList, Address, memo);

		//登録処理
		connection();

		//アドレス帳テーブルに登録
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement addressBook = conn.prepareStatement(addressBookSql);) {
			try {
				addressBook.setString(1, uuid.toString());
				addressBook.setString(2, name);
				addressBook.setString(3, kana);
				addressBook.setString(4, Address);
				addressBook.setString(5, memo);
				addressBook.setString(6, currentTime);
				addressBook.setString(7, null);
				int result = addressBook.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//mail_Addressテーブルに登録
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement mailAddress = conn.prepareStatement(mailAddressSql);) {
			try {
				//ソート順用のカウンタ
				int sortNumber = 1;
				int nullNumber = 3;
				for (int i = 1; i <= mailAddressList.size(); i++) {
					//mail_Addressテーブル用のUUIDを生成
					UUID mailAddressUuid = UUID.randomUUID();
					mailAddress.setString(1, mailAddressUuid.toString());
					mailAddress.setString(2, uuid.toString());

					//nullだった場合飛ばして最後にnull詰める
					if (mailAddressList.get(i - 1) != null) {
						mailAddress.setString(3, String.valueOf(sortNumber));
						mailAddress.setString(4, mailAddressList.get(i - 1));
						sortNumber++;
					} else {
						//ソート順を上書き
						mailAddress.setString(3, String.valueOf(nullNumber));
						mailAddress.setString(4, null);
						nullNumber--;
					}
					mailAddress.setString(5, currentTime);
					mailAddress.setString(6, null);
					int result = mailAddress.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//メールアドレス
		return addaddress;
	}

	private void connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
