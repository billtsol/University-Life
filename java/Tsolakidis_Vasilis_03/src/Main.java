
public class Main {

	public static void main(String[] args) {

		// Creation of suspect objects
		Suspect s1 = new Suspect("John Dow", "Sleepy Dog", "Spain", "Barcelona");
		s1.addNumber("00496955444444");
		s1.addNumber("00496955333333");

		Suspect s2 = new Suspect("Danny Rust", "Rusty Knife", "UK", "London");
		s2.addNumber("00446999888888");

		Suspect s3 = new Suspect("Bob Robson", "Frozen Bear", "Spain", "Oslo");
		s3.addNumber("00478484777777");
		s3.addNumber("00478484666666");
		s3.addNumber("00478484222222");

		// Addition for ASSIGNMENT 3
		Suspect s4 = new Suspect("John Papas", "Quick knife", "Greece", "Athens");
		s4.addNumber("0030210567888");

		// Creaton of communication objects
		Communication[] comms = new Communication[15];

		comms[0] = new PhoneCall("00496955444444", "00478484777777", 15, 10, 2022, 127);
		comms[1] = new PhoneCall("00496955444444", "00478484777777", 16, 10, 2022, 240);
		comms[2] = new PhoneCall("00446999888888", "00496955333333", 17, 10, 2022, 52);
		comms[3] = new PhoneCall("00446999888888", "00478484777777", 18, 10, 2022, 180);
		comms[4] = new PhoneCall("00478484666666", "00496955333333", 19, 10, 2022, 305);
		comms[5] = new PhoneCall("00496955444444", "00478484222222", 20, 10, 2022, 247);
		comms[6] = new PhoneCall("00478484222222", "00496955333333", 21, 10, 2022, 32);

		comms[7] = new SMS("00496955444444", "00478484777777", 10, 10, 2022, "fancy a drink tonight?");
		comms[8] = new SMS("00496955333333", "00446999888888", 11, 10, 2022, "Nitro Bomb prepared");
		comms[9] = new SMS("00446999888888", "00496955444444", 12, 10, 2022, "flying to Berlin tomorrow");
		comms[10] = new SMS("00478484777777", "00446999888888", 13, 10, 2022, "No internet connection today");
		comms[11] = new SMS("00478484777777", "00446999888888", 14, 10, 2022, "Gun Received from Rusty Knife");
		comms[12] = new SMS("00478484777777", "00446999888888", 15, 10, 2022, "Metro Attack ready");
		comms[13] = new SMS("00478484666666", "00446999888888", 16, 10, 2022, "Explosives downtown have been placed");

		// Addition for ASSIGNMENT 3
		comms[14] = new SMS("0030210567888", "00478484222222", 22, 10, 2022, "Meet you at Oslo");

		// Creation of Registry object
		Registry registry = new Registry();

		registry.addSuspect(s1);
		registry.addSuspect(s2);
		registry.addSuspect(s3);

		// Addition for ASSIGNMENT 3
		registry.addSuspect(s4);

		for (int i = 0; i < 15; i++) {
			registry.addCommunication(comms[i]);
		}

		System.out.println("Suggested Partners for " + s1.getCodeName());
		for (Suspect sp : s1.SuggestedPartners()) {
			System.out.println(sp.getCodeName());
		}
		System.out.println("---------------------------------");

		System.out.println("Suggested Partners for " + s2.getCodeName());
		for (Suspect sp : s2.SuggestedPartners()) {
			System.out.println(sp.getCodeName());
		}
		System.out.println("---------------------------------");

		System.out.println("Suggested Partners for " + s3.getCodeName());
		for (Suspect sp : s3.SuggestedPartners()) {
			System.out.println(sp.getCodeName());
		}
		System.out.println("---------------------------------");

		System.out.println("Suggested Partners for " + s4.getCodeName());
		for (Suspect sp : s4.SuggestedPartners()) {
			System.out.println(sp.getCodeName());
		}
		System.out.println("---------------------------------");

		SuspectPage suspectPage;
		// SearchPage searchPage = new SearchPage(registry,suspectPage);

	}

}