package stdbtt.chat.dto;

public class MessageDTO {

    private String text;

    private ParticipantDTO participantDTO;

    public MessageDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ParticipantDTO getParticipantDTO() {
        return participantDTO;
    }

    public void setParticipantDTO(ParticipantDTO participantDTO) {
        this.participantDTO = participantDTO;
    }
}
