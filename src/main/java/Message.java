package main.java;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	
	private LocalDate date;
	
	private LocalTime time;
	
	private String author;
	
	private String text;
	
	public static LinkedList<Message> filterMessagesByAuthor(LinkedList<Message> messages, String name){
		return messages.stream().filter(m -> m.getAuthor().equals(name)).collect(Collectors.toCollection(LinkedList::new));
	}

}
