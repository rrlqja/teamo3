package song.teamo3.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
