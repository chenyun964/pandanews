package sg.edu.smu.cs203.pandanews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sg.edu.smu.cs203.pandanews.model.news.News;


@Getter
@Setter
@AllArgsConstructor
public class NewsDTO {
    private News news;
    private Long id;
}
