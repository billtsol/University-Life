public class SMS extends Communication {
    private String message;

    public SMS(String phoneNumber1, String phoneNumber2, int year, int month, int day, String message) {
        super(phoneNumber1, phoneNumber2, year, month, day);
        this.message = message;

    }

    @Override
    public void printInfo() {
        System.out.println("This SMS has the following info");
        super.printInfo();
        System.out.println("Text: " + this.message);
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public String getType() {
        return "sms";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
