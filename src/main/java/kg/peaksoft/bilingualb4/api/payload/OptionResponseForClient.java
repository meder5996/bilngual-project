package kg.peaksoft.bilingualb4.api.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class OptionResponseForClient {
    private Long id;
    private String name;
    private String file;
}
