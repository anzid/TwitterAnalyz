package fr.lhous;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class TwitterStats {

	public Twitter Connect(){
		Connexion c = new Connexion();
		return c.connect();
	}

	public  List<User> getFollowers(String twAccountName, Twitter twitter) throws TwitterException{
		PagableResponseList<User> followersList;
		List<User> sortedFollower = new ArrayList<>();
		Map<Long,User> mapUsers = new HashMap<>();
		int countPages = 0;
		long cursor = -1;
		do {
			followersList = twitter.friendsFollowers().getFollowersList( twAccountName, cursor, 200);
			for (User user : followersList) {
				mapUsers.put(user.getId(), user);
			}
		} while ((cursor = followersList.getNextCursor()) != 0 && ++countPages < 2); 
		System.out.println(followersList);
		List<Long> cles = new ArrayList<Long>(mapUsers.keySet());
		Collections.sort(cles, new FollowersComparator(mapUsers) );
		for(Long id : cles){
			sortedFollower.add(mapUsers.get(id));
		}
		return sortedFollower;
	}

	public List<Status> listOfTweets(String twAccountName, Twitter twitter) throws TwitterException{
		String query = "from:" + twAccountName;
		return twitter.search(new Query(query)).getTweets();
	}

	public int[][][]  NumberOfTweetsAndRetweets(String twAccountName, Twitter twitter) throws TwitterException{
		String query = "from:" + twAccountName;
		List<Status> tweets = twitter.search(new Query(query)).getTweets();
		int numberOftweetsAndRetweets[][][] = new int[7][24][2]; 
		GregorianCalendar calendar =new GregorianCalendar();
		for(Status tweet : tweets){
			calendar.setTime( tweet.getCreatedAt());
			numberOftweetsAndRetweets[calendar.get(Calendar.DAY_OF_WEEK)][calendar.get(Calendar.HOUR_OF_DAY)][0]++;
			numberOftweetsAndRetweets[calendar.get(Calendar.DAY_OF_WEEK)][calendar.get(Calendar.HOUR_OF_DAY)][1]+= tweet.getRetweetCount();
		}
		return numberOftweetsAndRetweets;
	}

	public  List<Status> listOfTweetsInDay(String twAccountName, Twitter twitter, String day) throws TwitterException, ParseException{
		String query = "from:" + twAccountName;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(day));
		c.add(Calendar.DATE, 1);  // number of days to add
		String followingDay = sdf.format(c.getTime());  // dt is now the new date
		List<Status> tweets = twitter.search(new Query(query).since(day).until(followingDay)).getTweets();
		return tweets;
	} 

	
	public List<Status> MostRetweetedTweet(String twAccountName, Twitter twitter) throws TwitterException{
		String query = "from:" + twAccountName;
		List<Status> tweets = twitter.search(new Query("from:BFMTV").resultType(Query.ResultType.popular)).getTweets();
		for(Status tweet : tweets){
			System.out.println("***************************************************");
			System.out.println(tweet.getText());
			System.out.println("***************************************************");
		}
		return tweets;
	}


	public float NumberOfRetweetInHour(List<Status> tweets){
		int result = 0;
		for(Status tweet : tweets){
			result = result + tweet.getRetweetCount();		
		}
		result = result / tweets.size();
		return result;
	}

	public float NumberOfRetweetInDay(List<Status> tweets){
		int result = 0;
		for(Status tweet : tweets){
			result = result + tweet.getRetweetCount();		
		}
		result = result / tweets.size();
		return result;
	}

}
