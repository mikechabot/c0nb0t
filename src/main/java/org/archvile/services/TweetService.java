package org.archvile.services;

import org.archvile.models.ContestTweet;
import org.springframework.social.twitter.api.Tweet;

import java.util.List;

public interface TweetService {

    List<ContestTweet> getRetweets();
    List<Long> getRetweetIds();
    ContestTweet saveTweetAsContestTweet(Tweet tweet);

}
