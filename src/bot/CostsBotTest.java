package bot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class CostsBotTest extends TelegramLongPollingBot {
	private Long chatId;
	static ConcurrentHashMap<Long, ThreadForUser> chatIdThreadMap = new ConcurrentHashMap<>();
	static ConcurrentHashMap<Long, Integer> counterMap = new ConcurrentHashMap<>(); //счетчик сообщений для каждого пользователя
	ThreadForUser thread;

	public String getBotToken() {
		// System.out.println("Ready! Token");
		return "597710015:AAFvYu0rDwE0vYxhaPQ6gsSgiRLXqFUACK4";
	}

	public String getBotUsername() {
		System.out.println("Ready! UserName");
		return "forCosts_bot";
	}


	public void onUpdateReceived(Update e) {
		chatId = e.getMessage().getChatId();
		if (chatIdThreadMap.containsKey(chatId)) {
			
			
			for (ConcurrentHashMap.Entry entry : chatIdThreadMap.entrySet()) {
			    if (chatId.equals(entry.getKey())) {
					thread = (ThreadForUser) entry.getValue();
					thread.setUpdate(e);
					thread.setChatId(chatId);
					System.out.println(thread.getName());	
					sendMsg(e.getMessage(), ThreadForUser.getMessageTextForUser());
			    }
			}

		} else {
			System.out.println("Long.toString(chatId) = " + Long.toString(chatId));
			thread = new ThreadForUser(Long.toString(chatId));
			chatIdThreadMap.put(chatId, thread);
			thread.setUpdate(e);
			thread.setChatId(chatId);
			thread.start();	
			sendMsg(e.getMessage(), ThreadForUser.getMessageTextForUser());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendMsg(Message msg, String text) {
		SendMessage s = new SendMessage();
		s.enableMarkdown(true);
		// Обязательно строчку ниже менять на нормального бота!!!!
		//BotKeyboardTest.setButtons(s); // обращаемся к клавиатуре в классе BotKeyboard
		s.setChatId(msg.getChatId());

		s.setText(ThreadForUser.getMessageTextForUser());
		try {
			sendMessage(s);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
