package kg.peaksoft.bilingualb4.api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestRequest {

    private String title;
    private String shortDescription;
    private boolean isActive;

}
