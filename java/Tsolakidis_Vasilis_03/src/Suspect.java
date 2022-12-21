import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

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

    // Getters
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

    // Methods
    public void addNumber(String number) {
        this.phoneList.add(number);
    }

    public void addNewSupect(Suspect aSuspect) {
        if (!isConnectedTo(aSuspect)) {
            isConnected.add(aSuspect);
        }
    }

    public boolean isConnectedTo(Suspect aSuspect) {
        return this.isConnected.contains(aSuspect);
    }

    public ArrayList<Suspect> getCommonPartners(Suspect aSuspect) {
        ArrayList<Suspect> results = new ArrayList<Suspect>();

        for (Suspect suspect : aSuspect.getConnectedList()) {
            if (this.isConnected.contains(suspect)) {
                results.add(suspect);
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

    public ArrayList<Suspect> SuggestedPartners() {
        ArrayList<Suspect> suggestedPartners = new ArrayList<Suspect>();

        for (Suspect commonSuspect : this.isConnected) {
            ArrayList<Suspect> commonSuspectConnectedList = (ArrayList<Suspect>) commonSuspect.getConnectedList()
                    .clone();

            commonSuspectConnectedList.remove(this);
            for (Suspect isConnectedSuspectInCommonSuspect : commonSuspectConnectedList) {
                if (!this.isConnected.contains(isConnectedSuspectInCommonSuspect)) {
                    suggestedPartners.add(isConnectedSuspectInCommonSuspect);
                }
            }
        }

        // Set solution
        // Set<Suspect> data = new LinkedHashSet<Suspect>();
        // for (Suspect commonSuspect : this.isConnected) {
        // for (Suspect isConnectedSuspectInCommonSuspect :
        // commonSuspect.getConnectedList()) {
        // if (!this.isConnected.contains(isConnectedSuspectInCommonSuspect)) {
        // data.add(isConnectedSuspectInCommonSuspect);
        // }
        // }
        // }
        // data.remove(this);

        return suggestedPartners;
    }

}
