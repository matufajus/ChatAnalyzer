package main.java;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public abstract class Info {
	
	long totalMessages;
	
	long totalWords;
	
	SimpleEntry<LocalTime, Integer> mostActiveHour;
	 
	SimpleEntry<LocalTime, Integer> leastActiveHour;
	
	//top 5 most used words longer than 3 letters
	LinkedHashMap<String, Integer> mostUsedWords;
	
	public Info(LinkedList<Message> messages) {
		setTotalMessages(messages);
		setTotalWords(messages);
		setMostAndLeastActiveHour(messages);
		setMostUsedWords(messages, 5, 3);
	}
	
	public void setTotalMessages(LinkedList<Message> messages) {
		totalMessages = messages.size();
	}
	
	public void setTotalWords(LinkedList<Message> messages) {
		Stream<String[]> words = messages.stream().map(m->m.getText().split("\\s+"));	
		Stream<String> flatWords = words.flatMap(x -> Arrays.stream(x));
		totalWords = flatWords.collect(Collectors.counting());
	}
	
	public void setMostAndLeastActiveHour(LinkedList<Message> messages) {
		//Creating ArrayList to store 24 integers which represent hours from 00:00, to 23:00
		ArrayList<Integer> hours = new ArrayList<Integer>();
		for(int i = 0; i < 24; i++) {
			hours.add(i, 0);
		}
		//For each message, value at specific index is incremented by one.
		for (Message message : messages) {
			int hour = message.getTime().getHour();
			hours.set(hour, hours.get(hour)+1);
		}
		int maxMessages = Collections.max(hours);
		int maxHour = hours.indexOf(maxMessages);
		int minMessages = Collections.min(hours);
		int minHour = hours.indexOf(minMessages);
		mostActiveHour = new SimpleEntry<LocalTime, Integer>(LocalTime.of(maxHour, 0), maxMessages);
		leastActiveHour = new SimpleEntry<LocalTime, Integer>(LocalTime.of(minHour, 0), minMessages);
	}

	public void setMostUsedWords(LinkedList<Message> messages, int amountOfWords, int minNumberOfLetters){
		HashMap<String, Integer> frequentWords = getWordsWithFrequency(messages, minNumberOfLetters);
		List<Integer> frequences = new ArrayList<Integer>(frequentWords.values());
		Collections.sort(frequences, Collections.reverseOrder());
		frequences = frequences.subList(0, amountOfWords);
		LinkedHashMap<String, Integer> topFrequentWords = new LinkedHashMap<String, Integer>();
		for(int amount : frequences) {
			String word = HashMapHelper.getKeyByValue(frequentWords, amount);
			frequentWords.remove(word);
			topFrequentWords.put(word, amount);
		}
		mostUsedWords = topFrequentWords;
	}
	
	public HashMap<String, Integer> getWordsWithFrequency(LinkedList<Message> messages, int minNumberOfLetters){
		HashMap<String, Integer> wordsCounter = new HashMap<String, Integer>(); 
		Stream<String[]> words = messages.stream().map(m->m.getText().split("[\\s+,.-;!?]"));	
		Stream<String> flatWords = words.flatMap(x -> Arrays.stream(x));	
		Stream<String> filteredWords = flatWords.filter(w -> (!w.isEmpty() && (w.length() > minNumberOfLetters)) && (!w.startsWith("<") && (!w.endsWith(">"))));
		List<String> messageWords = filteredWords.collect(Collectors.toList());
		messageWords.forEach(word -> word.replaceAll("[-+.^:,]", ""));
		for (String word : messageWords) {
			if (!wordsCounter.containsKey(word)) {
				wordsCounter.put(word, 1);
			}else {
				int amount = wordsCounter.get(word);
				wordsCounter.replace(word, amount+1);	
			}
		}
		return wordsCounter;
	}

}
