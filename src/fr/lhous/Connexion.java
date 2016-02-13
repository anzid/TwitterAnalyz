package fr.lhous;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Connexion {
	
	public Twitter connect(){
		
		System.out.println("je suis dans connect");
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("uuOC68JWTrkywDYSrCdCYK6Wn");
		builder.setOAuthConsumerSecret("3FFbwA1czmGWBtcGq1Y1260GmLFYCDqaldHxrRFyu0rrIYAy7S");
		//builder.setOAuthAccessToken(new AccessToken("327291290-B0tfDr7wx2CcnHqBF8DoqCuYiBRhqagwZHTY7KuN", "K9J8z4uG20CQNr0CuOx4nTY5ql0jUG8Qr8VNSfZUKW1Q9");)
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		AccessToken accessToken = new AccessToken("327291290-B0tfDr7wx2CcnHqBF8DoqCuYiBRhqagwZHTY7KuN", "K9J8z4uG20CQNr0CuOx4nTY5ql0jUG8Qr8VNSfZUKW1Q9");
		twitter.setOAuthAccessToken(accessToken);
		
//		Twitter twitter = TwitterFactory.getSingleton();
//		twitter.setOAuthConsumer("uuOC68JWTrkywDYSrCdCYK6Wn", "3FFbwA1czmGWBtcGq1Y1260GmLFYCDqaldHxrRFyu0rrIYAy7S");
//		AccessToken accessToken = new AccessToken("327291290-B0tfDr7wx2CcnHqBF8DoqCuYiBRhqagwZHTY7KuN", "K9J8z4uG20CQNr0CuOx4nTY5ql0jUG8Qr8VNSfZUKW1Q9");
//		twitter.setOAuthAccessToken(accessToken);
		return twitter;
	}

}
