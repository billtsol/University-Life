public abstract class Communication {
    private String phoneNumber1;
    private String phoneNumber2;
    private int year;
    private int month;
    private int day;
 
    public Communication(String phoneNumber1, String phoneNumber2, int day, int month, int year) {
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getPhoneNumber1() {
        return this.phoneNumber1;
    }

    public String getPhoneNumber2() {
        return this.phoneNumber2;
    }

    public void printInfo() {
        System.out.println("Between " + this.phoneNumber1 + " --- " + this.phoneNumber2);
        System.out.println("on " + this.year + "/" + this.month + "/" + this.day);
    }

    // Επιστρέφει το μήνυμα στην κλάση SMS
    public abstract String getMessage();

    // Επιστρέfει την διάρκεια της κλήσης στην κλάση PhoneCall
    public abstract int getDuration();
}
