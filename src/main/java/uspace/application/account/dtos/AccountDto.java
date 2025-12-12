package uspace.application.account.dtos;

import java.util.List;

public class AccountDto {
    public String username;
    public String homePlanetName;
    public List<String> bookingIds;

    public AccountDto() {
    }

    public AccountDto(String username,String homePlanetName, List<String> bookingIds) {
        this.username = username;
        this.homePlanetName = homePlanetName;
        this.bookingIds = bookingIds;
    }
}
