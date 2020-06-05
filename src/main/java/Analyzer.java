package main.java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Scanner;

public class Analyzer {
	
	public static void main(String[] args) throws IOException, ParseException {
		
		Scanner scanner = new Scanner(System.in);  
		System.out.println("Enter path to chat text file");

		String path = scanner.nextLine();  
		System.out.println("Reading file: " + path);  
		
		try {
			ChatReader chatReader = new ChatReader(path);
			Chat chat = new Chat(chatReader);
			int input = 1;
			while (input != 0) {
				System.out.println();
				System.out.println("Choose action:");
				System.out.println("1. Get basic chat information");
				System.out.println("2. Get chat member information");
				System.out.println("0. Exit");
				input = scanner.nextInt();
				scanner.nextLine();   //throw away the \n not consumed by nextInt()
				switch (input) {
				case 1:
					System.out.println(chat.getChatInfo().toString());
					break;

				case 2:
					System.out.print("Chat members: ");
					chat.getChatInfo().getPeople().forEach(name -> System.out.print(name +" "));
					System.out.println();
					System.out.println("Enter the name of the chat member:");
					String name = scanner.nextLine();
					MemberInfo memberInfo = chat.getMemberInfoByName(name);
					if (memberInfo != null) {
						System.out.println(memberInfo.toString());
					}else {
						System.out.println("Member " + name + " was not found in chat");
					}
					break;
					
				case 0:
					return;
				default:
					System.out.println("Wrong input. Try again.");
					break;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Chat file was not found.");
		}
		
	}
}
