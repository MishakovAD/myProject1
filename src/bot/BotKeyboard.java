package bot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

public class BotKeyboard {

	public static boolean getIncome() {
		Boolean flag = false;
		for (ConcurrentHashMap.Entry objCounter : CostsBot.flagIncomeMap.entrySet()) {
			if (CostsBot.chatId.equals(objCounter.getKey())) {
				flag = (Boolean) objCounter.getValue();
				return flag;
			} else {
				flag = false;
			}
		}
		System.out.println(flag);
		return flag;
	}

	public static boolean getConsumption() {
		Boolean flag = false;
		for (ConcurrentHashMap.Entry objCounter : CostsBot.flagConsumptionMap.entrySet()) {
			if (CostsBot.chatId.equals(objCounter.getKey())) {
				flag = (Boolean) objCounter.getValue();
			} else {
				flag = false;
			}
		}
		System.out.println(flag);
		return flag;

	}

	public static synchronized void setButtons(SendMessage sendMessage) {
		for (ConcurrentHashMap.Entry objCounter : CostsBot.counterMap.entrySet()) {
			if (CostsBot.chatId.equals(objCounter.getKey())) {
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
				} else if (count == 1) {
					InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
					List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
					List<InlineKeyboardButton> rowInline = new ArrayList<>();
					rowInline.add(new InlineKeyboardButton().setText("Это").setCallbackData("test"));
					List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
					rowInline.add(new InlineKeyboardButton().setText("Тест").setCallbackData("test"));
					List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
					rowInline.add(new InlineKeyboardButton().setText("Inline клавиатуры").setCallbackData("test"));
					// Set the keyboard to the markup
					rowsInline.add(rowInline);
					rowsInline.add(rowInline2);
					rowsInline.add(rowInline3);
					// Add it to the message
					markupInline.setKeyboard(rowsInline);
					sendMessage.setReplyMarkup(markupInline);
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
					keyboardSecondRow.add(new KeyboardButton("Репетиторство"));
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
					keyboardFirstRow.add(new KeyboardButton("Ашан"));
					KeyboardRow keyboardSecondRow = new KeyboardRow();
					keyboardSecondRow.add(new KeyboardButton("Пятерочка"));
					KeyboardRow keyboardTherdRow = new KeyboardRow();
					keyboardTherdRow.add(new KeyboardButton("Перекресток"));
					KeyboardRow keyboardFourRow = new KeyboardRow();
					keyboardFourRow.add(new KeyboardButton("Магнит"));
					keyboard.add(keyboardFirstRow);
					keyboard.add(keyboardSecondRow);
					keyboard.add(keyboardTherdRow);
					keyboard.add(keyboardFourRow);
					replyKeyboardMarkup.setKeyboard(keyboard);
				} else if (count == 3) {
					ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
					sendMessage.setReplyMarkup(replyKeyboardMarkup);
					replyKeyboardMarkup.setSelective(true);
					replyKeyboardMarkup.setResizeKeyboard(true);
					replyKeyboardMarkup.setOneTimeKeyboard(false);
					List<KeyboardRow> keyboard = new ArrayList<>();
					KeyboardRow keyboardFirstRow = new KeyboardRow();
					keyboardFirstRow.add(new KeyboardButton("/statistic"));
					KeyboardRow keyboardSecondRow = new KeyboardRow();
					keyboardSecondRow.add(new KeyboardButton("/newCost").setText("Новая запись"));
					keyboard.add(keyboardFirstRow);
					keyboard.add(keyboardSecondRow);
					replyKeyboardMarkup.setKeyboard(keyboard);
				}
			}

		}
	}

}