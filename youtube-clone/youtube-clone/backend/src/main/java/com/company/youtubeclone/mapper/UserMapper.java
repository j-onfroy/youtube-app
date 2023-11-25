package com.company.youtubeclone.mapper;


import com.company.youtubeclone.dto.UserInfoDTO;
import com.company.youtubeclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "firstName",source = "givenName")
    @Mapping(target = "lastName",source = "familyName")
    @Mapping(target = "fullName",source = "name")
    @Mapping(target = "emailAddress",source = "email")
    @Mapping(target = "sub",source = "sub")
    User userInfoDTOtoUser(UserInfoDTO userInfoDTO);
}
