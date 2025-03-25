package song.teamo3.domain.studyfavorite.dto;

import lombok.Data;

@Data
public class FavoriteDto {
    private boolean isFavorite;
    private Long favorites;

    public FavoriteDto(Long favorites) {
        this.isFavorite = false;
        this.favorites = favorites;
    }

    public FavoriteDto(Long favorites, boolean isFavorite) {
        this.favorites = favorites;
        this.isFavorite = isFavorite;
    }
}
