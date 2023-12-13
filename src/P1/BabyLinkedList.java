package P1;

import java.util.LinkedList;

public class BabyLinkedList {
	private LinkedList<BabyLink> babyLinkedList;
	
	public BabyLinkedList() {
		if (babyLinkedList == null) {
			babyLinkedList = new LinkedList<BabyLink>();
		}
	}
	
	public LinkedList<BabyLink> getBabyLinkedList() {
		return babyLinkedList;
	}

	public void setBabyLinkedList(LinkedList<BabyLink> babyLinkedList) {
		this.babyLinkedList = babyLinkedList;
	}

	public void add(BabyLink babyLink) {
		babyLinkedList.add(babyLink);
	}
	
	public boolean isEmpty() {
		return babyLinkedList.isEmpty();
	}

	 @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < babyLinkedList.size(); i++) {
	            sb.append(babyLinkedList.get(i));
	            if (i < babyLinkedList.size() - 1) {
	                sb.append(", ");
	            }
	        }
	        return sb.toString();
	    }

}
