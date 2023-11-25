package com.company.youtubeclone.controller;

import com.company.youtubeclone.dto.CommentDTO;
import com.company.youtubeclone.dto.UploadVideoResponse;
import com.company.youtubeclone.dto.VideoDTO;
import com.company.youtubeclone.model.Comment;
import com.company.youtubeclone.service.UserService;
import com.company.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        return videoService.videoUpload(file);
    }


    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
        return videoService.videoThumbnail(file, videoId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO editVideoMetadata(@RequestBody VideoDTO videoDTO) {
        return videoService.edit(videoDTO);
    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO getVideoDetails(@PathVariable String videoId){
        return videoService.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO likeVideo(@PathVariable String videoId){
        return videoService.likeVideo(videoId);
    }


    @PostMapping("/{videoId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public VideoDTO disLikeVideo(@PathVariable String videoId){
        return videoService.disLikeVideo(videoId);
    }


    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public void addCommit(@PathVariable String videoId,@RequestBody CommentDTO commentDTO){
        videoService.addComment(videoId,commentDTO);
    }

    @GetMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getAllComments(@PathVariable String videoId){
        return videoService.getAllComments(videoId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDTO> getAllVideos(){
        return videoService.getAllVideos();
    }


    @GetMapping("/{userId}/history")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> userHistory(@PathVariable String userId){
        return userService.userHistory(userId);
    }

}
