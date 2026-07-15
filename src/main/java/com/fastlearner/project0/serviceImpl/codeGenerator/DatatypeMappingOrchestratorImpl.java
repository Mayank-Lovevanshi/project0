package com.fastlearner.project0.serviceImpl.codeGenerator;

import com.fastlearner.project0.enums.Datatype;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.codeGenerator.DatatypeMapping;
import com.fastlearner.project0.service.codeGenerator.DatatypeMappingOrchestrator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DatatypeMappingOrchestratorImpl implements DatatypeMappingOrchestrator {

    private final Map<Language, DatatypeMapping> mappers;

    public DatatypeMappingOrchestratorImpl(List<DatatypeMapping> mapperList) {
       mappers = mapperList.stream().collect(Collectors.toMap(DatatypeMapping::getLanguage, datatype->datatype));
    }

    public String mapType(Datatype datatype, Language language) {
        return getMapper(language).mapType(datatype);
    }

    public String getInputCode(Datatype datatype, Language language) {
        return getMapper(language).getInputCode(datatype);
    }

    private DatatypeMapping getMapper(Language language) {
        DatatypeMapping mapper = mappers.get(language);
        if (mapper == null) {
            throw new UnsupportedOperationException(
                    "Datatype mapping architecture for language '" + language + "' is not supported yet."
            );
        }
        return mapper;
    }
}
