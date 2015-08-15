package org.archvile.services;

import org.archvile.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendOperations friendOperations;

    @Override
    public void follow(String username) {
        if (StringUtil.isEmpty(username)) throw new IllegalArgumentException("Username cannot be null");
        friendOperations.follow(username);
    }

    @Override
    public void follow(long id) {
        if (id < 0)  throw new IllegalArgumentException("User id cannot be less than zero");
        friendOperations.follow(id);
    }

}
