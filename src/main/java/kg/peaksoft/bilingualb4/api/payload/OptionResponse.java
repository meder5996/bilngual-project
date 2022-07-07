package kg.peaksoft.bilingualb4.api.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OptionResponse {
    private Long id;
    private String name;
    private String file;
    private boolean isCorrect;
}
