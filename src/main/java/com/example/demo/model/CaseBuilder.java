package com.example.demo.model;

import java.util.List;

public final class CaseBuilder {
    private Integer caseId;
    private String title;
    private String description;
    private Integer severity;
    private Case.Status status;
    private User user;
    private List<Note> notes;

    private CaseBuilder() {
    }

    public static CaseBuilder aCase() {
        return new CaseBuilder();
    }

    public CaseBuilder caseId(Integer caseId) {
        this.caseId = caseId;
        return this;
    }

    public CaseBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CaseBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CaseBuilder severity(Integer severity) {
        this.severity = severity;
        return this;
    }

    public CaseBuilder status(Case.Status status) {
        this.status = status;
        return this;
    }

    public CaseBuilder user(User user) {
        this.user = user;
        return this;
    }

    public CaseBuilder notes(List<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Case build() {
        Case caseModel = new Case();
        caseModel.setCaseId(caseId);
        caseModel.setTitle(title);
        caseModel.setDescription(description);
        caseModel.setSeverity(severity);
        caseModel.setStatus(status);
        caseModel.setUser(user);
        caseModel.setNotes(notes);
        return caseModel;
    }
}
