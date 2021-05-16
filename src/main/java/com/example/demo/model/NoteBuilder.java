package com.example.demo.model;

public final class NoteBuilder {
    private Integer noteId;
    private Integer caseId;
    private String details;

    private NoteBuilder() {
    }

    public static NoteBuilder aNote() {
        return new NoteBuilder();
    }

    public NoteBuilder noteId(Integer noteId) {
        this.noteId = noteId;
        return this;
    }

    public NoteBuilder caseId(Integer caseId) {
        this.caseId = caseId;
        return this;
    }

    public NoteBuilder details(String details) {
        this.details = details;
        return this;
    }

    public Note build() {
        Note note = new Note();
        note.setNoteId(noteId);
        note.setCaseId(caseId);
        note.setDetails(details);
        return note;
    }
}
