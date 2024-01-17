package com.exercise.app.repositories;

import com.exercise.app.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT * FROM notes WHERE archived = ?1", nativeQuery = true)
    List<Note> findAllNotesFilteredByArchived(Boolean archived);

}
