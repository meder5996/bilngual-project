package kg.peaksoft.bilingualb4.api.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRequest {
    private String name;
    private String file;
    private boolean isCorrect;
}
