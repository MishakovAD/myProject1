package bot;

import java.sql.SQLException;
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

	@Override
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
					//Проверка на запятую. Т.к она не должна там появлятся
					if (message.getText().contains(",")) {
						textMessage.add(message.getText().replaceAll(",", "."));
					} else if(message.getText().contains(" ")) {
						textMessage.add(message.getText().replaceAll(" ", "."));
					} else {
						textMessage.add(message.getText());
					}					
					System.out.println(textMessage);
					update = null;

					// Все Тестовые классы поменять на обычные!
					if (textMessage.size() == 5) {
						// sql
						try {
							DataBase.addCost(chatId, textMessage.get(2),
									textMessage.get(3),
									textMessage.get(4));
						} catch (NumberFormatException | InstantiationException
								| IllegalAccessException | SQLException e) {
							e.printStackTrace();
						}
						// sql
						textMessage.removeAll(textMessage);
						// ТЕСТОВЫЕ МЕНЯЕМ НА ОБЫЧНЫЕ И НАОБОРОТ
						CostsBot.chatIdThreadMap.remove(chatId);
						CostsBot.counterMap.remove(chatId);
						CostsBot.flagConsumptionMap.remove(chatId);
						CostsBot.flagIncomeMap.remove(chatId);
						System.out.println("Cleared!");
						isFinish = false;
					}

				} catch (IndexOutOfBoundsException | NullPointerException e) {
					// System.out.println(e);
				}

			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

}