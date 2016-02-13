package fr.lhous;

import java.util.Comparator;
import java.util.Map;

import twitter4j.User;

public class FollowersComparator implements Comparator<Long>{
	private Map<Long, User> users;
	
	public FollowersComparator(Map<Long, User> mapUsers){
        this.users = mapUsers; //stocker la copie pour qu'elle soit accessible dans compare()
    }
	
	public int compare(Long id1, Long id2){
		User u1 = users.get(id1);
		User u2 = users.get(id2);
		return u1.getFollowersCount() - u2.getFollowersCount();
	}

}