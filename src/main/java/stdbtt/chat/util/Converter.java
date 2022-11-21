package stdbtt.chat.util;

import stdbtt.chat.dto.MessageDTO;
import stdbtt.chat.dto.ParticipantDTO;
import stdbtt.chat.model.Message;
import stdbtt.chat.model.Participant;

public class Converter {
    public static ParticipantDTO convert(Participant participant){
        if(participant!=null) {
            ParticipantDTO participantDTO = new ParticipantDTO();
            participantDTO.setNickname(participant.getNickname());
            return participantDTO;
        }
        return null;
    }

    public static Participant convert(ParticipantDTO participantDTO){
        if(participantDTO!=null) {
            Participant participant = new Participant();
            participant.setNickname(participantDTO.getNickname());
            return participant;
        }
        return null;
    }

    public static MessageDTO convert(Message message){
        if(message!=null){
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(message.getText());
            Participant participant = message.getParticipant();
            messageDTO.setParticipantDTO(convert(participant));
            return messageDTO;
        }
        return null;
    }

    public static Message convert(MessageDTO messageDTO){
        if(messageDTO!=null) {
            Message message = new Message();
            message.setText(messageDTO.getText());
            ParticipantDTO participantDTO = messageDTO.getParticipantDTO();
            message.setParticipant(convert(participantDTO));
            return message;
        }
        return null;
    }
}
