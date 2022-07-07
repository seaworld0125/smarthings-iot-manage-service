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
public class JoinDto {

    private String username;
    private String password;
    private String PAT;
}
