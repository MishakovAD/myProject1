package bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

public class BotKeyboard {
	public static synchronized void setButtons(SendMessage sendMessage) {
		//System.out.println("Start setButtons");
			// Создаем клавиуатуру
		if(CostsBot.textArray.size() == 0) {
			ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
			sendMessage.setReplyMarkup(replyKeyboardMarkup);
			replyKeyboardMarkup.setSelective(true);
			replyKeyboardMarkup.setResizeKeyboard(true);
			replyKeyboardMarkup.setOneTimeKeyboard(false);

			// Создаем список строк клавиатуры
			List<KeyboardRow> keyboard = new ArrayList<>();

			// Первая строчка клавиатуры
			KeyboardRow keyboardFirstRow = new KeyboardRow();
			// Добавляем кнопки в первую строчку клавиатуры
			keyboardFirstRow.add(new KeyboardButton("Доход"));

			// Вторая строчка клавиатуры
			KeyboardRow keyboardSecondRow = new KeyboardRow();
			// Добавляем кнопки во вторую строчку клавиатуры
			keyboardSecondRow.add(new KeyboardButton("Расход"));

			// Добавляем все строчки клавиатуры в список
			keyboard.add(keyboardFirstRow);
			keyboard.add(keyboardSecondRow);
			// и устанваливаем этот список нашей клавиатуре
			replyKeyboardMarkup.setKeyboard(keyboard);
		}
		else if (CostsBot.textArray.size() == 2) {
			ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
			sendMessage.setReplyMarkup(replyKeyboardMarkup);
			replyKeyboardMarkup.setSelective(true);
			replyKeyboardMarkup.setResizeKeyboard(true);
			replyKeyboardMarkup.setOneTimeKeyboard(false);

			// Создаем список строк клавиатуры
			List<KeyboardRow> keyboard = new ArrayList<>();

			// Первая строчка клавиатуры
			KeyboardRow keyboardFirstRow = new KeyboardRow();
			// Добавляем кнопки в первую строчку клавиатуры
			keyboardFirstRow.add(new KeyboardButton("Зарплата"));

			// Вторая строчка клавиатуры
			KeyboardRow keyboardSecondRow = new KeyboardRow();
			// Добавляем кнопки во вторую строчку клавиатуры
			keyboardSecondRow.add(new KeyboardButton("Пятерочка"));

			// Добавляем все строчки клавиатуры в список
			keyboard.add(keyboardFirstRow);
			keyboard.add(keyboardSecondRow);
			// и устанваливаем этот список нашей клавиатуре
			replyKeyboardMarkup.setKeyboard(keyboard);
		}
	}

}
