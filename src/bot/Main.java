package bot;
// наъгюрекэмн!!!!!!!!!!!!!!!!!!!!!!!!!!!
//опх сярюмнбйе MySQL бшярюбхрэ опюбхкэмсч йндхпнбйс х опнбепхрэ
//наъгюрекэмн!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.sql.SQLException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.sql.SQLException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException {
		ApiContextInitializer.init(); // хМХЖХЮКХГХПСЕЛ ЮОХ
		TelegramBotsApi botapi = new TelegramBotsApi();
		System.out.println("Reg Bot");
		try {
			botapi.registerBot(new CostsBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}