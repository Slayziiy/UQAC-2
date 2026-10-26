package ca.uqac.aquarium.dto.fish;

import ca.uqac.aquarium.model.Fish;

public record FishResponse(Long id, String drawingData) {

    public static FishResponse from(Fish fish) {
        return new FishResponse(fish.getId(), fish.getDrawingData());
    }

}
