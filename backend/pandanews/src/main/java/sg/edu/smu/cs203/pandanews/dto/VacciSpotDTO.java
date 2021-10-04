package sg.edu.smu.cs203.pandanews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacciSpotDTO {
    private String type;
    private String name;
    private String region;
    private String address;
    private Double latitude;
    private Double longitude;
    private String vacciType;
}