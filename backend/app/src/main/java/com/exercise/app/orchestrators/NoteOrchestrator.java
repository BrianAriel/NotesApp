package com.exercise.app.orchestrators;

import com.exercise.app.domain.Note;
import com.exercise.app.dtos.NoteDTO;
import com.exercise.app.services.NoteSearcher;
import com.exercise.app.services.NoteUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteOrchestrator {

    @Autowired
    private NoteSearcher noteSearcher;

    @Autowired
    private NoteUpdater noteUpdater;

    public NoteDTO createNewNote(final NoteDTO noteDTO) {
        Note note = Note.builder()
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .archived(false)
                .categories(new ArrayList<>())
                .build();

        note = noteUpdater.save(note);

        return NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .archived(note.getArchived())
                .categories(note.getCategories())
                .build();
    }

    public void deleteNote(Long noteId) {
        noteUpdater.deleteOne(noteSearcher.findById(noteId));
    }

    public NoteDTO updateNote(NoteDTO noteDTO) {
        // First we find the note to update, we update it with the information in the noteDTO brought from frontend, and we save the changes. Return a new NoteDTO
        Note noteToUpdate = noteSearcher.findById(noteDTO.getId());

        noteToUpdate.setTitle(noteDTO.getTitle());
        noteToUpdate.setContent(noteDTO.getContent());

        Note noteUpdated = noteUpdater.save(noteToUpdate);

        return NoteDTO.builder()
                .id(noteUpdated.getId())
                .title(noteUpdated.getTitle())
                .content(noteUpdated.getContent())
                .archived(noteUpdated.getArchived())
                .categories(noteUpdated.getCategories())
                .build();
    }

    public void toggleArchiveNote(Long noteId) {
        //First find the note in the database, then change the archive value and save the changes
        Note noteToToggle = noteSearcher.findById(noteId);
        noteToToggle.setArchived(!noteToToggle.getArchived());
        noteUpdater.save(noteToToggle);
    }

    public List<NoteDTO> getArchivedNotes() {
        return noteSearcher.findNotesFilteredByArchived(true)
                .stream()
                .map(note ->  NoteDTO.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .archived(note.getArchived())
                        .categories(note.getCategories())
                        .build())
                .collect(Collectors.toList());
    }

    public List<NoteDTO> getActiveNotes() {
        return noteSearcher.findNotesFilteredByArchived(false)
                .stream()
                .map(note ->  NoteDTO.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .archived(note.getArchived())
                        .categories(note.getCategories())
                        .build())
                .collect(Collectors.toList());
    }

    public NoteDTO addCategory(Long noteId, String category) {
        Note note = noteSearcher.findById(noteId);
        if (!note.getCategories().contains(category))
            note.getCategories().add(category);

        Note noteToReturn = noteUpdater.save(note);
        return NoteDTO.builder()
                .id(noteToReturn.getId())
                .title(noteToReturn.getTitle())
                .content(noteToReturn.getContent())
                .archived(noteToReturn.getArchived())
                .categories(noteToReturn.getCategories())
                .build();
    }

    public void deleteCategory(Long noteId, String category) {
        Note note = noteSearcher.findById(noteId);
        note.getCategories().remove(category);

        noteUpdater.save(note);
    }

    public List<NoteDTO> getNotesFilteredByCategory(List<NoteDTO> noteDTOS, String category) {
        return noteDTOS.stream()
                .filter(note -> note.getCategories()
                        .stream()
                        .anyMatch(category::equalsIgnoreCase))
                .collect(Collectors.toList());
    }
}
