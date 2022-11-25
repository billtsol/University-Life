
public class PhoneCall extends Communication {
    private int duration;

    public PhoneCall(String phone1, String phone2, int year, int month, int day, int duration) {
        super(phone1, phone2, year, month, day);
        this.duration = duration;

    }

    public int getDuration() {
        return this.duration;
    }

    public String getType() {
        return "call";
    }

    
    public void printInfo() {
        System.out.println("This phone call has the following info");
        super.printInfo();
        System.out.println("Duration: "+this.duration);
    }
}
