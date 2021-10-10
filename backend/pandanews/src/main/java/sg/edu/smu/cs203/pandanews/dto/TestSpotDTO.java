package sg.edu.smu.cs203.pandanews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestSpotDTO {
    private String type;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String opHours;
    private String contact;
}