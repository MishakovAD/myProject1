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
	private static String username = "user";
	private static String password = "hedghog"; //для VPS
	private static String nameUser;

	static boolean checkUser = false;

	// Для отдельного запуска работы с БД
	public static void connect() throws SQLException, InstantiationException, IllegalAccessException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loading success!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Открываем соединение
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connected.");

		Statement statement = null;
		statement = con.createStatement();
//	// Создание таблицы в БД
		String SQL = "CREATE TABLE Costs " +
				 "(id INTEGER not NULL AUTO_INCREMENT, " +
				 " idFromTelegramBot INTEGER not NULL, " +
				 " dateCreated DATETIME not NULL, " +
				 " userName VARCHAR (100), " +
				 " typeCost VARCHAR (100), " +
				 " amount VARCHAR (50) not NULL, " +
				 " cause VARCHAR (100), " +
				 " PRIMARY KEY (id))";
		
		 statement.executeUpdate(SQL);
		 System.out.println("Table successfully created...");
	}

	// Проверка: зарегестрирован ли уже такой пользователь.
	public static boolean checkUser(Long id) throws SQLException, InstantiationException, IllegalAccessException {
		Connection con = DriverManager.getConnection(url, username, password);

		Statement statement = null;
		statement = con.createStatement();

		String SQL_select_all = "select * from costs;";
		ResultSet rs = statement.executeQuery(SQL_select_all);
		while (rs.next()) {
			if (id == rs.getInt(2)) {
				nameUser = rs.getString(4);
				System.out.println(nameUser + ": Пользователь уже зарегестрирован!");
				return true;
			}
			else {
				return false;
			}
			
		}
		return false;
	}

	public static void regNewUser() throws SQLException, InstantiationException, IllegalAccessException {
		// Добавить и распарсить правильно дату,чтобы была возможность отслеживать дату
		// регистрации пользователя
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Регистрация нового пользователя!");

		Statement statement = null;
		statement = con.createStatement();

		String SQL_insert_new_user = "insert into Costs (idFromTelegramBot, dateCreated, userName, "
				+ "typeCost, amount, cause)"
				+ " values (" + CostsBot.chatId + ", now(), '"
				+ CostsBot.nameUser + "', 'reg', 0, 'Registration');";
		
		System.out.println("ID: " + CostsBot.chatId);
		System.out.println("Name: " + CostsBot.nameUser);
		
		statement.execute(SQL_insert_new_user);
	}

	public static void addCost(Long id, String typeCost, String amount, String cause)
			throws SQLException, InstantiationException, IllegalAccessException {
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connected.");
		Statement statement = null;
		statement = con.createStatement();
		
		//Вопрос, где и как лучше получать имя. Сейчас, при вставке новой записи, или же потом, при выводе статистики
		//Пока проверяю сейчас и вставляю методом
		checkUser(id);
		//Вместо переменной date попробуем функцию now(). Распознает или нет, увидим
		String SQL_insert_cost = "insert into Costs (idFromTelegramBot, dateCreated, userName, "
				+ "typeCost, amount, cause)" + " values ("
				+ id + ", " + "now()" + ", '" 
				+ nameUser + "', '" + typeCost + "', "  + amount + ", '" + cause + "');";
		statement.execute(SQL_insert_cost);
	}
	
	//Получение всей информации из БД
	public static void getDBData() throws SQLException, InstantiationException, IllegalAccessException {
		Connection con = DriverManager.getConnection(url, username, password);
		Statement statement = null;
		statement = con.createStatement();

		String SQL_select_all = "select * from costs;";
		ResultSet rs = statement.executeQuery(SQL_select_all);
		while (rs.next()) {
			//1 элемент - это pk, а уже потом идет отсчет элементов
			System.out.println("Данные: " + rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", "
					+ rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", "+ rs.getString(7) + ", ");
		}
	}

	public static void main(String[] args) {
		try {
			//connect();
			//addCost("2000 Hohoho");
			getDBData();
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

}