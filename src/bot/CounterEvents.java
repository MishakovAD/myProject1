package bot;

import java.util.ArrayList;

//����� �����������, ��� ������� ������� ��� ������� ������������
//����� ����� �������� ��� ��������� � �� ���� �������� � ���������������
public class CounterEvents {
	private long userId;
	private int counter;
	public static ArrayList<String> messages = new ArrayList<>();
	
	public static void addMessages() {
		messages.add("������,�������� �����������! ��� ������ �������� ��� ���: ����� ��� ������?");
		messages.add("������� � ������� ���������� �����: ");
		messages.add("��������: ");
		messages.add("�������! ��� �������� ����� ������, ������ \"/newCost\". ��� ��������� ����������,������ \"/statistic\".");
	}
	
	
	public CounterEvents(long userId, int counter) {
		super();
		this.userId = userId;
		this.counter = counter;		
	}

	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	
}
