package edu.bbte.projectbluebook.datacatalog.users.model.mapper;

import edu.bbte.projectbluebook.datacatalog.users.model.ApiKey;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring", imports = {OffsetDateTime.class})
public abstract class ApiKeyMapper {
    @Mapping(target = "createdAt", expression = "java(OffsetDateTime.now())")
    public abstract ApiKey requestDtoToModel(ApiKeyCreationRequest apiKeyCreationRequest);

    public abstract ApiKeyCreationResponse modelToCreationResponseDto(ApiKey apiKey);

    public abstract  ApiKeyResponse modelToResponseDto(ApiKey apiKey);
}
