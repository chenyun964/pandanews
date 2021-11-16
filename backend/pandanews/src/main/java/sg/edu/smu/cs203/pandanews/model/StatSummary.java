package sg.edu.smu.cs203.pandanews.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import sg.edu.smu.cs203.pandanews.model.Statistic;

import lombok.*;

@Getter
@Setter
public class StatSummary {
    private Long totalCases;
    private Long totalDeath;
    private Long totalRecovery;
    private Statistic lastedRecord;
}
