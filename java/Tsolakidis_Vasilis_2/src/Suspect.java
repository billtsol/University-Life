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

    public String getCountry() {
        return this.country;
    }

    public String getName() {
        return this.name;
    }

    public String getCodeName() {
        return this.username;
    }

    public ArrayList<String> getPhoneList() {
        return this.phoneList;
    }

    public ArrayList<Suspect> getConnectedList() {
        return this.isConnected;
    }

    public void addNumber(String number) {
        this.phoneList.add(number);
    }

    public void addNewSupect(Suspect aSuspect) {
        if (!isConnectedTo(aSuspect)) {
            isConnected.add(aSuspect);
        }
    }

    public boolean isConnectedTo(Suspect aSuspect) {
        // return this.isConnected.contains(aSuspect);

        for (Suspect suspect : this.isConnected) {
            for (String phone : suspect.getPhoneList()) {
                if (aSuspect.getPhoneList().contains(phone)) {
                    return true;
                }
            }
        }
        return false;

    }

    public ArrayList<Suspect> getCommonPartners(Suspect aSuspect) {
        ArrayList<Suspect> results = new ArrayList<Suspect>();
        /*
         * Διρατρέχω την λίστα με τους συνδεδεμένους Suspects, του suspect που ήρθε σαν
         * όρισμα.
         * Διατρέχω την λίστα με τους συνδεδεμένους Suspects, του τρέχων suspect.
         * Για κάθε ένα τηλέφωνο των συνδεδεμένων Suspects του suspect που ήρθε ελέγχω
         * εαν είναι μέσα στα τηλέφωνα των συνδεδεμένων Suspects του τρέχων suspect.
         */
        for (Suspect suspect : aSuspect.getConnectedList()) {
            for (Suspect currSp : this.isConnected) {
                // if (suspect.getCodeName().equals(currSp.getCodeName())) {
                // results.add(suspect);
                // }
                // Μπορεί να γίνει και ελέγχοντας τα Code Name. Μοναδικά για κάθε Suspect
                for (String phone1 : suspect.getPhoneList()) {
                    if (currSp.getPhoneList().contains(phone1)) {
                        results.add(suspect);
                    }
                }

            }
        }
        return results;
    }

    public void printConnections() {
        for (Suspect sp : isConnected) {
            if (sp.getCountry().equals(this.country)) {
                System.out.println("Name: " + sp.getName() + " Username: " + sp.getCodeName() + "*");

            } else {
                System.out.println("Name: " + sp.getName() + " Username: " + sp.getCodeName());
            }
        }
    }

}
