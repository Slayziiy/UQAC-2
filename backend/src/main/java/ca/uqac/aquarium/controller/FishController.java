package ca.uqac.aquarium.controller;

import ca.uqac.aquarium.dto.fish.FishRequest;
import ca.uqac.aquarium.dto.fish.FishResponse;
import ca.uqac.aquarium.model.User;
import ca.uqac.aquarium.repository.UserRepository;
import ca.uqac.aquarium.service.FishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/fishes")
public class FishController {

    private final FishService fishService;
    private final UserRepository userRepository;

    public FishController(FishService fishService, UserRepository userRepository) {
        this.fishService = fishService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<FishResponse> allFishes(){
        return fishService.all().stream().map(FishResponse::from).toList();
    }

    @GetMapping("/me")
    public FishResponse getMyFish(@AuthenticationPrincipal Jwt jwt){
        return FishResponse.from(fishService.getFishByUserId(getUserId(jwt)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FishResponse createFish(@Valid @RequestBody FishRequest request, @AuthenticationPrincipal Jwt jwt){
        return FishResponse.from(fishService.createFish(request.drawingData(), getUser(jwt)));
    }

    @PutMapping("/me")
    public FishResponse updateFish(@Valid @RequestBody FishRequest request, @AuthenticationPrincipal Jwt jwt){
        return FishResponse.from(fishService.updateFish(getUserId(jwt), request.drawingData()));
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFish(@AuthenticationPrincipal Jwt jwt){
        fishService.deleteFish(getUserId(jwt));
    }

    @GetMapping("/{id}")
    public FishResponse getByFishId(@PathVariable Long id){
        return FishResponse.from(fishService.getFish(id));
    }

    private User getUser(Jwt jwt){
        return userRepository.getReferenceById(getUserId(jwt));
    }

    private Long getUserId(Jwt jwt){
        Object claim = jwt.getClaim("userId");
        if (claim == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        return ((Number) claim).longValue();
    }

}
