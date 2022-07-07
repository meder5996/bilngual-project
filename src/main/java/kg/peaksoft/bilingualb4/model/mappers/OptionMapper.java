package kg.peaksoft.bilingualb4.model.mappers;

import kg.peaksoft.bilingualb4.api.payload.OptionRequest;
import kg.peaksoft.bilingualb4.api.payload.OptionResponse;
import kg.peaksoft.bilingualb4.api.payload.OptionResponseForClient;
import kg.peaksoft.bilingualb4.model.entity.Options;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OptionMapper {

    public Options mapToEntity(OptionRequest optionRequest){
        return Options.builder()
                //.id(id)
                .optionName(optionRequest.getName())
                .file(optionRequest.getFile())
                .isCorrect(optionRequest.isCorrect())
                .build();
    }

    public List<Options> mapToEntity(List<OptionRequest> optionRequests){
        List<Options> optionsList = new ArrayList<>();
        for (OptionRequest optionRequest: optionRequests){
            optionsList.add(mapToEntity(optionRequest));
        }
        return optionsList;
    }

    public OptionResponse mapToResponse(Options options){
        return OptionResponse.builder()
                .id(options.getId())
                .name(options.getOptionName())
                .file(options.getFile())
                .isCorrect(options.isCorrect())
                .build();
    }

    public List<OptionResponse> mapToResponse(List<Options> optionsList){
        List<OptionResponse> optionResponseList = new ArrayList<>();
        for (Options options: optionsList){
            optionResponseList.add(mapToResponse(options));
        }
        return optionResponseList;
    }

    public OptionResponseForClient mapToResponseForClient(Options options){
        return OptionResponseForClient.builder()
                .id(options.getId())
                .name(options.getOptionName())
                .file(options.getFile())
                .build();
    }

    public List<OptionResponseForClient> mapToResponseForClient(List<Options> optionsList){
        List<OptionResponseForClient> optionResponseList1 = new ArrayList<>();
        for (Options options: optionsList){
            optionResponseList1.add(mapToResponseForClient(options));
        }
        return optionResponseList1;
    }
}
