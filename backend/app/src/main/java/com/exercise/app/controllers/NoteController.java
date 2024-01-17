package com.exercise.app.controllers;

import com.exercise.app.dtos.NoteDTO;
import com.exercise.app.orchestrators.NoteOrchestrator;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {

    @Autowired
    private NoteOrchestrator noteOrchestrator;

    @PostMapping
    public ResponseEntity<NoteDTO> createNewNote(@RequestBody NoteDTO noteDTO) {
        NoteDTO createdNoteDTO = noteOrchestrator.createNewNote(noteDTO);
        return new ResponseEntity<>(createdNoteDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") Long noteId) {
        noteOrchestrator.deleteNote(noteId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO) {
        NoteDTO updatedNoteDTO = noteOrchestrator.updateNote(noteDTO);
        return new ResponseEntity<>(updatedNoteDTO, HttpStatus.OK);
    }

    //For now, I will return nothing, this might change in the future.
    @PutMapping("/toggleArchive/{id}")
    public ResponseEntity<Void> toggleArchiveNote(@PathVariable("id") Long noteId) {
        noteOrchestrator.toggleArchiveNote(noteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getNotes(
            @RequestParam(value = "archived", required = false) Boolean archived,
            @RequestParam(value = "category", required = false) String category
    ) {
        List<NoteDTO> noteDTOS;

        if (archived != null && archived) {
            noteDTOS = noteOrchestrator.getArchivedNotes();
        } else {
            noteDTOS = noteOrchestrator.getActiveNotes();
        }

        //By marking the required param false we ensure that, at least, the active notes are retrieved. If we want them filtered by category we would do it without querying the database
        if(category != null && !category.isEmpty()) {
            noteDTOS = noteOrchestrator.getNotesFilteredByCategory(noteDTOS, category);
        }

        return new ResponseEntity<>(noteDTOS, HttpStatus.OK);
    }

    /*
    As spring automatically deserialize strings from a json to a string enclosed in quotes I realized that creating an entire class for just 1 string was not worthy
    So instead I use the JsonNode and just use the plain text with asText();
    This way, both add and remove categories methods work
    */


    //This could be POST or PUT. I chose POST because, in a way, we are creating a new category for the note
    @PostMapping("/addCategory/{id}")
    public ResponseEntity<NoteDTO> addCategories(@PathVariable("id") Long noteId, @RequestBody JsonNode jsonNode) {
        return new ResponseEntity<>(noteOrchestrator.addCategory(noteId, jsonNode.asText()), HttpStatus.CREATED);
    }

    //This could be DELETE or PUT. I chose DELETE because, in a way, we are deleting a category in the note
    @DeleteMapping("/removeCategory/{id}")
    public ResponseEntity<Void> deleteCategories(@PathVariable("id") Long noteId, @RequestBody JsonNode jsonNode) {
        noteOrchestrator.deleteCategory(noteId, jsonNode.asText());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
