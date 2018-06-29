package bot;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class CostsBot extends TelegramLongPollingBot {
	static Long chatId;
	static String nameUser;
	static ConcurrentHashMap<Long, ThreadForUser> chatIdThreadMap = new ConcurrentHashMap<Long, ThreadForUser>(); // потокобезопасное
	// хранилище всех
	// нитей,которые
	// запущены
	ThreadForUser thread;
	static ConcurrentHashMap<Long, Integer> counterMap = new ConcurrentHashMap<Long, Integer>(); // счетчик
																									// сообщений
																									// для
																									// каждого
	// пользователя

	// хранилище флагов, нужно для того,чтобы правильно определять набор
	// клавиатуры
	static ConcurrentHashMap<Long, Boolean> flagIncomeMap = new ConcurrentHashMap<Long, Boolean>();
	static ConcurrentHashMap<Long, Boolean> flagConsumptionMap = new ConcurrentHashMap<Long, Boolean>();
	public static boolean isIncome = false; // Доход
	public static boolean isConsumption = false; // Расход

	public String getBotToken() {
		// System.out.println("Ready! Token");
		return "597710015:AAFvYu0rDwE0vYxhaPQ6gsSgiRLXqFUACK4";
	}

	public String getBotUsername() {
		System.out.println("Ready! UserName");
		return "forCosts_bot";
	}

	public void onUpdateReceived(Update e) {
		// Различные проверки и заполнение необходимыми данными
		// Типа флагов, или заполнение массива ответов пользователю
		// *********************ФЛАГИ**НАЧАЛО**********************************//
		if (CounterEvents.messages.isEmpty()) {
			CounterEvents.addMessages();
		}
		if (e.getMessage().getText().equals("Доход")) {
			isIncome = true;
			flagIncomeMap.put(e.getMessage().getChatId(), isIncome);
		} else if (e.getMessage().getText().equals("Расход")) {
			isConsumption = true;
			flagConsumptionMap.put(e.getMessage().getChatId(), isConsumption);
		}
		// *********************ФЛАГИ**КОНЕЦ**********************************//
		String text = null;
		chatId = e.getMessage().getChatId();

		// Учим бота реагировать только на команды
		// *****************COMMANDS**********************//
		if (e.getMessage().getText().equals("/start")) {
			System.out.println("Регестрируем нового пользователя.");
			text = "Приветствую! Как я могу к Вам обращаться?";
			sendMsg(e.getMessage(), text);
		} else
			try {
				//Подумать, везде ли нужны проверки того ,что пользователь зарегестрирован
				if (e.getMessage().getText().length() > 0 && !DataBase.checkUser(chatId)) {
					nameUser = e.getMessage().getText();
					DataBase.regNewUser();
					text = "Очень приятно, " + nameUser + "! \nЧтобы узнать список команд, введите \"/help\""
							+ "\n Чтобы узнать подробнее о боте, введите \"/about\"";
					sendMsg(e.getMessage(), text);
				} else if (e.getMessage().getText().equals("/help") && DataBase.checkUser(chatId)) {
					text = "Список команд:  ";
					sendMsg(e.getMessage(), text);
				} else if (e.getMessage().getText().equals("/about") && DataBase.checkUser(chatId)) {
					text = "О боте:  ";
					sendMsg(e.getMessage(), text);
				} else if (e.getMessage().getText().equals("/statistic") && DataBase.checkUser(chatId)) {
					System.out.println("Статистика");
				} else if (DataBase.checkUser(chatId)){
					if (chatIdThreadMap.containsKey(chatId)) {
						for (ConcurrentHashMap.Entry entry : chatIdThreadMap.entrySet()) {
							if (chatId.equals(entry.getKey())) {
								thread = (ThreadForUser) entry.getValue();
								// **********СЧЕТЧИК СООБЩЕНИЙ НАЧАЛО****************//
								// В зависимости от значения, выводит пользователю то
								// или иное сообщение
								for (ConcurrentHashMap.Entry objCounter : counterMap
										.entrySet()) {
									if (chatId.equals(objCounter.getKey())) {
										Integer count = (Integer) objCounter.getValue();
										count++;
										text = CounterEvents.messages.get(count);
										counterMap.put(chatId, count);
									}
								}
								// **********СЧЕТЧИК СООБЩЕНИЙ КОНЕЦ****************//
								thread.setUpdate(e);
								thread.setChatId(chatId);
								sendMsg(e.getMessage(), text);
							}
						}

					} else {
						// **********СЧЕТЧИК СООБЩЕНИЙ НАЧАЛО****************//
						Integer count = 0;
						counterMap.put(chatId, count);
						text = CounterEvents.messages.get(count);
						// **********СЧЕТЧИК СООБЩЕНИЙ КОНЕЦ****************//
						// System.out.println("Long.toString(chatId) = " +
						// Long.toString(chatId));
						thread = new ThreadForUser(Long.toString(chatId));
						chatIdThreadMap.put(chatId, thread);
						thread.setUpdate(e);
						thread.setChatId(chatId);
						thread.start();
						sendMsg(e.getMessage(), text);
					}
				}
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		// *****************COMMANDS*КОНЕЦ*****************//
	}

	@SuppressWarnings("deprecation")
	public void sendMsg(Message msg, String text) {
		SendMessage s = new SendMessage();
		s.enableMarkdown(true);
		// Обязательно строчку ниже менять на нормального бота!!!!
		BotKeyboardTest.setButtons(s); // обращаемся к клавиатуре в классе //
										// BotKeyboard
		s.setChatId(msg.getChatId());

		s.setText(text);
		try {
			sendMessage(s);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
