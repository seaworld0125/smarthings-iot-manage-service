package networklab.smartapp.domain.auth;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.member.Member;
import networklab.smartapp.domain.member.MemberRepository;
import networklab.smartapp.error.exception.BusinessException;
import networklab.smartapp.error.exception.ErrorCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public void authVerify(String username, String password) throws BusinessException {

//        password = passwordEncoder.encode(password); // 내가 할 필요 없다

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void joinVerify(String pat, String username, String password) {

        Optional<Member> optionalMember = memberRepository.findMemberByUsername(username);
        if(optionalMember.isPresent()) {
            throw new BusinessException(ErrorCode.OVERLAP_MEMBER);
        }

        memberRepository.save(Member.builder()
                    .PAT(pat)
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build());
    }
}
