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
	static ConcurrentHashMap<Long, ThreadForUser> chatIdThreadMap = new ConcurrentHashMap<>(); // потокобезопасное
																								// хранилище всех
																								// нитей,которые
																								// запущены
	ThreadForUser thread;
	static ConcurrentHashMap<Long, Integer> counterMap = new ConcurrentHashMap<>(); // счетчик сообщений для каждого
																					// пользователя

	// хранилище флагов, нужно для того,чтобы правильно определять набор клавиатуры
	static ConcurrentHashMap<Long, Boolean> flagIncomeMap = new ConcurrentHashMap<>();
	static ConcurrentHashMap<Long, Boolean> flagConsumptionMap = new ConcurrentHashMap<>();
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
		// ************************************************************************//
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
		// ************************************************************************//
		String text = null;
		chatId = e.getMessage().getChatId();
		if (chatIdThreadMap.containsKey(chatId)) {
			for (ConcurrentHashMap.Entry entry : chatIdThreadMap.entrySet()) {
				if (chatId.equals(entry.getKey())) {
					thread = (ThreadForUser) entry.getValue();
					// ************************************************************************//
					for (ConcurrentHashMap.Entry objCounter : counterMap.entrySet()) {
						if (chatId.equals(objCounter.getKey())) {
							Integer count = (Integer) objCounter.getValue();
							count++;
							text = CounterEvents.messages.get(count);
							counterMap.put(chatId, count);
						}
					}
					// ************************************************************************//
					thread.setUpdate(e);
					thread.setChatId(chatId);
					// System.out.println(thread.getName());
					sendMsg(e.getMessage(), text);
				}
			}

		} else {
			// ************************************************************************//
			Integer count = 0;
			counterMap.put(chatId, count);
			text = CounterEvents.messages.get(count);
			// ************************************************************************//
			// System.out.println("Long.toString(chatId) = " + Long.toString(chatId));
			thread = new ThreadForUser(Long.toString(chatId));
			chatIdThreadMap.put(chatId, thread);
			thread.setUpdate(e);
			thread.setChatId(chatId);
			thread.start();
			sendMsg(e.getMessage(), text);
		}
	}

	@SuppressWarnings("deprecation")
	public void sendMsg(Message msg, String text) {
		SendMessage s = new SendMessage();
		s.enableMarkdown(true);
		// Обязательно строчку ниже менять на нормального бота!!!!
		BotKeyboard.setButtons(s); // обращаемся к клавиатуре в классе // BotKeyboard
		s.setChatId(msg.getChatId());

		s.setText(text);
		try {
			sendMessage(s);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
