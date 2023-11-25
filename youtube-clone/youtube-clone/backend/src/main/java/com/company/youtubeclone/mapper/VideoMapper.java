package com.company.youtubeclone.mapper;

import com.company.youtubeclone.dto.VideoDTO;
import com.company.youtubeclone.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    @Mapping(target = "likes", expression = "java(video.getLikes().get())")
    @Mapping(target = "disLikes", expression = "java(video.getDisLikes().get())")
    @Mapping(target = "viewCount", expression = "java(video.getViewCount().get())")
    VideoDTO videoToVideoDTO(Video video);


}
