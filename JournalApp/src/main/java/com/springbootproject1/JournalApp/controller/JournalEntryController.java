package com.springbootproject1.JournalApp.controller;

import com.springbootproject1.JournalApp.entity.JournalEntry;
import com.springbootproject1.JournalApp.repository.JournalEntryRepo;
import com.springbootproject1.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService entryService;

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry entry)
    {
        entry.setDate(LocalDateTime.now());
        entryService.saveEntry(entry);
        return true;
    }

    @GetMapping
    public List<JournalEntry> getAll()
    {
        return entryService.getEntries();
    }
    @GetMapping("id/{id}")
    public List<JournalEntry> getById(@PathVariable ObjectId id)
    {
        return Collections.singletonList(entryService.findById(id));
    }

    @PutMapping("id/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry entry)
    {
        JournalEntry oldEntry=entryService.findById(id);
        if(oldEntry!=null)
        {
            oldEntry.setTitle((entry.getTitle()!=null && !entry.getTitle().isEmpty())?entry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent((entry.getContent()!=null && !entry.getContent().isBlank())?entry.getContent(): oldEntry.getContent());
        }
        entryService.saveEntry(oldEntry);
        return oldEntry;
    }

    @DeleteMapping("id/{id}")
    public boolean deleteEntry(@PathVariable ObjectId id)
    {
        entryService.deleteById(id);
        return true;
    }
}
