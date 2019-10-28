package service;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.TweetEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Stateless
public class TweetServiceImpl implements TweetService{


    @PersistenceContext(name = "Tweet_APP_JPA")
    EntityManager entityManager;


    @Override
    public TweetEntity postTweet(TweetEntity tweet) {
    	System.out.println("***********************************************Inside Service Post Tweet");
        entityManager.persist(tweet);
		createTweet(tweet.getTweetBody());
        return tweet;
    }

    @Override
    public List<TweetEntity> retrieveTweets() {
    	System.out.println("***********************************************Inside Service Retrieve Tweets");
        return entityManager.createQuery("SELECT t FROM Tweet t", TweetEntity.class).getResultList();
    }
    
    public Status createTweet(String tweet) {
        Twitter twitter = getTwitterinstance();
        Status status;
		try {
			status = twitter.updateStatus(tweet);
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
        return status;
    }
    
       private Twitter getTwitterinstance(){    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	      .setOAuthConsumerKey("HFHlV2dNT0chcqzAck252MtJx")
	      .setOAuthConsumerSecret("64jot53psACiEDZgqdhOEXuZ0rVSgEPLcxHcn4Y6WBRcwBRZxb")
	      .setOAuthAccessToken("356294977-jGVYsh0cLxjonLyhMiYO9IPxbpFm7mAM9qxts8Go")
	      .setOAuthAccessTokenSecret("XBwrFP3iMZE2zORnG8RdH4q3upRgy56gl8nfxndWA1HXj");
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    return tf.getInstance();
    }
}
