package fr.lhous;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

	public  int numberOfTweetsInDay(String twAccountName, Twitter twitter, String day) throws TwitterException, ParseException{
		String query = "from:" + twAccountName;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(day));
		c.add(Calendar.DATE, 1);  // number of days to add
		String followingDay = sdf.format(c.getTime());  // dt is now the new date
		List<Status> tweets = twitter.search(new Query(query).since(day).until(followingDay)).getTweets();
		return tweets.size();
	} 

	@SuppressWarnings("deprecation")
	public int[] NumberOfTweetInHour(List<Status> tweets){
		int[] numberOfTweetInHour = {0,0,0,0,0,0,0,0,0,0,0,0};
		for(Status tweet : tweets){
			if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 1, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,0,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 2, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,1,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 3, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,2,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 4, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,3,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 5, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,4,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 6, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,5,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 7, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,6,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 8, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,7,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 9, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,8,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 10, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,9,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 11, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,10,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
			else if(tweet.getCreatedAt().before(new Date(2016, 2, 11, 12, 0)) && tweet.getCreatedAt().after(new Date(2016,2,11,11,0))) {
				numberOfTweetInHour[0] = numberOfTweetInHour[0] + 1;
			}
		}
		return numberOfTweetInHour;
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
