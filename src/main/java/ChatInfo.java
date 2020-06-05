package main.java;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper=true)
public class ChatInfo extends Info{
	
	private HashSet<String> people;
	
	private LocalDate firstMessageDate;
	
	private LocalDate lastMessageDate;
	
	public ChatInfo(LinkedList<Message> messages) {
		super(messages);
		setPeople(messages);
		setFirstMessageDate(messages);
		setLastMessageDate(messages);
	}
	
	public void setPeople(LinkedList<Message> messages) {
		people = messages.stream().map(m -> m.getAuthor()).distinct().collect(Collectors.toCollection(HashSet::new));
	}

	public void setFirstMessageDate(LinkedList<Message> messages) {
		firstMessageDate = messages.getFirst().getDate();
	}
	
	public void setLastMessageDate(LinkedList<Message> messages) {
		lastMessageDate = messages.getLast().getDate();
	}

}
