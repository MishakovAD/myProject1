package bot;

// ласс задумывалс€, как счетчик событий дл€ каждого пользовател€
//„тобы верно выводить ему сообщени€ и не было путаницы в многопоточности
public class CounterEvents {
	private long userId;
	private int counter;
	
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
