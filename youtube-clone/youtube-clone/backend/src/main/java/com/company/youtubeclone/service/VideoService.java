package com.company.youtubeclone.service;

import com.company.youtubeclone.dto.CommentDTO;
import com.company.youtubeclone.dto.UploadVideoResponse;
import com.company.youtubeclone.dto.VideoDTO;
import com.company.youtubeclone.mapper.CommentMapper;
import com.company.youtubeclone.mapper.VideoMapper;
import com.company.youtubeclone.model.Comment;
import com.company.youtubeclone.model.Video;
import com.company.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;
    private final VideoMapper videoMapper;
    private final CommentMapper commentMapper;

    public UploadVideoResponse videoUpload(MultipartFile multipartFile) {
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public VideoDTO edit(VideoDTO videoDTO) {

        var savedVideo = getVideoById(videoDTO.getId());
        savedVideo.setVideoStatus(videoDTO.getVideoStatus());
        savedVideo.setTags(videoDTO.getTags());
        savedVideo.setDescription(videoDTO.getDescription());
        savedVideo.setTitle(videoDTO.getTitle());
        savedVideo.setThumbnailUrl(videoDTO.getThumbnailUrl());

        videoRepository.save(savedVideo);
        return videoDTO;

    }

    public String videoThumbnail(MultipartFile file, String videoId) {
        Video savedVideo = getVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);
        savedVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(savedVideo);
        return thumbnailUrl;
    }


    private Video getVideoById(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id " + videoId));

    }

    public VideoDTO getVideoDetails(String videoId) {
        Video savedVideo = getVideoById(videoId);
        increaseVideoCount(savedVideo);
        userService.addVideoToHistory(videoId);

        return videoMapper.videoToVideoDTO(savedVideo);
    }

    private void increaseVideoCount(Video savedVideo) {
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDTO likeVideo(String videoId) {
        Video videoById = getVideoById(videoId);
        if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDisLikeVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikeVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        } else {
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(videoById);
        return videoMapper.videoToVideoDTO(videoById);
    }

    public VideoDTO disLikeVideo(String videoId) {
        Video videoById = getVideo(videoId);
        if (userService.ifDisLikeVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikeVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        } else {
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }
        videoRepository.save(videoById);
        return videoMapper.videoToVideoDTO(videoById);
    }

    private Video getVideo(String videoId) {
        return getVideoById(videoId);
    }

    public void addComment(String videoId, CommentDTO commentDTO) {
        Video video = getVideoById(videoId);
        Comment comment = commentMapper.toComment(commentDTO);
        video.addComment(comment);
        videoRepository.save(video);

    }

    public List<CommentDTO> getAllComments(String videoId) {
        Video video = getVideoById(videoId);
        List<Comment> commentList = video.getCommentList();
        return commentList.stream().map(commentMapper::toCommentDTO).toList();
    }

    public List<VideoDTO> getAllVideos() {
        return videoRepository.findAll().stream().map(videoMapper::videoToVideoDTO).toList();
    }
}
