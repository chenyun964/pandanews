package sg.edu.smu.cs203.pandanews.model.GeoCode;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoCodeListDAO {
    private ArrayList<GeoCodeDAO> data;
}
