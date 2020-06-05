package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.ChatInfo;
import main.java.Info;
import main.java.Message;

class InfoTest {
	
	LinkedList<Message> messages;
	
	Info info;
	
	@BeforeEach
	public void init() {
		messages = new LinkedList<Message>();
		info = new ChatInfo();
	}

	@Test
	void testSetTotalMessages() {
		messages.add(new Message());
		messages.add(new Message());		
		messages.add(new Message());
		info.setTotalMessages(messages);
		assertEquals(3, info.getTotalMessages());
	}
	
	@Test
	void testSetTotalWords() {
		messages.add(new Message(null, null, null, "This is a test message"));
		messages.add(new Message(null, null, null, "This is an another message"));
		messages.add(new Message(null, null, null, "This is a third test message"));
		info.setTotalWords(messages);
		assertEquals(16, info.getTotalWords());
	}
	
	@Test
	void testSetMostAndLeastActiveHour() {
		for(int i = 0; i < 24; i++) {
			if (i != 12)
				messages.add(new Message(LocalDate.parse("2015-10-12"), LocalTime.of(i, 0), "", ""));
		}
		messages.add(new Message(LocalDate.parse("2015-10-12"), LocalTime.parse("10:16"), "", ""));
		info.setMostAndLeastActiveHour(messages);
		assertEquals(LocalTime.of(10, 0), info.getMostActiveHour().getKey());
		assertEquals(2, info.getMostActiveHour().getValue());
		assertEquals(LocalTime.of(12, 0), info.getLeastActiveHour().getKey());
		assertEquals(0, info.getLeastActiveHour().getValue());
	}
	
	@Test
	void whenThereAreMoreThanOneMostOrLeastActiveHours_thenUseFirst() {
		messages.add(new Message(LocalDate.parse("2019-10-12"), LocalTime.parse("05:20"), "", ""));
		messages.add(new Message(LocalDate.parse("2020-06-12"), LocalTime.parse("05:15"), "", ""));
		messages.add(new Message(LocalDate.parse("2015-10-12"), LocalTime.parse("10:20"), "", ""));
		messages.add(new Message(LocalDate.parse("2019-10-12"), LocalTime.parse("10:15"), "", ""));
		info.setMostAndLeastActiveHour(messages);
		assertEquals(LocalTime.of(5, 0), info.getMostActiveHour().getKey());
		assertEquals(LocalTime.of(0, 0), info.getLeastActiveHour().getKey());
	}
	
	@Test
	void testGetWordsFrequency(){
		messages.add(new Message(null, null, null, "This is a test, message."));
		messages.add(new Message(null, null, null, "Here is an another message, - message"));
		messages.add(new Message(null, null, null, "This test method is only counting words which are longer than three letters"));
		HashMap<String, Integer> words = info.getWordsWithFrequency(messages, 3);
		assertEquals(3, words.get("message"));
		assertEquals(2, words.get("This"));
		assertEquals(1, words.get("counting"));
	}
	
	@Test
	void testSetThreeMostUsedWordsWhichAreLongerThanThreeLetters() {
		messages.add(new Message(null, null, null, "This is a test, message."));
		messages.add(new Message(null, null, null, "Here is an another message, - message"));
		messages.add(new Message(null, null, null, "This test method is only counting words which are longer than three letters"));
		info.setMostUsedWords(messages, 3, 3);
		assertEquals(3, info.getMostUsedWords().size());
		String[] mostUsedWords = {"message", "This", "test"};
		Integer[] values = {3, 2, 2};
 		assertTrue(info.getMostUsedWords().keySet().containsAll(Arrays.asList(mostUsedWords)));
		assertTrue(info.getMostUsedWords().values().containsAll(Arrays.asList(values)));	
	}

}
