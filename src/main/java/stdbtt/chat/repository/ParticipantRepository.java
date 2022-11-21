package stdbtt.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdbtt.chat.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    boolean existsParticipantByNickname(String nickname);

    Participant getParticipantByNickname(String nickname);
}
