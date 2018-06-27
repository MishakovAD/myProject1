package bot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

public class BotKeyboardTest {
	
	public static boolean getIncome() {
		Boolean flag = null;
				for (ConcurrentHashMap.Entry objCounter : CostsBotTest.flagIncomeMap.entrySet()) {
					if (CostsBotTest.chatId.equals(objCounter.getKey())) {
						flag = (Boolean) objCounter.getValue();
						return flag;
					}
					else {
						flag = false;
					}
				}
				System.out.println(flag);
				return flag;
	}
	
	public static boolean getConsumption() {
		Boolean flag = null;
		for (ConcurrentHashMap.Entry objCounter : CostsBotTest.flagConsumptionMap.entrySet()) {
			if (CostsBotTest.chatId.equals(objCounter.getKey())) {
				flag = (Boolean) objCounter.getValue(); 
			}
			else {
				flag = false;
			}
		}
		System.out.println(flag);
		return flag;
		
	}
	
	public static synchronized void setButtons(SendMessage sendMessage) {
		for (ConcurrentHashMap.Entry objCounter : CostsBotTest.counterMap.entrySet()) {
			if (CostsBotTest.chatId.equals(objCounter.getKey())) {
				Integer count = (Integer) objCounter.getValue();
				if (count == 0) {
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
				} else if (count == 2 && getIncome()) {
					ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
					sendMessage.setReplyMarkup(replyKeyboardMarkup);
					replyKeyboardMarkup.setSelective(true);
					replyKeyboardMarkup.setResizeKeyboard(true);
					replyKeyboardMarkup.setOneTimeKeyboard(false);
					List<KeyboardRow> keyboard = new ArrayList<>();
					KeyboardRow keyboardFirstRow = new KeyboardRow();
					keyboardFirstRow.add(new KeyboardButton("Зарплата"));
					KeyboardRow keyboardSecondRow = new KeyboardRow();
					keyboardSecondRow.add(new KeyboardButton("Мелкие"));
					keyboard.add(keyboardFirstRow);
					keyboard.add(keyboardSecondRow);
					replyKeyboardMarkup.setKeyboard(keyboard);
				} else if (count == 2 && getConsumption()) {
					ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
					sendMessage.setReplyMarkup(replyKeyboardMarkup);
					replyKeyboardMarkup.setSelective(true);
					replyKeyboardMarkup.setResizeKeyboard(true);
					replyKeyboardMarkup.setOneTimeKeyboard(false);
					List<KeyboardRow> keyboard = new ArrayList<>();
					KeyboardRow keyboardFirstRow = new KeyboardRow();
					keyboardFirstRow.add(new KeyboardButton("Магнит"));
					KeyboardRow keyboardSecondRow = new KeyboardRow();
					keyboardSecondRow.add(new KeyboardButton("Пятерочка"));
					keyboard.add(keyboardFirstRow);
					keyboard.add(keyboardSecondRow);
					replyKeyboardMarkup.setKeyboard(keyboard);
				} else if (count == 3) {
					ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
					sendMessage.setReplyMarkup(replyKeyboardMarkup);
					replyKeyboardMarkup.setSelective(true);
					replyKeyboardMarkup.setResizeKeyboard(true);
					replyKeyboardMarkup.setOneTimeKeyboard(false);
					List<KeyboardRow> keyboard = new ArrayList<>();
					KeyboardRow keyboardFirstRow = new KeyboardRow();
					keyboardFirstRow.add(new KeyboardButton("Стоп"));
					KeyboardRow keyboardSecondRow = new KeyboardRow();
					keyboardSecondRow.add(new KeyboardButton("Stop"));
					keyboard.add(keyboardFirstRow);
					keyboard.add(keyboardSecondRow);
					replyKeyboardMarkup.setKeyboard(keyboard);
				}
			}

		}
	}

}
