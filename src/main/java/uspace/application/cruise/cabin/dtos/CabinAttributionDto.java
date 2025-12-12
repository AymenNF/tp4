package uspace.application.cruise.cabin.dtos;

import java.util.List;

public class CabinAttributionDto {
    public List<CabinDto> cabins;

    public CabinAttributionDto() {
    }

    public CabinAttributionDto(List<CabinDto> cabins) {
        this.cabins = cabins;
    }
}

