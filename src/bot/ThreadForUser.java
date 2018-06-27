package bot;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class ThreadForUser extends Thread {
	private Update update;
	private Message message;
	private Long chatId;
	private volatile ArrayList<String> textMessage;
	private static String messageTextForUser;
	private boolean isFinish = true;
	private Integer counter;

	public ThreadForUser(String name) {
		super(name);
	}

	public static String getMessageTextForUser() {
		return messageTextForUser;
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
	
	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public Integer getCounter() {
		return counter;
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
					
//					if (getCounter() == 1) {
//						messageTextForUser = "Привет,следуйте инструкциям! Для начала выберете что это: Доход или Расход?";
//					} else if (getCounter() == 2) {
//						messageTextForUser = "Введите с обычной клавиатуры сумму: ";
//					} else if (getCounter() == 3) {
//						messageTextForUser = "Выберете: ";
//					} else if (getCounter() == 4) {
//						messageTextForUser = "Спасибо! Чтобы создать новую запись, напиши \"Привет\". ";
//					}
					
					textMessage.add(message.getText() + " / ");					
					System.out.println(textMessage);
					System.out.println("counter = " + getCounter());
					update = null;					

					if (textMessage.size() == 5) {
						// отправляем сообщение в БД (с записью ID), а так же очищаем массив
						// textMessage.add(update.getMessage().getChatId().toString());
						textMessage.add(" id = " + getChatId());
						// sql
						textMessage.removeAll(textMessage);
						CostsBotTest.chatIdThreadMap.remove(chatId);
						CostsBotTest.counterMap.remove(chatId);
						counter = 0;
						System.out.println("Cleared!");
						messageTextForUser = "Привет,следуйте инструкциям! Для начала выберете что это: Доход или Расход?";
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