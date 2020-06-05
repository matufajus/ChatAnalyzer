package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import main.java.Message;

class MessageTest {

	@Test
	void testFilterMessageByAuthor() {
		LinkedList<Message> messages = new LinkedList<Message>();
		messages.add(new Message(null, null, "Kevin", ""));
		messages.add(new Message(null, null, "Kevin", ""));
		messages.add(new Message(null, null, "John", ""));
		messages.add(new Message(null, null, "John", ""));
		messages.add(new Message(null, null, "John", ""));
		assertEquals(2, Message.filterMessagesByAuthor(messages, "Kevin").size());
		assertEquals(3, Message.filterMessagesByAuthor(messages, "John").size());
	}

}
