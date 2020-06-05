package main.java;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Chat {
		
	private ChatReader chatReader;
	
	private LinkedList<Message> messages;
	
	private ChatInfo chatInfo;
	
	private HashSet<MemberInfo> memberInfos;
	
	public Chat(ChatReader chatReader) throws IOException, ParseException {
		this.chatReader = chatReader;
		messages = chatReader.readMessagesFromFile();
		chatInfo = new ChatInfo(messages);
		memberInfos = new HashSet<MemberInfo>();
		chatInfo.getPeople().forEach(name -> memberInfos.add(new MemberInfo(Message.filterMessagesByAuthor(messages, name), name)));
	}
	
	public MemberInfo getMemberInfoByName(String name) {
		return memberInfos.stream().filter(m -> m.getName().equals(name)).findAny().orElse(null);
	}

}
