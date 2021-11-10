package sg.edu.smu.cs203.pandanews.dto;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NewsDTO implements Serializable{
    private static final long serialVersionUID = 6039321808442141068L;

    private String title;

    private String description;

    private String content;

    private String source;

    private String coverImage;

    private long category;

    private boolean pinned;

    public Boolean getPinned(){
        return pinned;
    }

}
