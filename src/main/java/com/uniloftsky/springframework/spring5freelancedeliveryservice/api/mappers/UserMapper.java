package com.uniloftsky.springframework.spring5freelancedeliveryservice.api.mappers;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.model.UserDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

}
