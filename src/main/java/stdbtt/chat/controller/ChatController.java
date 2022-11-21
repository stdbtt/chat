package stdbtt.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import stdbtt.chat.util.Converter;
import stdbtt.chat.dto.MessageDTO;
import stdbtt.chat.dto.ParticipantDTO;
import stdbtt.chat.model.Message;
import stdbtt.chat.model.Participant;
import stdbtt.chat.service.ChatService;
import stdbtt.chat.service.MessageService;
import stdbtt.chat.service.ParticipantService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    private final HttpSession httpSession;

    private final MessageService messageService;

    private final ParticipantService participantService;

    private final ChatService chatService;

    @Autowired
    public ChatController(HttpSession httpSession, MessageService messageService, ParticipantService participantService, ChatService chatService) {
        this.httpSession = httpSession;
        this.messageService = messageService;
        this.participantService = participantService;
        this.chatService = chatService;
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("participant", new ParticipantDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("participant") @Valid ParticipantDTO participantDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "login";
        Participant participant = Converter.convert(participantDTO);
        if(chatService.joinChat(participant)){
            httpSession.setAttribute("participantDTO", participantDTO);
            participantService.add(participant);
        }
        else{
            bindingResult.rejectValue("nickname", "", "Пользователь с таким именем уже присоединился к чату." +
                    " Выберите другой ник.");
            return "login";
        }
        return "redirect:/chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    public ResponseEntity<HttpStatus> sendMessage(@RequestBody MessageDTO messageDTO){
        messageDTO.setParticipantDTO((ParticipantDTO) httpSession.getAttribute("participantDTO"));
        Message message = Converter.convert(messageDTO);
        messageService.save(message);
        chatService.addNewMessageForAllParticipants(message);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/chat")
    public String getChat(Model model){
        if(httpSession.getAttribute("participantDTO")==null)
            return "redirect:/login";

        List<MessageDTO> messages = messageService.findAll().stream()
                .map(Converter::convert)
                .collect(Collectors.toList());
        model.addAttribute("messages", messages);
        model.addAttribute("newMessage", new MessageDTO());
        return "chat";
    }

    @GetMapping("/getNewMessages")
    @ResponseBody
    public List<MessageDTO> getNewMessages(){
        ParticipantDTO participantDTO = (ParticipantDTO)httpSession.getAttribute("participantDTO");
        Participant participant = Converter.convert(participantDTO);

        List<Message> messages = chatService.getNewMessagesForParticipant(participant);
        if(messages!=null){
            return messages.stream()
                    .map(Converter::convert)
                    .collect(Collectors.toList());
            }
        else{
            return null;
        }
    }

    @GetMapping("/getOnlineParticipants")
    @ResponseBody
    public List<ParticipantDTO> getOnlineParticipants(){
            return chatService.getOnlineParticipants().stream()
                    .map(Converter::convert)
                    .collect(Collectors.toList());
    }

    @PostMapping("/leftChat")
    public String leftChat(){
        ParticipantDTO participantDTO = (ParticipantDTO)httpSession.getAttribute("participantDTO");
        chatService.leftChat(Converter.convert(participantDTO));
        httpSession.removeAttribute("participantDTO");
        return "redirect:/login";
    }
}
