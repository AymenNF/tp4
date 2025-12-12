package uspace.application.cruise.crew.dtos;

public class CrewMemberDto {
    public String employeeId;

    public String name;

    public CrewMemberDto() {
    }

    public CrewMemberDto(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }
}
