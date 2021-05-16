package com.example.demo.repository.entities;

import com.example.demo.model.Case;

import javax.persistence.*;

@Entity
@Table(name = "TB_NOTE")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private Integer noteId;

    @ManyToOne()
    @JoinColumn(name = "CASE_ID")
    private CaseEntity caseEntity;

    @Column(name = "DETAILS")
    private String details;

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public CaseEntity getCaseEntity() {
        return caseEntity;
    }

    public void setCaseEntity(CaseEntity caseEntity) {
        this.caseEntity = caseEntity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public static final class NoteEntityBuilder {
        private Integer noteId;
        private CaseEntity caseEntity;
        private String details;

        private NoteEntityBuilder() {
        }

        public static NoteEntityBuilder aNoteEntity() {
            return new NoteEntityBuilder();
        }

        public NoteEntityBuilder noteId(Integer noteId) {
            this.noteId = noteId;
            return this;
        }

        public NoteEntityBuilder caseEntity(CaseEntity caseEntity) {
            this.caseEntity = caseEntity;
            return this;
        }

        public NoteEntityBuilder details(String details) {
            this.details = details;
            return this;
        }

        public NoteEntity build() {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setNoteId(noteId);
            noteEntity.setCaseEntity(caseEntity);
            noteEntity.setDetails(details);
            return noteEntity;
        }
    }
}
