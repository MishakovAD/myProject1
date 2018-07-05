package bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	private static String url = "jdbc:mysql://localhost:3306/costs_db?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "user";
	private static String password = "hedghog"; // для VPS
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
		String SQL = "CREATE TABLE Costs character set utf8" + "(id INTEGER not NULL AUTO_INCREMENT, "
				+ " idFromTelegramBot INTEGER not NULL, " + " dateCreated DATETIME not NULL, "
				+ " userName VARCHAR (100), " + " typeCost VARCHAR (100), " + " amount VARCHAR (50) not NULL, "
				+ " cause VARCHAR (100), " + " PRIMARY KEY (id))";

		statement.executeUpdate(SQL);
		System.out.println("Table successfully created...");
	}

	// Проверка: зарегестрирован ли уже такой пользователь.
	public static boolean checkUser(Long id) throws SQLException, InstantiationException, IllegalAccessException {
		Connection con = DriverManager.getConnection(url, username, password);

		Statement statement = null;
		statement = con.createStatement();

		String SQL_select_all = "select * from Costs;";
		ResultSet rs = statement.executeQuery(SQL_select_all);
		while (rs.next()) {
			if (id == rs.getInt(2)) {
				nameUser = rs.getString(4);
				System.out.println(nameUser + ": Пользователь уже зарегестрирован!");
				return true;
			}

		}
		return false;
	}

	public static void regNewUser(String name) throws SQLException, InstantiationException, IllegalAccessException {
		// Добавить и распарсить правильно дату,чтобы была возможность отслеживать дату
		// регистрации пользователя
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Регистрация нового пользователя!");

		Statement statement = null;
		statement = con.createStatement();

		String SQL_insert_new_user = "insert into Costs (idFromTelegramBot, dateCreated, userName, "
				+ "typeCost, amount, cause)" + " values (" + CostsBot.chatId + ", now(), '" + name
				+ "', 'reg', '0', 'Registration');";

		System.out.println("ID: " + CostsBot.chatId);
		System.out.println("Name: " + CostsBot.nameUser);

		statement.execute(SQL_insert_new_user);
		System.out.println("User registred ");
	}

	public static void addCost(Long id, String typeCost, String amount, String cause)
			throws SQLException, InstantiationException, IllegalAccessException {
		Connection con = DriverManager.getConnection(url, username, password);
		System.out.println("Connected.");
		Statement statement = null;
		statement = con.createStatement();

		// Вопрос, где и как лучше получать имя. Сейчас, при вставке новой записи, или
		// же потом, при выводе статистики
		// Пока проверяю сейчас и вставляю методом
		checkUser(id);
		// Вместо переменной date попробуем функцию now(). Распознает или нет, увидим
		String SQL_insert_cost = "insert into Costs (idFromTelegramBot, dateCreated, userName, "
				+ "typeCost, amount, cause)" + " values (" + id + ", " + "now()" + ", '" + nameUser + "', '" + typeCost
				+ "', " + amount + ", '" + cause + "');";
		statement.execute(SQL_insert_cost);
	}

	// Получение всей информации из БД
	public static String getDBData(Long id) 
			throws SQLException, InstantiationException, IllegalAccessException {
		String costs = "Совершенные операции: \n";
		int summIncome = 0;
		int summConsumption = 0;
		int counter = 0;
		Connection con = DriverManager.getConnection(url, username, password);
		Statement statement = null;
		statement = con.createStatement();

		String SQL_select_all = "SELECT * FROM Costs WHERE idFromTelegramBot=" 
		+ id + " ORDER BY id DESC;";
		ResultSet rs = statement.executeQuery(SQL_select_all);
		while (rs.next()) {
			// 1 элемент - это pk, а уже потом идет отсчет элементов
			System.out.println(
					"Данные: " + rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", " + rs.getString(4)
							+ ", " + rs.getString(5) + ", " + rs.getString(6) + ", " + rs.getString(7) + ", ");
			costs += "\n";
			costs += "Дата: " + rs.getString(3) + "\n" +
					"Тип операции: " + rs.getString(5) + "\n" +
					"Количество денег: " + rs.getString(6) + "\n" +
					"Откуда/Куда: " + rs.getString(7) + "\n";
			costs += "\n";
			try {
				if (rs.getString(5).equalsIgnoreCase("Доход")) {
					summIncome += Float.valueOf(rs.getString(6));
				} else if (rs.getString(5).equalsIgnoreCase("Расход")) {
					summConsumption += Float.valueOf(rs.getString(6));
				}
			} catch (Exception e) {
				System.out.println("Не увдалось посчитать сумму");
			}
			counter++;
			if (counter > 15) {
				//Выводим только последние 15 записей
				break;
			}
		}
		costs += "Всего потрачено: " + summConsumption + "\n";
		costs += "Всего получено: " + summIncome;
		return costs;
	}

//	public static void deleteDBData() {
//		costs = null;
//	}

	public static void main(String[] args) {
		try {
			// connect();
			// addCost("2000 Hohoho");
			getDBData((long) 1);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

}
