package com.springbootproject1.JournalApp.controller;

import com.springbootproject1.JournalApp.entity.JournalEntry;
import com.springbootproject1.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService entryService;

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry)
    {
        try {
            entry.setDate(LocalDateTime.now());
            entryService.saveEntry(entry);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        List<JournalEntry> all=entryService.getEntries();
        if(all!=null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id)
    {
        JournalEntry entry=entryService.findById(id);
        if(entry!=null)
            return new ResponseEntity<>(entryService.findById(id), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry entry)
    {
        JournalEntry oldEntry=entryService.findById(id);
        if(oldEntry!=null)
        {
            oldEntry.setTitle((entry.getTitle()!=null && !entry.getTitle().isEmpty())?entry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent((entry.getContent()!=null && !entry.getContent().isBlank())?entry.getContent(): oldEntry.getContent());
            entryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id)
    {
        JournalEntry entry=entryService.findById(id);
        if(entry==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        entryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
