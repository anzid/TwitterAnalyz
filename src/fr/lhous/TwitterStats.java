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
import twitter4j.Paging;
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

	//Return top 10 followers
	public  List<User> getTopTenFollowers(String twAccountName, Twitter twitter) throws TwitterException{
		PagableResponseList<User> followersList;
		List<User> sortedFollower = new ArrayList<>();
		Map<Long,User> mapUsers = new HashMap<>();
		int countPages = 0;
		long cursor = -1;
		do {
			followersList = twitter.friendsFollowers().getFollowersList( twAccountName, cursor, 20);
			for (User user : followersList) {
				mapUsers.put(user.getId(), user);
			}
		} while ((cursor = followersList.getNextCursor()) != 0 && ++countPages < 10); 
		System.out.println("followers recuper�s");
		List<Long> cles = new ArrayList<Long>(mapUsers.keySet());
		Collections.sort(cles, new FollowersComparator(mapUsers) );
		for(Long id : cles){
			sortedFollower.add(mapUsers.get(id));
		}
		return sortedFollower.subList(0, 10);
	}

	// return list of tweets
	public List<Status> getTweets(String twAccountName, Twitter twitter) throws TwitterException{
		int pageno = 1;
		String user = twAccountName;
		List<Status> tweets = new ArrayList();
		while (true) {
			try {
				int size = tweets.size(); 
				Paging page = new Paging(pageno++, 10);
				tweets.addAll(twitter.getUserTimeline(user, page));
				if (tweets.size() == size)
					break;
			}
			catch(TwitterException e) {

				e.printStackTrace();
			}
		}
		System.out.println("tweets recuper�s");
		return tweets;
	}

	// ranks tweets by date and time
	public int[][][]  NumberOfTweetsAndRetweets(List<Status> tweets, Twitter twitter) throws TwitterException{
		int numberOftweetsAndRetweets[][][] = new int[2][8][24]; 
		GregorianCalendar calendar =new GregorianCalendar();

		for(Status tweet : tweets){
			calendar.setTime( tweet.getCreatedAt());
			numberOftweetsAndRetweets[0][calendar.get(Calendar.DAY_OF_WEEK)][calendar.get(Calendar.HOUR_OF_DAY)]++;
			numberOftweetsAndRetweets[1][calendar.get(Calendar.DAY_OF_WEEK)][calendar.get(Calendar.HOUR_OF_DAY)]+= tweet.getRetweetCount();
		}
		System.out.println("tweets class�s");
		return numberOftweetsAndRetweets;
	}

	// return the number of tweets in day
	public int NumberOfTweetInDay(String twAccountName, Twitter twitter, int[][][] numberOfTweetsAndRetweets, int day ){
		int NumberOfTweetInDay = 0;
		for(int i=0; i< 24; i++){
			NumberOfTweetInDay += numberOfTweetsAndRetweets[0][day][i];
		}
		return NumberOfTweetInDay;
	}

	// return a list of tweets in day
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

	// return a list of the most retweeted tweets
	public List<Status> MostRetweetedTweet(String twAccountName, Twitter twitter) throws TwitterException{
		String query = "from:" + twAccountName;
		List<Status> tweets = twitter.search(new Query("from:cnn").resultType(Query.ResultType.popular)).getTweets();
		System.out.println("mostRetweetedTweets recuper�s");
		return tweets;
	}

	//return the number of tweets in hour
	public int NumberOfRetweetInDay(String twAccountName, Twitter twitter, int[][][] numberOfTweetsAndRetweets, int day ){
		int NumberOfRetweetInDay = 0;
		for(int i=0; i< 24; i++){
			NumberOfRetweetInDay += numberOfTweetsAndRetweets[1][day][i];
		}
		return NumberOfRetweetInDay;
	}

}
