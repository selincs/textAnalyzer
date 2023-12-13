package P1;

import java.util.HashMap;
import java.util.LinkedList;

public class MainLinkedList {
	private LinkedList<MainLink> mainLinkedList;
	private HashMap<String, MainLink> mainLinksMap;

	public MainLinkedList() {
		this.mainLinkedList = new LinkedList<MainLink>();
		this.mainLinksMap = new HashMap<String, MainLink>();
	}

	public boolean isEmpty() {
		return mainLinkedList.isEmpty();
	}

	public void add(MainLink newLink) {
		String keyword = newLink.getKeyword().toLowerCase();
		MainLink existingLink = mainLinksMap.get(keyword);

		if (mainLinkedList.isEmpty()) {
			mainLinkedList.add(newLink);
			mainLinksMap.put(keyword, newLink);
			return;
		} else if (existingLink != null && newLink.getFollowingWord() != null) {
			existingLink.getFollowingWords().add(new BabyLink(newLink.getFollowingWord()));
        } else {
            mainLinkedList.add(newLink);
            mainLinksMap.put(keyword, newLink);
        }
	}

	public HashMap<String, MainLink> getMainLinksMap() {
		return mainLinksMap;
	}

	public LinkedList<MainLink> getMainLinkedList() {
		return mainLinkedList;
	}

	public void setMainLinkedList(LinkedList<MainLink> mainLinkedList) {
		this.mainLinkedList = mainLinkedList;
	}

	@Override
	public String toString() {
		return "MainLinkedList : {" + mainLinkedList + "}";
	}
}