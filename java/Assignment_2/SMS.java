public class SMS extends Communication {
    private String message;

    public SMS(String phone1, String phone2, int year, int month, int day, String message) {
        super(phone1, phone2, year, month, day);
        this.message = message;

    }

    public String getType() {
        return "sms";
    }

    public String getMessage() {
        return message;
    }
}
