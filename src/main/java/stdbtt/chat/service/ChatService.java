package stdbtt.chat.service;

import org.springframework.stereotype.Service;
import stdbtt.chat.model.Message;
import stdbtt.chat.model.Participant;

import java.util.*;

@Service
public class ChatService {
    private final HashMap<String, LinkedList<Message>> newMessages = new HashMap<>();
    private final List<Participant> onlineParticipants = new LinkedList<>();

    private final Object messageLock = new Object();
    private final Object participantLock = new Object();

    public boolean joinChat(Participant participant){
        synchronized(participantLock) {
            if (onlineParticipants.contains(participant)) {
                return false;
            } else {
                onlineParticipants.add(participant);
                newMessages.put(participant.getNickname(), new LinkedList<>());
                return true;
            }
        }
    }

    public void leftChat(Participant participant){
        synchronized (participantLock) {
            onlineParticipants.remove(participant);
        }
        synchronized (messageLock) {
            newMessages.remove(participant.getNickname());
        }
    }

    public void addNewMessageForAllParticipants(Message message){
        synchronized (messageLock) {
            for( LinkedList<Message> newMessagesForParticipant : newMessages.values()){
                newMessagesForParticipant.add(message);
            }
        }
    }

    public List<Message> getNewMessagesForParticipant(Participant participant){
        if(participant!=null) {
            synchronized (messageLock) {
                List<Message> messages = newMessages.get(participant.getNickname());
                List<Message> copyMessages = new LinkedList<>(messages);
                messages.clear();
                return copyMessages;
            }
        }
        return null;
    }

    public List<Participant> getOnlineParticipants(){
        synchronized (participantLock) {
            return onlineParticipants;
        }
    }

}
