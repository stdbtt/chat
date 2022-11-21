package stdbtt.chat.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ParticipantDTO {

    @Size(min = 3, max = 20, message = "Ник не может быть длиннее 20  короче 3 символов.")
    @Pattern(regexp = "\\w*", message = "Ник может содержать: латинские буквы, цифры, знак '_'")
    private String nickname;

    public ParticipantDTO() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
