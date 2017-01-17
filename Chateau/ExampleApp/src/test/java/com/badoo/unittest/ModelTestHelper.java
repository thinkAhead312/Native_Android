package com.badoo.unittest;

import android.support.annotation.NonNull;

import com.badoo.chateau.example.data.model.ExampleConversation;
import com.badoo.chateau.example.data.model.ExampleUser;
import com.badoo.chateau.example.data.util.ParseUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelTestHelper {

    public static List<ExampleUser> createUsers(int count) {
        final List<ExampleUser> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            users.add(new ExampleUser(Integer.toString(i), "User" + i));
        }
        return users;
    }

    public static List<ParseUser> createParseUsers(int count) {
        List<ParseUser> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            users.add(createParseUser(Integer.toString(i)));
        }
        return users;
    }

    public static ParseUser createParseUser(String id) {
        ParseUser user = mock(ParseUser.class);
        when(user.getUsername()).thenReturn(id);
        when(user.getObjectId()).thenReturn(id);
        when(user.getString(ParseUtils.UsersTable.Fields.DISPLAY_NAME)).thenReturn(id);
        return user;
    }

    public static List<ExampleConversation> createConversations(int count) {
        final List<ExampleConversation> conversations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            conversations.add(new ExampleConversation(Integer.toString(i)
                , "Convo" + i, Collections.emptyList(), null, 0));
        }
        return conversations;
    }

    public static List<ParseObject> createSubscriptions(int count) {
        final List<ParseObject> subscriptions = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            subscriptions.add(createSubscription(Integer.toString(i)));
        }
        return subscriptions;
    }

    public static ParseObject createSubscription(@NonNull String id) {
        final ParseObject subscription = mock(ParseObject.class);
        when(subscription.getObjectId()).thenReturn(id);
        final ParseObject chat = mock(ParseObject.class);
        when(subscription.getParseObject(ParseUtils.ChatSubscriptionTable.Fields.CHAT)).thenReturn(chat);
        when(chat.isDataAvailable()).thenReturn(false);

        return subscription;
    }
}
