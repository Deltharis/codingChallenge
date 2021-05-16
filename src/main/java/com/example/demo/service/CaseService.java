package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Case;
import com.example.demo.model.Note;
import com.example.demo.repository.CaseRepository;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entities.CaseEntity;
import com.example.demo.repository.entities.NoteEntity;
import com.example.demo.repository.entities.UserEntity;
import com.example.demo.service.mappers.CaseMapper;
import com.example.demo.service.mappers.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    private CaseRepository caseRepository;
    private UserRepository userRepository;
    private NoteRepository noteRepository;
    private CaseMapper caseMapper;
    private NoteMapper noteMapper;

    @Autowired
    public CaseService(CaseRepository caseRepository, UserRepository userRepository, NoteRepository noteRepository,
                       CaseMapper caseMapper, NoteMapper noteMapper) {
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.caseMapper = caseMapper;
        this.noteMapper = noteMapper;
    }

    public List<Case> getCasesByStatus(Case.Status status) {
        List<CaseEntity> entities = caseRepository.findAllByStatus(status);
        return caseMapper.caseEntitiesToCases(entities);
    }

    public List<Case> getCasesByUser(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User %s not found", userId)));
        List<CaseEntity> entities = caseRepository.findAllByUser(user);
        return caseMapper.caseEntitiesToCases(entities);
    }

    public List<Case> getCasesByUser(Integer userId, Case.Status status) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User %s not found", userId)));
        List<CaseEntity> entities = caseRepository.findAllByUserAndStatus(user, status);
        return caseMapper.caseEntitiesToCases(entities);
    }

    public Case getCaseById(Integer caseId) {
        CaseEntity caseEntity = caseRepository.findById(caseId)
                .orElseThrow(() -> new NotFoundException(String.format("Case %s not found", caseId)));
        return caseMapper.caseEntityToCase(caseEntity);
    }

    public Case createCase(Case toCreate) {
        CaseEntity entity = caseMapper.caseToCaseEntity(toCreate);
        Integer userId = entity.getUser().getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User %s not found", userId)));
        entity.setUser(user);
        entity = caseRepository.save(entity);
        return caseMapper.caseEntityToCase(entity);
    }

    public Note createNote(Integer caseId, String detail) {
        CaseEntity entity = caseRepository.findById(caseId)
                .orElseThrow(() -> new NotFoundException(String.format("Case %s not found", caseId)));
        NoteEntity noteEntity = NoteEntity.NoteEntityBuilder.aNoteEntity().caseEntity(entity).details(detail).build();
        noteEntity = noteRepository.save(noteEntity);
        return noteMapper.noteEntityToNote(noteEntity);
    }
}
