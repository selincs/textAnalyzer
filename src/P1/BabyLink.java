package P1;

public class BabyLink {

	private final String babyLink;
	
	public BabyLink(String babyLink) {
		this.babyLink = babyLink;
	}

	public String getNextWord() {
		return babyLink;
	}

	@Override
	public String toString() {
		return " " + babyLink;
	}
}