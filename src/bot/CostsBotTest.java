package bot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.CostsBotTest.UpdateId;

public class CostsBotTest extends TelegramLongPollingBot {
	public Long chatId;
	public static String Name;
	public static String Costs;
	public static ArrayList<String> textMessage = new ArrayList<>();
	public static ArrayList<Update> update = new ArrayList<>();
	// Пробую сделать многопоточность. создаем хранилище для айдишников
	// пользователей,которые пишут
	public static ArrayList<Long> chatIdArray = new ArrayList<Long>();
	public static ArrayList<UpdateId> updateIdArray = new ArrayList<UpdateId>();
	public static Set<Long> setId = new LinkedHashSet<>();
	static ConcurrentHashMap<Long, ThreadForUser> chatIdThreadMap = new ConcurrentHashMap<>();
	ThreadForUser thread;
	ThreadForUser thread2;

	public String getBotToken() {
		// System.out.println("Ready! Token");
		return "597710015:AAFvYu0rDwE0vYxhaPQ6gsSgiRLXqFUACK4";
	}

	public String getBotUsername() {
		System.out.println("Ready! UserName");
		return "forCosts_bot";
	}

	public void removeId(Set s, Object o) {
		s.remove(o);
	}

	public void onUpdateReceived(Update e) {

		// System.out.println("updateIdArray.size() = " + updateIdArray.size());
		updateIdArray.add(new UpdateId(e.getMessage().getChatId(), e));
		int count = updateIdArray.size();
		chatId = e.getMessage().getChatId();
//		if (chatIdThreadMap.isEmpty()) {
//			//System.out.println("Long.toString(chatId) = " + Long.toString(chatId));
//			thread = new ThreadForUser(Long.toString(chatId), count);
//			chatIdThreadMap.put(chatId, thread);
//			thread.start();
//			thread.setChatId(chatId);
//		}
		if (chatIdThreadMap.containsKey(chatId)) {
			// Нужно решить проблему с тем, чтобы данные добавлялись именно в нужный поток с
			// Id
			for (ConcurrentHashMap.Entry entry : chatIdThreadMap.entrySet()) {
			    if (chatId.equals(entry.getKey())) {
					thread = (ThreadForUser) entry.getValue();
					System.out.println(thread.getName());
					thread.setIndex(count);
					thread.setChatId(chatId);
			    }
			}

		} else {
			System.out.println("Long.toString(chatId) = " + Long.toString(chatId));
			thread = new ThreadForUser(Long.toString(chatId), count);
			chatIdThreadMap.put(chatId, thread);
			thread.start();			
			thread.setChatId(chatId);
		}
		System.out.println("chatIdThreadMap.size() = " + chatIdThreadMap.size());

	}

	@SuppressWarnings("deprecation")
	private void sendMsg(Message msg, String text) {
		SendMessage s = new SendMessage();
		s.enableMarkdown(true);
		// Обязательно строчку ниже менять на нормального бота!!!!
		BotKeyboardTest.setButtons(s); // обращаемся к клавиатуре в классе BotKeyboard
		s.setChatId(msg.getChatId());

		s.setText(ThreadForUser.getMessageTextForUser());
		try {
			sendMessage(s);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	class UpdateId {
		private Long id;
		private Update event;

		public UpdateId(Long id, Update event) {
			super();
			this.id = id;
			this.event = event;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Update getEvent() {
			return event;
		}

		public void setEvent(Update event) {
			this.event = event;
		}
	}

}
