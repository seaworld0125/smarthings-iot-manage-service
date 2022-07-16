package networklab.smartapp.domain.auth;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.dto.JoinDto;
import networklab.smartapp.domain.member.Member;
import networklab.smartapp.domain.member.MemberRepository;
import networklab.smartapp.error.exception.BusinessException;
import networklab.smartapp.error.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean authVerify(String username, String password) throws BusinessException {

        Optional<Member> optionalMember = memberRepository.findMemberByUsername(username);
        if(optionalMember.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }

        String memberPassword = optionalMember.get().getPassword();
        if(!passwordEncoder.matches(password, memberPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_INVALID);
        }

        return true;
    }

    @Override
    public boolean joinVerify(JoinDto joinDto) {

        Optional<Member> optionalMember = memberRepository.findMemberByUsername(joinDto.getUsername());
        if(optionalMember.isEmpty()) {
            throw new BusinessException(ErrorCode.OVERLAP_MEMBER);
        }

        memberRepository.save(Member.builder()
                    .PAT(joinDto.getPAT())
                    .username(joinDto.getUsername())
                    .password(passwordEncoder.encode(joinDto.getPassword()))
                    .build());

        return true;
    }
}
