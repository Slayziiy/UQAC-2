package ca.uqac.aquarium.service;

import ca.uqac.aquarium.model.Fish;
import ca.uqac.aquarium.model.User;
import ca.uqac.aquarium.repository.FishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FishService {

    private final FishRepository repository;

    public FishService(FishRepository repository) {
        this.repository = repository;
    }

    public List<Fish> all(){
        return repository.findAll();
    }

    public Fish getFishByUserId(Long userId){
        return repository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found")
        );
    }

    public Fish getFish(Long fishId){
        return repository.findById(fishId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish not found"));
    }

    public Fish createFish(String drawingData, User user){
        if(repository.existsByUserId(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot have more than 1 fish");
        }
        return repository.save(new Fish(drawingData, user));
    }

    public Fish updateFish(Long userId, String drawingData){
        Fish fish = repository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish not found"));

        fish.setDrawingData(drawingData);

        return repository.save(fish);
    }

    public void deleteFish(Long userId){
        Fish fish = repository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Fish not found"));

        repository.delete(fish);
    }
}
