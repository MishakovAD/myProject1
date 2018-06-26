package bot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class CostsBot extends TelegramLongPollingBot {
	public static Long ChatId;
	public static String Name;
	public static String Costs;
	public static ArrayList<String> textMessage = new ArrayList<>();
	public static ArrayList<Update> update = new ArrayList<>();
	public static ArrayList<String> textArray = new ArrayList<String>();
	public static ArrayList<String> HDD = new ArrayList<String>();
	//Пробую сделать многопоточность. создаем хранилище для айдишников пользователей,которые пишут
	
	public int updateSizeBefore = 0;
	public int updateSizeAfter;

	public String getBotToken() {
		System.out.println("Ready! Token");
		return "597710015:AAFvYu0rDwE0vYxhaPQ6gsSgiRLXqFUACK4";
	}

	public String getBotUsername() {
		System.out.println("Ready! UserName");
		return "forCosts_bot";
	}

	public void onUpdateReceived(Update e) {
		update.add(e);
		updateSizeAfter = update.size();
		ChatId = update.get(updateSizeBefore).getMessage().getChatId();
		if (update.get(updateSizeBefore).hasMessage()) {
					SimpleThread thread = new SimpleThread(update.get(updateSizeBefore).getMessage());
					sendMsg(update.get(updateSizeBefore).getMessage(), update.get(updateSizeBefore).getMessage().getText());
					//System.out.println(ChatId);
					Thread myThready = new Thread(thread);
					myThready.start();
					updateSizeBefore = updateSizeAfter;
		} else if (e.hasCallbackQuery()) {
			System.out.println("else if");
			// AnswerCallbackThread answerThread = new
			// AnswerCallbackThread(e.getCallbackQuery());
		}
		 try {
		 DataBase.checkUser();
		 } catch (InstantiationException | IllegalAccessException | SQLException e2) {
		 e2.printStackTrace();
		 }
		// if (txt.equals("/start")) {
		// sendMsg(msg, "Привет! Это бот для учета расходов! Как тебя зовут? \n" + "
		// \n");
		// System.out.println("Ready! Msg");
		// } else if (txt.equals("/help")) {
		// sendMsg(msg, "Список команд: \n" + "1. /get - получить фильм \n" + "2. /like
		// - лайкнуть полученный фильм \n"
		// + "3. /dislike - дизлайкнуть полученный фильм \n");
		// System.out.println("Ready! Msg " + msg.getChatId());
		// }
		//
		// else if (txt.length() > 0 && DataBase.checkUser == false) {
		// Name = txt;
		// try {
		// DataBase.regNewUser();
		// } catch (InstantiationException | IllegalAccessException | SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// sendMsg(msg, txt);
		// System.out.println("Сообщение от: " + msg.getChatId() + " Содержит: " + txt);
		//
		// } else if (txt.length() > 0 && DataBase.checkUser == true) {
		// Costs = txt;
		// text_message.add(Costs + " / ");
		//// try {
		//// DataBase.addCost(Costs);
		//// } catch (InstantiationException | IllegalAccessException | SQLException e1)
		// {
		//// // TODO Auto-generated catch block
		//// e1.printStackTrace();
		//// }
		// sendMsg(msg, "Спасибо,что пользуетесь нашим сервисом!");
		// System.out.println("Сообщение от: " + msg.getChatId() + " Содержит: " + txt);
		//
		// }
	}

	public static void printText() {
		for (int i = 0; i < textMessage.size(); i++) {
			System.out.println(textMessage.get(i));
		}
	}

	@SuppressWarnings("deprecation")
	private void sendMsg(Message msg, String text) {
		String messageTextForUser = "Привет,следуйте инструкциям! Для начала выберете что это: Доход или Расход?";
		SendMessage s = new SendMessage();
		s.enableMarkdown(true);
		BotKeyboard.setButtons(s); //бращаемся к клавиатуре в классе BotKeyboard
		s.setChatId(msg.getChatId());
		
		if(textArray.size() == 1) {
			messageTextForUser = "Введите с обычной клавиатуры сумму: ";
		}
		else if (textArray.size() == 2) {
			messageTextForUser = "Выберете: ";
		}
		else if (textArray.size() == 3) {
			messageTextForUser = "Спасибо! Чтобы создать новую запись, напиши \"Привет\". ";
		}
		
		s.setText(messageTextForUser);
		try {
			sendMessage(s);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	static class SimpleThread extends Thread {
		public Message mesg;

		public SimpleThread(Message mesg) {
			super();
			this.mesg = mesg;
		}

		@Override
		public void run() {
			textArray.add(" ChatID: " + ChatId + " /" + mesg.getText());
			HDD.add(" ChatID: " + ChatId + " /" + mesg.getText());
			//System.out.println("textArray.size(): " + textArray.size());
			
//			for (int i = 0; i < textArray.size(); i++) {
//				System.out.println(textArray.get(i));
//			}
			
			//тут массив будет очищаться и данные заноситься в БД
			//Теперь нужно решить проблему: нужно создавать отдельный поток для разного юзера
			//озможно есть смысл проверять айдишники юзеров, и если они разные, то создавать новый объект, в противном случае продолжать тот же поток
			if (textArray.size() > 3) {
				System.out.println("Cleared!!!");
				textArray.clear();
				for (int i = 1; i < HDD.size(); i++) {
					if (i % 4 != 0) {
						System.out.println(HDD.get(i));
					}					
				}
				System.out.println();
			}

		}

	}

}
