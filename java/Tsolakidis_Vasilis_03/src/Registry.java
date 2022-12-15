import java.util.ArrayList;

public class Registry {
    ArrayList<Communication> communucationList = new ArrayList<Communication>();
    ArrayList<Suspect> SuspectList = new ArrayList<Suspect>();

    public void addSuspect(Suspect aSuspect) {
        SuspectList.add(aSuspect);
    }

    public ArrayList<Suspect> getSuspectList() {
        return this.SuspectList;
    }

    public void addCommunication(Communication aCommunication) {
        /*
         * Βρίσκουμε τον έναν από τους δύο Suspects μέσω του τηλεφώνου 1 .
         * και τον αποθηκεύουμε. Ψάχνουμε με τον suspect με το τηλέφωνο 2. Αφού τον
         * βρούμε
         * συνδέουμε τον αποθηκεύμενο suspect με αυτόν που βρίκαμε.
         */
        communucationList.add(aCommunication);

        Suspect oneS = null;
        for (Suspect sp : SuspectList) {
            for (String phone : sp.getPhoneList()) {
                if (aCommunication.getPhoneNumber1().equals(phone)) {
                    oneS = sp;
                }
            }
        }

        for (Suspect sp : SuspectList) {
            for (String phone : sp.getPhoneList()) {
                if (aCommunication.getPhoneNumber2().equals(phone)) {
                    oneS.addNewSupect(sp);
                    sp.addNewSupect(oneS);
                }
            }
        }
    }

    public Suspect getSuspectWithMostPartners() {
        /*
         * Αρχικοποιώ στην μεταβλητή max με το μεγεθος του Connection List που εχει.
         * Διατρέχω τον κάθε Suspect και ελέγχω το μέγεθος της Connection list του.
         */
        int max = SuspectList.get(0).getConnectedList().size();
        Suspect curr = SuspectList.get(0);
        for (Suspect sp : SuspectList) {
            if (max <= sp.getConnectedList().size()) {
                max = sp.getConnectedList().size();
                curr = sp;
            }
        }
        return curr;
    }

    public PhoneCall getLongestPhoneCallBetween(String number1, String number2) {
        int max = -1;
        PhoneCall results = null;
        for (Communication cm : communucationList) {
            if (cm.getClass() == PhoneCall.class) { // Ελεγχω εαν ειναι PhoneCall Class
                if (cm.getPhoneNumber1().equals(number1) && cm.getPhoneNumber2().equals(number2)
                        && max < cm.getDuration()) {
                    max = cm.getDuration();
                    results = (PhoneCall) cm;
                }
            }
        }
        return results;
    }

    public ArrayList<SMS> getMessagesBetween(String number1, String number2) {
        ArrayList<SMS> results = new ArrayList<SMS>();
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("Bomb");
        messages.add("Explosives");
        messages.add("Attack");
        messages.add("Gun");

        for (Communication cm : communucationList) {
            if (cm.getClass() == SMS.class) {// Ελεγχω εαν ειναι SMS Class
                if (cm.getPhoneNumber1().equals(number1) && cm.getPhoneNumber2().equals(number2)
                        || cm.getPhoneNumber1().equals(number2) && cm.getPhoneNumber2().equals(number1)) {
                    for (String m : messages) {
                        if (cm.getMessage().contains(m)) {
                            results.add((SMS) cm);
                            break;
                        }
                    }
                }
            }
        }
        return results;
    }

    public void printSuspectsFromCountry(String country) {
        System.out.println("Suspects coming from " + country + ": ");
        // stin preogoymeni askisi ypeirxe ayto to println ->
        // System.out.println("Suspects coming from Spain: ");
        // apisteyto fail....... oups :)
        for (Suspect sp : SuspectList) {
            if (sp.getCountry().equals(country)) {
                System.out.println(sp.getName() + " (" + sp.getCodeName() + ")");
            }
        }
    }
}
