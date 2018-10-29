package calendar;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;



public class CalendarModel {

	private LocalDate today;
	private int monthLength;
	int year;

	public CalendarModel() {
		today = LocalDate.now();
		monthLength = today.getMonth().maxLength();
		year = today.getYear();

	}

	public int getStartingPos() {
		// gets the starting column position of the first day of the current month
		LocalDate day = today.with(TemporalAdjusters.firstDayOfMonth());

		int startingPos = day.getDayOfWeek().getValue();

		switch (startingPos) {
		// sun
		case 7:
			startingPos = 0;
			break;
		// mon
		case 1:
			startingPos = 1;
			break;
		// tue
		case 2:
			startingPos = 2;
			break;
		case 3:
			startingPos = 3;
			break;
		case 4:
			startingPos = 4;
			break;
		case 5:
			startingPos = 5;
			break;
		case 6:
			startingPos = 6;
			break;
		}
		return startingPos;
	}

	public int[] getDecade() {
		int year = getToday().getYear();
		int[] decade = new int[10];

		int decadeMin = year - year % 10;
		for (int i = 0; i < decade.length; i++) {

			decade[i] = decadeMin;

			decadeMin++;

		}

		return decade;
	}

	public void rollForwardMonth() {
		today = today.plusMonths(1);
		monthLength = today.getMonth().maxLength();
		checkIfLeapYear();

	}

	public void rollBackMonth() {
		today = today.minusMonths(1);
		monthLength = today.getMonth().maxLength();
		checkIfLeapYear();
	}

	public void rollForwardYear() {
		today = today.plusYears(1);
		monthLength = today.getMonth().maxLength();
		checkIfLeapYear();

	}

	public void rollBackYear() {
		today = today.minusYears(1);
		monthLength = today.getMonth().maxLength();
		checkIfLeapYear();

	}

	public void checkIfLeapYear() {
		if (today.getMonth() == Month.FEBRUARY && !today.isLeapYear()) {
			monthLength = 28;
		}

	}

	public int getMonthLength() {

		return monthLength;
	}

	public LocalDate getToday() {
		return this.today;
	}

	public String getMonth() {
		String monthString = today.getMonth().toString();

		return monthString;
	}

}
