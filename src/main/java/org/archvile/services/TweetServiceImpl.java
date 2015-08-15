package org.archvile.services;

import org.archvile.models.ContestTweet;
import org.archvile.repository.ContestTweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private ContestTweetRepository contestTweetRepository;

    @Override
    public List<ContestTweet> getRetweets() {
        return contestTweetRepository.findAll();
    }

    @Override
    public List<Long> getRetweetIds() {
        List<Long> retweetIds = new ArrayList<>();
        for (ContestTweet tweet : getRetweets()) {
            retweetIds.add(tweet.getTweetId());
        }
        return retweetIds;
    }

    @Override
    public ContestTweet saveTweetAsContestTweet(Tweet tweet) {
        ContestTweet contestTweet = new ContestTweet(tweet);
        contestTweetRepository.save(contestTweet);
        return contestTweet;
    }

}
