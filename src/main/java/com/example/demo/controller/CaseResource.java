package com.example.demo.controller;

import com.example.demo.model.Case;
import com.example.demo.model.Note;
import com.example.demo.service.CaseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CaseResource implements CaseAPI {

    private CaseService caseService;

    public CaseResource(CaseService caseService) {
        this.caseService = caseService;
    }

    @Override
    @GetMapping("/cases/status/{status}")
    public List<Case> getCasesWithStatus(@PathVariable Case.Status status) {
        return caseService.getCasesByStatus(status);
    }

    @Override
    @GetMapping("/cases/user/{userId}")
    public List<Case> getUserCases(@PathVariable Integer userId) {
        return caseService.getCasesByUser(userId);
    }

    @Override
    @GetMapping("/cases/user/{userId}/status/{status}")
    public List<Case> getUserCases(@PathVariable Integer userId, @PathVariable Case.Status status) {
        return caseService.getCasesByUser(userId, status);
    }

    @Override
    @GetMapping("/case/{caseId}")
    public Case getCase(@PathVariable Integer caseId) {
        return caseService.getCaseById(caseId);
    }

    @Override
    @PostMapping(value = "/case/create", consumes = "application/json")
    public Case createCase(@RequestBody @Valid Case toCreate) {
        return caseService.createCase(toCreate);
    }

    @Override
    @PostMapping(value = "/case/{caseId}/addNote", consumes = "text/plain")
    public Note addNote(@PathVariable Integer caseId, @RequestBody String detail) {
        return caseService.createNote(caseId, detail);
    }

}