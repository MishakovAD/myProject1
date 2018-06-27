package bot;

import java.util.ArrayList;

//Класс задумывался, как счетчик событий для каждого пользователя
//Чтобы верно выводить ему сообщения и не было путаницы в многопоточности
public class CounterEvents {
	private long userId;
	private int counter;
	public static ArrayList<String> messages = new ArrayList<>();
	
	public static void addMessages() {
		messages.add("Привет,следуйте инструкциям! Для начала выберете что это: Доход или Расход?");
		messages.add("Введите с обычной клавиатуры сумму: ");
		messages.add("Выберете: ");
		messages.add("Спасибо! Для создания новой записи, выбери \"/newCost\". Для получения статистики,выбери \"/statistic\".");
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
