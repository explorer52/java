package ua.nmu.smahin.spring.services;

import org.springframework.stereotype.Service;
import ua.nmu.smahin.spring.models.ItemModel;
import ua.nmu.smahin.spring.repositories.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RepositoryService {
    private final ItemRepository repository;

    public RepositoryService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<ItemModel> get() {
        return repository.findAll();
    }

    public Optional<ItemModel> getById(Long id) {
        return repository.findById(id);
    }

    public void add(ItemModel model) {
        Optional<ItemModel> item = repository.findByName(model.getName());

        if (!item.isPresent()) {
            repository.save(model);
        }
    }
}
