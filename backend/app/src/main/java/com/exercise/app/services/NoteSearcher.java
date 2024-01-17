package com.exercise.app.services;

import com.exercise.app.domain.Note;
import com.exercise.app.repositories.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteSearcher {

    @Autowired
    private NoteRepository noteRepository;

    public Note findById(Long noteId) throws EntityNotFoundException {
        return noteRepository.findById(noteId).orElseThrow(() -> new EntityNotFoundException("Note entity not found with id: " + noteId));
    }

    public List<Note> findNotesFilteredByArchived(Boolean archived) {
        return noteRepository.findAllNotesFilteredByArchived(archived);
    }

}
