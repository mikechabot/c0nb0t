package org.archvile.services;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;

import java.util.List;

public interface SearchService {

    SearchResults getSearchResultsBySearchTerm(String searchTerm);
    SearchResults getSearchResultsByHashtag(String hashtag);
    List<Tweet>  getTweetsBySearchTerm(String searchTerm);
    List<Tweet> getTweetsByHashtag(String hashtag);

}
