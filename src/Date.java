public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    @Override
    public int compareTo(Date other) {
        int yearComparison = Integer.compare(year, other.getYear());
        if (yearComparison != 0) {
            return yearComparison;
        }

        int monthComparison = Integer.compare(month, other.getMonth());
        if (monthComparison != 0) {
            return monthComparison;
        }

        return Integer.compare(day, other.getDay());
    }
    public String toString() {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
