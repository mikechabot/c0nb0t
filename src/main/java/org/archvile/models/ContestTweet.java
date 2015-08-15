package org.archvile.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.Date;

@Document
public class ContestTweet extends MongoDocument {

    private long tweetId;
    private String text;
    private Date createdAt;
    private String fromUser;
    private Long toUserId;
    private Long inReplyToStatusId;
    private Long inReplyToUserId;
    private String inReplyToScreenName;
    private long fromUserId;
    private String languageCode;
    private String source;
    private TwitterProfile user;
    private Integer retweetCount;
    private boolean retweeted;

    private boolean favorited;
    private Integer favoriteCount;

    public ContestTweet() { }

    public ContestTweet(Tweet tweet) {
        this.tweetId = tweet.getId();
        this.text = tweet.getText();
        this.createdAt = tweet.getCreatedAt();
        this.fromUser = tweet.getFromUser();
        this.toUserId = tweet.getToUserId();
        this.inReplyToUserId = tweet.getInReplyToUserId();
        this.inReplyToStatusId = tweet.getInReplyToStatusId();
        this.inReplyToScreenName = tweet.getInReplyToScreenName();
        this.fromUserId = tweet.getFromUserId();
        this.languageCode = tweet.getLanguageCode();
        this.source = tweet.getSource();
        this.user = tweet.getUser();
        this.retweetCount = tweet.getRetweetCount();
        this.retweeted = tweet.isRetweeted();
        this.favorited = tweet.isFavorited();
        this.favoriteCount = tweet.getFavoriteCount();
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public TwitterProfile getUser() {
        return user;
    }

    public void setUser(TwitterProfile user) {
        this.user = user;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}
