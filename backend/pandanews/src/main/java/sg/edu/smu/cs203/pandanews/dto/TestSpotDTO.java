package sg.edu.smu.cs203.pandanews.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestSpotDTO {
    @NotBlank
    private String type;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String opHours;

    @NotBlank
    private String contact;
}