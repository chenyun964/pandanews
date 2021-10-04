package sg.edu.smu.cs203.pandanews.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrganisationDTO implements Serializable{
    private static final long serialVersionUID = 6039321808442141068L;

    @NotNull
    private String title;

    @NotNull
    private String address;

    @NotNull
    private String contact;

}