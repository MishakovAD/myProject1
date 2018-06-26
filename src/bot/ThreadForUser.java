package bot;

import java.util.ArrayList;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import bot.CostsBotTest.UpdateId;

public class ThreadForUser extends Thread {
//	private String nameThread;
//	private UpdateId updateId;
//	private Update update;
	private Message message;
	private Long chatId;
	private volatile ArrayList<String> textMessage;
	private int counter = 0;
	private static String messageTextForUser = "Привет,следуйте инструкциям! Для начала выберете что это: Доход или Расход?";
	private boolean isFinish = true;
	private int index;
	

	public ThreadForUser(String name, int index) {
		super(name);
		this.index = index;
	}

	public static String getMessageTextForUser() {
		return messageTextForUser;
	}

	public int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		index = index;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public void run() {
		//Long id = CostsBotTest.chatIdThreadMap.get(chatId).chatId; //id пользователя, верно получаем
		Thread.currentThread().setName("Thread-" + getChatId().toString());
		System.out.println("Start thread! " + Thread.currentThread().getName());
		textMessage = new ArrayList<String>();
		while (isFinish) {
			try {
					getIndex();
					getChatId();
					message = CostsBotTest.updateIdArray.get(index-1).getEvent().getMessage();
					textMessage.add(message.getText() + " / ");
					counter += 1;
					System.out.println(textMessage);
					CostsBotTest.updateIdArray.remove(index-1);

					if (counter == 1) {
						messageTextForUser = "Введите с обычной клавиатуры сумму: ";
					} else if (counter == 2) {
						messageTextForUser = "Выберете: ";
					} else if (counter == 3) {
						messageTextForUser = "Спасибо! Чтобы создать новую запись, напиши \"Привет\". ";
					}

					if (textMessage.size() > 3) {
						// отправляем сообщение в БД (с записью ID), а так же очищаем массив
						//textMessage.add(update.getMessage().getChatId().toString());
						textMessage.add(" id = " + getChatId());
						// sql
						textMessage.removeAll(textMessage);
						CostsBotTest.setId.remove(chatId);
						CostsBotTest.chatIdThreadMap.remove(chatId);
						counter = 0;
						System.out.println("Cleared!");
						isFinish = false;
					}
					
			} catch (IndexOutOfBoundsException e) {
				//System.out.println(e);
			}
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

	}

}