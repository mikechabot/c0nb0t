package org.archvile.services;

import org.archvile.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchOperations searchOperations;


    @Override
    public SearchResults getSearchResultsBySearchTerm(String searchTerm) {
        if (StringUtil.isEmpty(searchTerm)) throw new IllegalArgumentException("Search term cannot be null");
        return searchOperations.search(searchTerm, 200);
    }

    @Override
    public SearchResults getSearchResultsByHashtag(String hashtag) {
        if (StringUtil.isEmpty(hashtag)) throw new IllegalArgumentException("Hashtag cannot be null");
        if (!hashtag.startsWith("#")) hashtag = "#" + hashtag;
        return getSearchResultsBySearchTerm(hashtag);
    }

    @Override
    public List<Tweet> getTweetsBySearchTerm(String searchTerm) {
        return getSearchResultsBySearchTerm(searchTerm).getTweets();
    }

    @Override
    public List<Tweet> getTweetsByHashtag(String hashtag) {
        return getSearchResultsByHashtag(hashtag).getTweets();
    }

}
