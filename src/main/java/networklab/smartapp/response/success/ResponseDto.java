package networklab.smartapp.response.success;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class ResponseDto<T> {
    private final int status;
    private final String message;

    @Nullable
    private final T data;

    public ResponseDto(SuccessCode successCode) {
        this.status = successCode.getStatus();
        this.message = successCode.getMessage();
        this.data = null;
    }

    public ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
    public ResponseDto(int status, String message, @Nullable T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}