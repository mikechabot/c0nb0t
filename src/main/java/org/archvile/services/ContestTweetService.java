package org.archvile.services;

import org.archvile.models.ContestTweet;
import org.springframework.social.twitter.api.Tweet;

import java.util.List;
import java.util.Set;

public interface ContestTweetService {

    List<ContestTweet> getRetweets();
    List<Long> getRetweetIds();
    ContestTweet saveTweet(Tweet tweet);
    Set<Tweet> getPotentialContestTweets();

}
