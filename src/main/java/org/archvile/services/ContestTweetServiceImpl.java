package org.archvile.services;

import org.archvile.models.ContestTweet;
import org.archvile.repository.ContestTweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContestTweetServiceImpl implements ContestTweetService {

    @Autowired
    private ContestTweetRepository contestTweetRepository;

    @Autowired
    private StaticDataService staticDataService;

    @Autowired
    private SearchService searchService;

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
    public ContestTweet saveTweet(Tweet tweet) {
        ContestTweet contestTweet = new ContestTweet(tweet);
        contestTweetRepository.save(contestTweet);
        return contestTweet;
    }

    @Override
    public Set<Tweet> getPotentialContestTweets() {
        Set<Tweet> potentialContests = new HashSet<>();
        List<Long> retweetedContestIds = getRetweetIds();
        for (String searchTerm : staticDataService.getSearchTermsAndHashTags()) {
            for (Tweet tweet : searchService.getTweetsBySearchTerm(searchTerm)) {
                if (tweet.isRetweet()) {
                    Tweet retweet = tweet.getRetweetedStatus();
                    if (!retweetedContestIds.contains(Long.valueOf(retweet.getId()))) {
                        potentialContests.add(retweet);
                    }
                }
            }
        }
        return potentialContests;
    }

}
