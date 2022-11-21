package stdbtt.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stdbtt.chat.model.Message;
import stdbtt.chat.model.Participant;
import stdbtt.chat.repository.MessageRepository;
import stdbtt.chat.repository.ParticipantRepository;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final ParticipantRepository participantRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ParticipantRepository participantRepository) {
        this.messageRepository = messageRepository;
        this.participantRepository = participantRepository;
    }


    public void save(Message message){
        String nickname = message.getParticipant().getNickname();
        Participant participantWithId = participantRepository.getParticipantByNickname(nickname);
        message.setParticipant(participantWithId);
        messageRepository.save(message);
    }

    public List<Message> findAll(){
        return messageRepository.findAll();
    }

}
