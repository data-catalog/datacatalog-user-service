package edu.bbte.projectbluebook.datacatalog.users.model.mapper;

import edu.bbte.projectbluebook.datacatalog.users.model.User;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserLoginResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "role", constant = "USER")
    public abstract User requestDtoToModel(UserCreationRequest userCreationRequest);

    public abstract UserResponse modelToResponseDto(User user);

    public abstract UserLoginResponse modelToLoginResponse(User user, String token);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User updateModelFromDto(@MappingTarget User asset, UserUpdateRequest userUpdateRequest);
}
