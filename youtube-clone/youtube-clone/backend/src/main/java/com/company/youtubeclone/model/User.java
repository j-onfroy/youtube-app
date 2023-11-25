package com.company.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private String sub;
    private Set<String> subscribers= ConcurrentHashMap.newKeySet();
    private Set<String> subscribedToUsers=ConcurrentHashMap.newKeySet();
    private Set<String> videoHistory = ConcurrentHashMap.newKeySet();
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> disLikeVideos = ConcurrentHashMap.newKeySet();

    public void addToLikeVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void removeFromLikeVideos(String videoId) {
        likedVideos.remove(videoId);
    }

    public void removeFromDisLikeVideos(String videoId) {
        disLikeVideos.remove(videoId);
    }

    public void addToDisLikeVideos(String videoId) {
        disLikeVideos.add(videoId);
    }

    public void addToVideoHistory(String videoId) {
        videoHistory.add(videoId);
    }

    public void addToSubscribedToUser(String userId) {
        subscribedToUsers.add(userId);
    }

    public void addToSubscribers(String userId) {
        subscribers.add(userId);
    }

    public void removeToSubscribedToUser(String userId) {
        subscribedToUsers.remove(userId);
    }

    public void removeToSubscribers(String userId) {
        subscribers.remove(userId);
    }
}
