package edu.bbte.projectbluebook.datacatalog.users.model.mapper;

import edu.bbte.projectbluebook.datacatalog.users.model.User;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserLoginResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "role", constant = "USER")
    public abstract User requestDtoToModel(UserCreationRequest userCreationRequest);

    public abstract UserResponse modelToResponseDto(User user);

    public abstract UserLoginResponse modelToLoginResponse(User user, String token);
}
