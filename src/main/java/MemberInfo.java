package main.java;
import java.util.LinkedList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class MemberInfo extends Info{
	
	private String name;
	
	public MemberInfo(LinkedList<Message> messages, String name) {
		super(messages);
		this.name = name;
	}
	
	
	
	

}
