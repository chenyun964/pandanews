package sg.edu.smu.cs203.pandanews.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO implements Serializable{
    private static final long serialVersionUID = 6039321808442141068L;

    @NotNull
    private String username;

    @NotNull
    @Email(message = "Email is invalid!")
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;
}
