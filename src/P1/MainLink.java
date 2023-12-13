package P1;

import java.util.Objects;

public class MainLink {
	private final String keyword;
	private String followingWord;
	private BabyLinkedList followingWords;

	public MainLink(String keyword, String followingWord) {
		this.keyword = keyword;
		this.followingWords = new BabyLinkedList();
		followingWords.add(new BabyLink(followingWord));
	}

	public MainLink(String lastWordInLine) {
		this.keyword = lastWordInLine;
		this.followingWords = new BabyLinkedList();
	}

	@Override
	public String toString() {
		return "[Keyword: \"" + keyword + "\", Following Words: " + followingWords.toString() + "]";
	}

	public BabyLinkedList getFollowingWords() {
		return followingWords;
	}

	public void setFollowingWords(BabyLinkedList followingWords) {
		this.followingWords = followingWords;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getFollowingWord() {
		return followingWord;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MainLink newLink = (MainLink) obj;
		return newLink.getKeyword().equalsIgnoreCase(this.getKeyword());
	}

	@Override
	public int hashCode() {
		return Objects.hash(keyword);
	}
}