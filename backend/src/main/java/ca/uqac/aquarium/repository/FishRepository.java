package ca.uqac.aquarium.repository;

import ca.uqac.aquarium.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishRepository extends JpaRepository<Fish, Long> {
    Optional<Fish> findByUserId(Long userId);
}
