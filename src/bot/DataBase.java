package bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBase {
	private static String url = "jdbc:mysql://localhost:3306/costs_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "root";
	private static String password = "root";
	private static String nameUser;

	static boolean checkUser = false;

	// ��� ���������� ������� ������ � ��
	public static void connect() throws SQLException, InstantiationException, IllegalAccessException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loading success!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// ��������� ����������
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connected.");

		Statement statement = null;
		statement = con.createStatement();
//	// �������� ������� � ��
//		 String SQL = "CREATE TABLE costs " +
//		 "(id INTEGER not NULL AUTO_INCREMENT, " +
//		 " IDFromTelegramBot INTEGER not NULL, " +
//		 " DateCreated DATE not NULL, " +
//		 " Name VARCHAR (50), " +
//		 " Amount INTEGER not NULL, " +
//		 " Cause VARCHAR (1000), " +
//		 " PRIMARY KEY (id))";
//		
//		 statement.executeUpdate(SQL);
//		 System.out.println("Table successfully created...");
	}

	// ��������: ��������������� �� ��� ����� ������������.
	public static void checkUser() throws SQLException, InstantiationException, IllegalAccessException {
		//System.out.println("Start check!");
		Connection con = DriverManager.getConnection(url, username, password);
		//System.out.println("Connected.");

		Statement statement = null;
		statement = con.createStatement();

		String SQL_select_all = "select * from costs;";
		ResultSet rs = statement.executeQuery(SQL_select_all);
		while (rs.next()) {
			//����� ������ (���� �����������,����� �� �������)
//			System.out.println("������: " + rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", "
//					+ rs.getString(4) + ", " + rs.getInt(5) + ", " + rs.getString(6) + ", ");
//			if (CostsBot.ChatId == rs.getInt(2)) {
//				checkUser = true;
//				nameUser = rs.getString(4);
//			}
			//�������� ������ ����
			if (CostsBotTest.ChatId == rs.getInt(2)) {
				checkUser = true;
				nameUser = rs.getString(4);
			}
		}
		System.out.println(nameUser + ": ������������ ��� ���������������!");
		System.out.println("���������� CheckUser: " + DataBase.checkUser);
	}

	public static void regNewUser() throws SQLException, InstantiationException, IllegalAccessException {
		// �������� � ���������� ��������� ����,����� ���� ����������� ����������� ����
		// ����������� ������������
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connected.");

		Statement statement = null;
		statement = con.createStatement();

		String SQL_insert_new_user = "insert into costs (IDFromTelegramBot, DateCreated, Name, Amount, Cause)"
				+ " values (" + CostsBot.ChatId + ", 20180613000001, '" + CostsBot.Name + "', 0, 'Registration');";
		statement.execute(SQL_insert_new_user);
	}

	public static void addCost(String text) throws SQLException, InstantiationException, IllegalAccessException {
		// �������� � ���������� ��������� ����,����� ���� ����������� ����������� ����
		// ����������� ������������
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connected.");
		Statement statement = null;
		statement = con.createStatement();

		// �������� ���������� ������
		String amount;
		String cause;
		int indexOfSpace = text.indexOf(" "); // ������ �������, ������������ ����� � �������
		amount = text.substring(0, indexOfSpace);
		cause = text.substring(5);
		// ��������� ������� ���� � �������
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		System.out.println("Date: " + date);
		
		String SQL_insert_cost = "insert into costs (IDFromTelegramBot, DateCreated, Name, Amount, Cause)" + " values ("
				+ CostsBot.ChatId + ", " + date + ", '" + nameUser + "', " + amount + ", '" + cause + "');";
		statement.execute(SQL_insert_cost);
	}
	
	public static void getDBData() throws SQLException, InstantiationException, IllegalAccessException {
		Connection con = DriverManager.getConnection(url, username, password);
		Statement statement = null;
		statement = con.createStatement();

		String SQL_select_all = "select * from costs;";
		ResultSet rs = statement.executeQuery(SQL_select_all);
		while (rs.next()) {
			System.out.println("������: " + rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", "
					+ rs.getString(4) + ", " + rs.getInt(5) + ", " + rs.getString(6) + ", ");
		}
	}

	public static void main(String[] args) {
		try {
			//connect();
			//addCost("2000 Hohoho");
			getDBData();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

// ���, ������� ������������� �����, �� ����� �� �����

//// �������� ������� � ��
// String SQL = "CREATE TABLE costs " +
// "(id INTEGER not NULL AUTO_INCREMENT, " +
// " IDFromTelegramBot INTEGER not NULL, " +
// " DateCreated DATE not NULL, " +
// " Name VARCHAR (50), " +
// " Amount INTEGER not NULL, " +
// " Cause VARCHAR (1000), " +
// " PRIMARY KEY (id))";
//
// statement.executeUpdate(SQL);
// System.out.println("Table successfully created...");