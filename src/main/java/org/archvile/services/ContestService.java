package org.archvile.services;

import org.springframework.social.twitter.api.Tweet;

import java.util.Set;

public interface ContestService {

    Set<Tweet> getPotentialContests();

}
