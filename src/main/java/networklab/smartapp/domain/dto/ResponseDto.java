package networklab.smartapp.domain.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-07-07
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private String message;

    private Object data;

    public static ResponseDto of(String message) {
        return ResponseDto.builder()
                .message(message)
                .data(new ArrayList<>())
                .build();
    }

    public static ResponseDto of(String message, Object data) {
        return ResponseDto.builder()
                .message(message)
                .data(data)
                .build();
    }
}
