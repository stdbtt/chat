package stdbtt.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stdbtt.chat.model.Participant;
import stdbtt.chat.repository.ParticipantRepository;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Transactional
    public void add(Participant participant){
        if(!participantRepository.existsParticipantByNickname(participant.getNickname())){
            participantRepository.save(participant);
        }
    }

}
