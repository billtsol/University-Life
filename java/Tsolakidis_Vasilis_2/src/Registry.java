import java.util.ArrayList;

public class Registry {
    ArrayList<Communication> communucationList = new ArrayList<Communication>();
    ArrayList<Suspect> SuspectList = new ArrayList<Suspect>();

    public void addSuspect(Suspect aSuspect) {
        SuspectList.add(aSuspect);
    }

    public void addCommunication(Communication aCommunication) {
        /*
         * Βρίσκουμε τον έναν από τους δύο Suspects μέσω του τηλεφώνουμε 1 .
         * και τον αποθηκεύουμε. Ψάχνουμε με τον suspect με το τηλέφωνο 2. Αφού τον
         * βρούμε
         * συνδέουμε τον αποθηκεύμενο suspect με αυτόν που βρίκαμε.
         */
        communucationList.add(aCommunication);
        Suspect oneS = null;
        for (Suspect sp : SuspectList) {
            for (String phones : sp.getPhoneList()) {
                if (aCommunication.getPhoneNumber1().equals(phones)) {
                    oneS = sp;
                }
            }
        }

        for (Suspect sp : SuspectList) {
            for (String phones : sp.getPhoneList()) {
                if (aCommunication.getPhoneNumber2().equals(phones)) {
                    oneS.addNewSupect(sp);
                    sp.addNewSupect(oneS);
                }
            }
        }
    }

    public Suspect getSuspectWithMostPartners() {
        int max = SuspectList.get(0).getConnectedList().size();
        Suspect curr = SuspectList.get(0);
        for (Suspect sp : SuspectList) {
            // System.out.println(sp.getName() + " " + sp.getConnectedList().size());
            if (max <= sp.getConnectedList().size()) {
                max = sp.getConnectedList().size();
                curr = sp;
            }
        }
        return curr;
    }

    public PhoneCall getLongestPhoneCallBetween(String number1, String number2) {
        int max = -1;
        PhoneCall res = null;
        for (Communication cm : communucationList) {
            if (cm.getType().equals("call")) {
                if (cm.getPhoneNumber1().equals(number1) && cm.getPhoneNumber2().equals(number2)
                        && max < cm.getDuration()) {
                    max = cm.getDuration();
                    res = (PhoneCall) cm;
                }
            }
        }
        return res;
    }

    public ArrayList<SMS> getMessagesBetween(String number1, String number2) {
        ArrayList<SMS> res = new ArrayList<>();

        for (Communication cm : communucationList) {
            if (cm.getType().equals("sms")) {
                if (cm.getPhoneNumber1().equals(number1) && cm.getPhoneNumber2().equals(number2)) {
                    if (cm.getMessage().contains("Bomb") || cm.getMessage().contains("Attack")
                            || cm.getMessage().contains("Explosives") || cm.getMessage().contains("Gun")) {
                        res.add((SMS) cm);
                    }
                }
            }
        }
        return res;
    }

    public void printSuspectsFromCountry(String country) {
        System.out.println("Suspects coming from Spain:");
        for (Suspect sp : SuspectList) {
            if (sp.getCountry().equals(country)) {
                System.out.println(sp.getName() + " (" + sp.getCodeName() + ")");
            }
        }
    }
}
