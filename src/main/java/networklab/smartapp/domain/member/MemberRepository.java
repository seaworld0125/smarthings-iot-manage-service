package networklab.smartapp.domain.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-07-07
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByUsername(String username);
}
