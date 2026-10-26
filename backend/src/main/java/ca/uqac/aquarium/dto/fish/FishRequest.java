package ca.uqac.aquarium.dto.fish;

import jakarta.validation.constraints.NotBlank;

public record FishRequest(@NotBlank String drawingData) {
}
