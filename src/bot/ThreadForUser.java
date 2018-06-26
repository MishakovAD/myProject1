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
	private ArrayList<String> textMessage;
	private int counter = 0;
	private static String messageTextForUser = "������,�������� �����������! ��� ������ �������� ��� ���: ����� ��� ������?";
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
		System.out.println(getChatId());
		Thread.currentThread().setName("Thread-" + getChatId().toString());
		System.out.println("Start thread! " + Thread.currentThread().getName());
		textMessage = new ArrayList<String>();
		while (isFinish) {
			try {
					getIndex();
					getChatId();
					//System.out.println("getChatId(): " + getChatId());

					message = CostsBotTest.updateIdArray.get(index-1).getEvent().getMessage();
					textMessage.add(message.getText() + " / ");
					counter += 1;
					System.out.println(textMessage);
					CostsBotTest.updateIdArray.remove(index-1);

					if (counter == 1) {
						messageTextForUser = "������� � ������� ���������� �����: ";
					} else if (counter == 2) {
						messageTextForUser = "��������: ";
					} else if (counter == 3) {
						messageTextForUser = "�������! ����� ������� ����� ������, ������ \"������\". ";
					}

					if (textMessage.size() > 3) {
						// ���������� ��������� � �� (� ������� ID), � ��� �� ������� ������
						//textMessage.add(update.getMessage().getChatId().toString());
						textMessage.add(" id = " + chatId);
						// sql
						textMessage.removeAll(textMessage);
						CostsBotTest.setId.remove(chatId);
						counter = 0;
						System.out.println("Cleared!");
						// isFinish = false;
					}
					
			} catch (IndexOutOfBoundsException e) {
				//System.out.println(e);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}