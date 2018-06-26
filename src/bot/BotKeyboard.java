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
			// ������� �����������
		if(CostsBot.textArray.size() == 0) {
			ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
			sendMessage.setReplyMarkup(replyKeyboardMarkup);
			replyKeyboardMarkup.setSelective(true);
			replyKeyboardMarkup.setResizeKeyboard(true);
			replyKeyboardMarkup.setOneTimeKeyboard(false);

			// ������� ������ ����� ����������
			List<KeyboardRow> keyboard = new ArrayList<>();

			// ������ ������� ����������
			KeyboardRow keyboardFirstRow = new KeyboardRow();
			// ��������� ������ � ������ ������� ����������
			keyboardFirstRow.add(new KeyboardButton("�����"));

			// ������ ������� ����������
			KeyboardRow keyboardSecondRow = new KeyboardRow();
			// ��������� ������ �� ������ ������� ����������
			keyboardSecondRow.add(new KeyboardButton("������"));

			// ��������� ��� ������� ���������� � ������
			keyboard.add(keyboardFirstRow);
			keyboard.add(keyboardSecondRow);
			// � ������������� ���� ������ ����� ����������
			replyKeyboardMarkup.setKeyboard(keyboard);
		}
		else if (CostsBot.textArray.size() == 2) {
			ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
			sendMessage.setReplyMarkup(replyKeyboardMarkup);
			replyKeyboardMarkup.setSelective(true);
			replyKeyboardMarkup.setResizeKeyboard(true);
			replyKeyboardMarkup.setOneTimeKeyboard(false);

			// ������� ������ ����� ����������
			List<KeyboardRow> keyboard = new ArrayList<>();

			// ������ ������� ����������
			KeyboardRow keyboardFirstRow = new KeyboardRow();
			// ��������� ������ � ������ ������� ����������
			keyboardFirstRow.add(new KeyboardButton("��������"));

			// ������ ������� ����������
			KeyboardRow keyboardSecondRow = new KeyboardRow();
			// ��������� ������ �� ������ ������� ����������
			keyboardSecondRow.add(new KeyboardButton("���������"));

			// ��������� ��� ������� ���������� � ������
			keyboard.add(keyboardFirstRow);
			keyboard.add(keyboardSecondRow);
			// � ������������� ���� ������ ����� ����������
			replyKeyboardMarkup.setKeyboard(keyboard);
		}
	}

}
