package UserAuthentication;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class WebReader
{
	final static HashMap<String, String> movies = new HashMap<>();
	final static HashMap<String, String> movieInformation = new HashMap<>();
	static Scanner sc = new Scanner(System.in);
	static ArrayList<String> moviesList = new ArrayList<>();
	static Random rand = new Random();
	static int tempValue;
	
	public void readPage() throws URISyntaxException
	{
		try
		{
			menu1();
			menu2();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void menu1() throws IOException
	{
		
		System.out.println("Input the genre of movie you would like to watch: ");
		Document doc = Jsoup.connect("https://www.imdb.com/feature/genre/?ref_=nv_ch_gr").get();
		
		
		Elements temp = doc.select("div.image");
		
		
		int i = 0;
		
		for(Element resultsList: temp)
		{
			i++;
			System.out.println(i + ". " + resultsList.getElementsByTag("img").first().attr("title"));
			
			String genre = resultsList.getElementsByTag("img").first().attr("title");
			String link = resultsList.getElementsByTag("a").first().absUrl("href");
			movies.put(genre, link);
		}
	}
	
	
	public static void menu2() throws IOException, URISyntaxException
	{
		
		 int randomNumber = rand.nextInt(50);
		 tempValue = randomNumber;
		//ArrayList<String> movieInfo = new ArrayList<>();
		String genreSelection = sc.next();
		
		if(movies.containsKey(genreSelection))
		{
			Document genreDoc = Jsoup.connect(movies.get(genreSelection)).get();
		
			Elements temp2 = genreDoc.select("h3.lister-item-header");
			Elements tempInfo = genreDoc.select("p.text-muted");
			
			int x = 0;
			
			for(Element movieList: temp2)
			{
				moviesList.add(movieList.getElementsByTag("a").first().text());
				
				String movieName = movieList.getElementsByTag("a").first().text();
				String movieLink = movieList.getElementsByTag("a").first().absUrl("href");
				
				movieInformation.put(movieName, movieLink);
			}
			
//			for(Element movieList: tempInfo)
//			{
//				movieInfo.add(movieList.text());
//			}
			
			System.out.println(moviesList.get(randomNumber));
			//System.out.println(movieInfo.get(0));
			menu3();
		}
		else
		{
			System.out.println("Invalid Entry.");
		}

	}
	
	public static void menu3() throws IOException, URISyntaxException
	{
		 
		boolean flag = true;
		
		
		while(flag)
		{
			int randomNumber = rand.nextInt(50);
			
		System.out.println("-------------------------");
		System.out.println("Would you like to:");
		System.out.printf("1. View another option %n2. View movie info %n3. Pick different genre %n4. Quit %n", null);
		System.out.println("Enter the appropriate number to select the option.");
		
		int selection = sc.nextInt();
		
		
		switch(selection)
		{
		
		case 1:
	
			tempValue = randomNumber; 
			System.out.println(moviesList.get(randomNumber));
			
			break;
		
		case 2:

			if(movieInformation.containsKey(moviesList.get(tempValue)))
			{
				URI uri= new URI(movieInformation.get(moviesList.get(tempValue)));
				java.awt.Desktop.getDesktop().browse(uri);
			}
	
			break;
			
		case 3:
			moviesList.clear();
			menu1();
			menu2();
			flag = false;
			break;
		
		case 4:
			
			flag = false;
			break;
			
		default: 
			break;
		}
	}
	}
	
}


















