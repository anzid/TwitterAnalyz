package fr.lhous;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Main {

	public static void main(String[] args) throws TwitterException {
		// TODO Auto-generated method stub
		TwitterStats t = new TwitterStats(); 
		TwitterStats twitterStats = new TwitterStats();
		Twitter twitter = twitterStats.Connect();	
		//		int numberOftweetsAndRetweets[][][] = new int[2][7][24]; 
		//		numberOftweetsAndRetweets = twitterStats.NumberOfTweetsAndRetweets("BFMTV", twitter);
		//		for(int i=0; i<7; i++){
		//			System.out.println(i + "***" +twitterStats.NumberOfTweetInDay("BFMTV", twitter, numberOftweetsAndRetweets, i));
		//		}	
		//		
		//		for(int i=0; i<24; i++){
		//			System.out.println(i + "***" + numberOftweetsAndRetweets[0][3][i]);
		//		}	
//		System.out.println("*******************************************");
//		List<Status> tweets = twitter.search(new Query("BFMTV")).getTweets();
//		for(Status tweet : tweets){
//			System.out.println(tweet.getCreatedAt());
//		}
//		System.out.println("*******************************************");
//		System.out.println(twitter.search(new Query("BFMTV")).getTweets());
		
		int numberOftweetsAndRetweets[][][] = new int[2][7][24]; 
		List<Status> listOfTweets = twitterStats.getTweets("cnn",twitter);
		numberOftweetsAndRetweets = twitterStats.NumberOfTweetsAndRetweets(listOfTweets, twitter);
		System.out.println(listOfTweets);
		System.out.println("********************************************");
	}


}
