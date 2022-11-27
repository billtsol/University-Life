public class PhoneCall extends Communication {
    private int duration;

    public PhoneCall(String phoneNumber1, String phoneNumber2, int year, int month, int day, int duration) {
        super(phoneNumber1, phoneNumber2, year, month, day);
        this.duration = duration;
    }

    @Override
    public void printInfo() {
        System.out.println("This phone call has the following info");
        super.printInfo();
        System.out.println("Duration: " + this.duration);
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public String getType() {
        return "call";
    }
}
