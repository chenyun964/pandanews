package sg.edu.smu.cs203.pandanews.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacciSpotDTO implements Serializable {
    private static final long serialVersionUID = 6039321808442141068L;

    @NotBlank
    private String type;

    @NotBlank
    private String name;

    @NotBlank
    private String region;

    @NotBlank
    private String address;

    @NotBlank
    private String vacciType;
}