package com.example.demo.controller;

import com.example.demo.exception.ValidationErrorModel;
import com.example.demo.model.Case;
import com.example.demo.model.Note;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.example.demo.controller.APIResponseMessages.VALIDATION_FAILURE_MESSAGE;

@Tag(name = "Case Resource", description = "Set of endpoints used to control Cases")
public interface CaseAPI {

    @Operation(
            description = "Get all cases with specified status",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema),
                            description = "Cases returned successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorModel.class),
                            examples = @ExampleObject(value = VALIDATION_FAILURE_MESSAGE), mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Invalid status"
                    )
            }
    )
    List<Case> getCasesWithStatus(@PathVariable Case.Status status);

    @Operation(
            description = "Get all cases for a specified user",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema),
                            description = "Cases returned successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    List<Case> getUserCases(@PathVariable Integer userId);

    @Operation(
            description = "Get all cases for a specified user",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(schema = @Schema),
                            description = "Cases returned successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorModel.class),
                            examples = @ExampleObject(value = VALIDATION_FAILURE_MESSAGE), mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Invalid status"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    List<Case> getUserCases(@PathVariable Integer userId, @PathVariable Case.Status status);

    @GetMapping("/case/{caseId}")
    Case getCase(@PathVariable Integer caseId);

    @PostMapping(value = "/case/create", consumes = "application/json")
    Case createCase(@RequestBody Case toCreate);

    @PostMapping(value = "/case/{caseId}/addNote", consumes = "text/plain")
    Note addNote(@PathVariable Integer caseId, @RequestBody String detail);
}
