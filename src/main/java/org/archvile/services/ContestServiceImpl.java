package org.archvile.services;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private SearchService searchService;

    @Autowired
    private StaticDataService staticDataService;

    @Autowired
    private TweetService tweetService;

    @Override
    public Set<Tweet> getPotentialContests() {
        Set<Tweet> potentialContests = new HashSet<>();
        List<Long> retweetedContestIds = tweetService.getRetweetIds();
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
