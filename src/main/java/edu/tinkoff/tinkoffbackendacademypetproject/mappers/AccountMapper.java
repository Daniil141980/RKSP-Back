package edu.tinkoff.tinkoffbackendacademypetproject.mappers;

import edu.tinkoff.tinkoffbackendacademypetproject.dto.requests.SaveInformationAboutAccountRequestDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.AccountResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.AccountUpdateRoleResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.GetAllUserResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.dto.responses.StatusAccountResponseDto;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Named("getRole")
    static String getRole(RoleEntity role) {
        return role.getName().getDescription();
    }

    @Named("getDate")
    static String getDate(LocalDate dateTime) {
        return dateTime == null ? null : dateTime.toString();
    }

    @Named("getBirthDate")
    static LocalDate getBirthDate(String s) {
        try {
            return LocalDate.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    @Mapping(target = "photoNameInDirectory", source = "profilePhoto.fileNameInDirectory")
    @Mapping(source = "role", target = "role", qualifiedByName = "getRole")
    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "getDate")
    AccountResponseDto toAccountResponseDto(Account account);

    @Mapping(source = "role", target = "role", qualifiedByName = "getRole")
    GetAllUserResponseDto toGetAllUserResponseDto(Account account);

    StatusAccountResponseDto toStatusAccountResponseDto(Account account);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "publications", ignore = true)
    @Mapping(target = "profilePhoto", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isBanned", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "getBirthDate")
    Account fromSaveInformationAboutAccountRequestDto(SaveInformationAboutAccountRequestDto saveInformationAboutAccountRequestDto);

    @Mapping(source = "role", target = "role", qualifiedByName = "getRole")
    AccountUpdateRoleResponseDto toAccountUpdateRoleResponseDto(Account account);
}
