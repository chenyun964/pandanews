package sg.edu.smu.cs203.pandanews.dto;

import java.io.Serializable;

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
 
    private Long id;
    private String type;
    private String name;
    private String region;
    private String address;
    private Double latitude;
    private Double longitude;
    private String vacciType;
}