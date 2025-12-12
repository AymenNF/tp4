package uspace.api.cruise.crew;

import uspace.application.cruise.crew.dtos.CrewMemberDto;
import uspace.domain.exceptions.MissingParameterException;

public class CrewMemberDtoValidator {
    public void validate(CrewMemberDto crewMemberDto) {
        if (crewMemberDto.name == null) {
            throw new MissingParameterException("name");
        }
        if (crewMemberDto.employeeId == null) {
            throw new MissingParameterException("employeeId");
        }
    }
}
