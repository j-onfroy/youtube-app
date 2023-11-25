package com.company.youtubeclone.mapper;

import com.company.youtubeclone.dto.CommentDTO;
import com.company.youtubeclone.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "text",source = "commentText")
    Comment toComment(CommentDTO commentDTO);
    @Mapping(target = "commentText",source = "text")
    CommentDTO toCommentDTO(Comment comment);
}
