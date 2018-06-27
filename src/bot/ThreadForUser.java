package bot;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class ThreadForUser extends Thread {
	private Update update;
	private Message message;
	private Long chatId;
	private volatile ArrayList<String> textMessage;
	private boolean isFinish = true;

	public ThreadForUser(String name) {
		super(name);
	}
	
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Long getChatId() {
		return chatId;
	}

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}	
	


	public void run() {
		Thread.currentThread().setName("Thread-" + getChatId().toString());
		System.out.println("Start thread! " + Thread.currentThread().getName());
		textMessage = new ArrayList<String>();
		textMessage.add(getChatId().toString());
		while (isFinish) {
			if (textMessage.get(0).equals(getChatId().toString())) {
				try {
					chatId = getChatId();
					update = getUpdate();
					message = update.getMessage();
					textMessage.add(message.getText() + " / ");					
					System.out.println(textMessage);
					update = null;					
					
					//Все Тестовые классы поменять на обычные!
					if (textMessage.size() == 5) {
						// отправляем сообщение в БД (с записью ID), а так же очищаем массив
						// textMessage.add(update.getMessage().getChatId().toString());
						textMessage.add(" id = " + getChatId());
						// sql
						textMessage.removeAll(textMessage);
						CostsBotTest.chatIdThreadMap.remove(chatId);
						CostsBotTest.counterMap.remove(chatId);
						CostsBotTest.flagConsumptionMap.remove(chatId);
						CostsBotTest.flagIncomeMap.remove(chatId);
						System.out.println("Cleared!");
						isFinish = false;
					}

				} catch (IndexOutOfBoundsException e) {
					// System.out.println(e);
				} catch (NullPointerException e) {
					// System.out.println(e);
				}

			} else {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

}