package networklab.smartapp.domain.dto;

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
                .build();
    }
}
