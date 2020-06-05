package main.java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map.Entry;

public class ChatReader {
	
	String path;
	
	public ChatReader(String path) {
		this.path = path;
	}
	
	public LinkedList<Message> readMessagesFromFile() throws IOException, ParseException {
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		LocalDate date;
		LocalTime time;
		String st, author, text;
		LinkedList<Message> messages = new LinkedList<Message>();
		//Skip first line 
		br.readLine();	 
		//Start reading the file
		while ((st = br.readLine()) != null) {
			if (st.matches("^\\d{4}-\\d{2}-\\d{2}.*")) {
				date =  LocalDate.parse(extractDate(st));
				time = LocalTime.parse(extractTime(st));
				author = extractName(st);
				text = extractText(st);
				Message message = new Message(date, time, author, text);
				messages.add(message);
			}
			else {
				messages.getLast().setText(messages.getLast().getText() + st);
			}
		}
		br.close();
		return messages;
	}
	
	private static String extractText(String st) {
		int nameEndIndex = st.indexOf(":", 19);
		return st.substring(nameEndIndex+1);
	}

	private static String extractName(String st) {
		int nameEndIndex = st.indexOf(":", 19);
		return st.substring(19, nameEndIndex);		
	}
	
	private static String extractDate(String st) {
		return st.substring(0, 10);
	}
	
	private static String extractTime(String st) {
		return st.substring(11, 16);
	}
	
}
