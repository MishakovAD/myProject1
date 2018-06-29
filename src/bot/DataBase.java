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
	private static String typeCost;

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
				 " dateCreated DATE not NULL, " +
				 " userName VARCHAR (50), " +
				 " typeCost VARCHAR (50), " +
				 " amount INTEGER not NULL, " +
				 " cause VARCHAR (1000), " +
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
			//Вывод данных (пока заккоменчен,чтобы не мешался)
//			System.out.println("Данные: " + rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", "
//					+ rs.getString(4) + ", " + rs.getInt(5) + ", " + rs.getString(6) + ", ");
			if (id == rs.getInt(2)) {
				//checkUser = true;
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

	public static void addCost(String text) throws SQLException, InstantiationException, IllegalAccessException {
		// Добавить и распарсить правильно дату,чтобы была возможность отслеживать дату
		// регистрации пользователя
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connected.");
		Statement statement = null;
		statement = con.createStatement();

		// Получаем содержимое текста
		//олучение данных поменять. Сейчас вид  другой. 
		//Лучше вообще сделать отдельным методом или дажеклассом на обработку
		String amount;
		String cause;
		int indexOfSpace = text.indexOf(" "); // индекс символа, разделяющего сумму и покупки
		amount = text.substring(0, indexOfSpace);
		cause = text.substring(5);
		// Получение текущей даты и времени
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		System.out.println("Date: " + date);
		
		//Вопрос, где и как лучше получать имя. Сейчас, при вставке новой записи, или же потом, при выводе статистики
		//Пока проверяю сейчас и вставляю методом
		//typeCost, как и все остальное будем получать отдельным методом из массива, в который будем сохранять данные.
		checkUser(CostsBot.chatId);
		//Вместо переменной date попробуем функцию now(). Распознает или нет, увидим
		String SQL_insert_cost = "insert into Costs (idFromTelegramBot, dateCreated, userName, "
				+ "typeCost, amount, cause)" + " values ("
				+ CostsBot.chatId + ", " + "now()" + ", '" 
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
			System.out.println("Данные: " + rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", "
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

// Код, который использовался ранее, но более не нужен

//// Создание таблицы в БД
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