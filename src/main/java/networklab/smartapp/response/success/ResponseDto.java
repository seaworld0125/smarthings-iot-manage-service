package networklab.smartapp.response.success;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private final int status;
    private final String message;

    @Nullable
    private final T data;

    public static ResponseDto of(SuccessCode successCode) {
        return ResponseDto.of(successCode.getStatus(), successCode.getMessage(), null);
    }
    public static ResponseDto of(int status, String message) {
        return ResponseDto.of(status, message, null);
    }
    public static <D> ResponseDto<D> of(SuccessCode successCode, D data) {
        return ResponseDto.of(successCode.getStatus(), successCode.getMessage(), data);
    }
    public static <D> ResponseDto<D> of(int status, String message, @Nullable D data) {
        return new ResponseDto<>(status, message, data);
    }
}