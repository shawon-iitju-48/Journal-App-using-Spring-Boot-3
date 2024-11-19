package com.springbootproject1.JournalApp.service;

import com.springbootproject1.JournalApp.entity.JournalEntry;
import com.springbootproject1.JournalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    public void saveEntry(JournalEntry entry)
    {
        journalEntryRepo.save(entry);
    }

    public List<JournalEntry>getEntries()
    {
        return journalEntryRepo.findAll();
    }

    public JournalEntry findById(ObjectId id)
    {
        return journalEntryRepo.findById(id).orElse(null);
    }

    public void deleteById(ObjectId id)
    {
        journalEntryRepo.deleteById(id);
    }
}
