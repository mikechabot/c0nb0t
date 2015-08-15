package org.archvile.repository;

import org.archvile.models.ContestTweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContestTweetRepository extends MongoRepository<ContestTweet, String> {

}
