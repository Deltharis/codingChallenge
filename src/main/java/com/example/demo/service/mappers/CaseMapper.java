package com.example.demo.service.mappers;

import com.example.demo.model.Case;
import com.example.demo.repository.entities.CaseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CaseMapper {

    Case caseEntityToCase(CaseEntity caseEntity);

    CaseEntity caseToCaseEntity(Case caseModel);

    List<Case> caseEntitiesToCases(List<CaseEntity> entities);
}
