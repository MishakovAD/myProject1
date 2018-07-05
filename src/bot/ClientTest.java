package bot;

import java.util.ArrayList;

import org.telegram.telegrambots.api.objects.Update;

public class ClientTest extends Thread {
	private ArrayList<Update> updateArray;
	private ArrayList<String> textMessageArray;
	private Long cliendId;
	private Update clientUpdate;
	private String nameThread;
	
	public ArrayList<String> getTextMessageArray() {
		return textMessageArray;
	}

	public void setTextMessageArray(ArrayList<String> textMessageArray) {
		this.textMessageArray = textMessageArray;
	}

	public Long getCliendId() {
		return cliendId;
	}

	public void setCliendId(Long cliendId) {
		this.cliendId = cliendId;
	}

	public Update getClientUpdate() {
		return clientUpdate;
	}

	public void setClientUpdate(Update clientUpdate) {
		this.clientUpdate = clientUpdate;
	}

	public String getNameThread() {
		return nameThread;
	}

	public void setNameThread(String nameThread) {
		this.nameThread = nameThread;
	}


	public ClientTest(Long cliendId, Update clientUpdate, ArrayList<Update> updateArray, String nameThread) {
		super();
		this.cliendId = cliendId;
		this.clientUpdate = clientUpdate;
		this.updateArray = updateArray;
		this.nameThread = nameThread;
	}

	@Override
	public void run() {
		final Long id = cliendId;
		while (true) {
			textMessageArray.add(clientUpdate.getMessage().getText() + "/");
		}
	}

}
