package com.example.demo.service.mappers;

import com.example.demo.model.Note;
import com.example.demo.repository.entities.NoteEntity;
import org.mapstruct.Mapper;

@Mapper
public interface NoteMapper {

    Note noteEntityToNote(NoteEntity noteEntity);
}
