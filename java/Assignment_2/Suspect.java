import java.util.ArrayList;

public class Suspect {
    private String name;
    private String username;
    private String country;
    private String city;

    ArrayList<String> phoneList = new ArrayList<String>();

    ArrayList<Suspect> isConnected = new ArrayList<Suspect>();

    public Suspect(String name, String username, String country, String city) {
        this.name = name;
        this.username = username;
        this.country = country;
        this.city = city;
    }

    public void addNumber(String number) {
        this.phoneList.add(number);
    }

    public void addNewSupect(Suspect aSuspect) {
        if (!isConnectedTo(aSuspect)) {
            isConnected.add(aSuspect);
        } else {
            System.out.println("This parter is already existing");
        }
    }

    public boolean isConnectedTo(Suspect aSuspect) {

        for (Suspect isC : isConnected) {
            for (String phone : isC.getPhoneList()) {
                for (String phone2 : aSuspect.getPhoneList()) {
                    if (phone.equals(phone2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<String> getPhoneList() {
        return this.phoneList;
    }

    public ArrayList<Suspect> getConnectedList() {
        return this.isConnected;
    }

    public ArrayList<Suspect> getCommonPartners(Suspect aSuspect) {
        ArrayList<Suspect> results = new ArrayList<Suspect>();

        for (Suspect sp : aSuspect.getConnectedList()) {
            for (Suspect currSp : isConnected) {
                for (String phones1 : sp.getPhoneList()) {
                    for (String currPhone : currSp.getPhoneList()) {
                        if (phones1.equals(currPhone)) {
                            results.add(sp);
                        }
                    }

                }
            }
        }
        return results;

    }

    public String getCountry() {
        return this.country;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getCodeName() {
        return this.username;
    }

    public void printConnections() {
        for (Suspect sp : isConnected) {
            if (sp.getCountry().equals(this.country)) {
                System.out.println("Name: " + sp.getName() + " Username: " + sp.getUsername() + "*");

            } else {
                System.out.println("Name: " + sp.getName() + " Username: " + sp.getUsername());
            }
        }
    }

}
