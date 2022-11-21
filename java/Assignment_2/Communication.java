
public class Communication {
    private String phone1;
    private String phone2;
    private int year;
    private int month;
    private int day;

    public Communication(String phone1, String phone2, int year, int month, int day) {
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void printInfo() {
        System.out.println("Phone1 : " + this.phone1);
        System.out.println("Phone2 : " + this.phone2);
        System.out.println(this.day + " / " + this.month + " / " + this.year);
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getType() {
        return "";
    }

    public String getMessage() {
        return "";
    }

    public int getDuration() {
        return -1;
    }
}
