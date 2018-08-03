package Music;
import java.util.*;
import java.io.*;
public class MainClass {
public ArrayList<Song>songList=new ArrayList<Song>();
public ArrayList<PlayList>playList=new ArrayList<PlayList>();
public int recentId;
public Scanner sc=new Scanner(System.in);
int display()
{
	System.out.println("*****************************");
	System.out.println("Enter your choice");
	System.out.println("1.Add song");
	System.out.println("2.Song Details");
	System.out.println("3.all songs");
	System.out.println("4.del Song");
	System.out.println("5.Play Lists");
	System.out.println("6.exit");
	System.out.println("*****************************");
	int choice=sc.nextInt();
	return choice;
}
int playListDisplay()
{
	System.out.println("Enter your choice");
	System.out.println("2.Create Play List");
	System.out.println("3.Show all playLists");
	System.out.println("4.show PlayList");
	System.out.println("5.Back");
	int choice=sc.nextInt();
	return choice;
}
void loadData() throws IOException
{
	try {
		FileReader fr=new FileReader("songs.txt");
		BufferedReader br=new BufferedReader(fr);
		String line;
		while((line=br.readLine())!=null)
		{
			    String[] str=line.split(",");
				Song s=new Song();
				s.addSong(str[0],str[1]);
				s.id=Integer.parseInt(str[2]);
				songList.add(s);
		}
		fr.close();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
	
}
void unloadData() {
	try {
		FileWriter fw=new FileWriter("songs.txt");
		PrintWriter pw=new PrintWriter(fw);
		int index=0;
		while(index<songList.size())
		{
			Song s=songList.get(index);
			pw.println(s.getSongName()+","+s.composer+","+ s.id);
			index++;
		}
		pw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
void unloadPlayLists()
{
	try {
		FileWriter fw=new FileWriter("playlists.txt");
		PrintWriter pw=new PrintWriter(fw);
		int index=0;
		while(index<playList.size())
		{
			PlayList p=playList.get(index);
			pw.print(p.id+","+p.getName());
			ArrayList<Song> temp=p.slist;
			int index1=0;
			while(index1<temp.size())
			{
				Song s=temp.get(index1);
				pw.print("," + s.id + "," + s.name + "," + s.composer);
				index1++;
			}
			index++;
			pw.println();
			//pw.println("EOP");
		}
		pw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
void loadPlayLists() throws NumberFormatException, IOException
{
	try {
		FileReader fr=new FileReader("playlists.txt");
		BufferedReader br=new BufferedReader(fr);
		String line;
		while((line=br.readLine())!=null)
		{
			PlayList p=new PlayList();
			    String[] str=line.split(",");
			    int id=Integer.parseInt(str[0]);
			    p.setId(id);
			    p.setName(str[1]);
			    if(str.length!=2)
			    {
			    int index=2;
			    ArrayList<Song> temp=new ArrayList<Song>();
			    while(index<str.length)
			    {
			    	Song s=new Song();
			    	s.id=Integer.parseInt(str[index]);
			    	s.addSong(str[index+1], str[index+2]);
			    	index+=3;
			    	temp.add(s);
			    }
			    p.setList(temp);
			    playList.add(p);
			    }
				
		}
		fr.close();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
}
void playLists()
{ int temp;
	while((temp=playListDisplay())!=5)
	{
		if(temp==2)
			createPlayList();
		if(temp==3)
			showAllPlayLists();
		if(temp==4)
			selectPlayList();
		if(temp==5)
			return;
	}
}
void createPlayList()
{
	System.out.println("Enter the PlayList Name");
	String  name=sc.next();
	PlayList p=new PlayList();
	p.setName(name);
	System.out.println("Enter the song id's separated by coma's to get added to the play list");
	allSongs();
	if(!songList.isEmpty())
	{
    try
	{
	String temp=sc.next();
	String[] temp1=temp.split(",");
	int[] temp2=new int[temp1.length];
	int flag=0;
	for(int i=0;i<temp1.length;i++)
	{
		temp2[i]=Integer.parseInt(temp1[i]);
		System.out.println(temp2[i]);
		int index=0;
		while(index<songList.size())
		{
			Song s=songList.get(index);
			if(s.id==temp2[i])
			{
				p.slist.add(s);
				flag++;
				break;
			}
			index++;
			
			
		}
		if(index==songList.size())
			System.out.println("Sorry:( this id "+ temp2[i]+" does not exists please check it");
		
	}
	if(playList.isEmpty())
	{
		p.id=1;
	}
	else
	{
		int len=playList.size();
		len+=1;
		p.id=len;
	}
	if(flag>0)
	{
		playList.add(p);
		System.out.println("You have uccessfully added the play list");
		return;
	}
	else
		System.out.println("Sorry play list is not created");
	}
	catch(Exception e)
   {
	System.out.println(e);
	}

	}
	
}
void showAllPlayLists()
{
	int index=0;
	if(playList.isEmpty())
	{
		System.out.println("Sorry No play Lists were present");
		return;
	}
	while(index<playList.size())
	{
		PlayList p=playList.get(index);
		System.out.println(p.name);
		index++;
	}
	System.out.println("1.select playList");
	System.out.println("2.Back");
	int choice=sc.nextInt();
	if(choice==2)
		return;
	if(choice==1)
	{
		selectPlayList();
	}
}
void selectPlayList()
{
	System.out.println("Please enter the play list name you want to know");
	String pname=sc.next();
	int index=0;
	while(index<playList.size())
	{
		PlayList p=playList.get(index);
		if(pname.equals(p.getName()))
		{
		System.out.println("id :" + p.id);
		System.out.println("name :" +p.name );
		ArrayList<Song> temp=p.getList();
		int index1=0;
		while(index1<temp.size())
		{
			Song s=temp.get(index1);
			System.out.println("song Id: " + s.id);
			System.out.println("Song Name: "+ s.getSongName());
			System.out.println("Composer: " + s.getComposerName());
			index1++;
			
		}
		}
		index++;
	}
}
void delSong()
{
	System.out.println("Enter the song name");
	String song=sc.next();
	if(songList.isEmpty())
	{
	System.out.println("Sorry!! List is empty");
	return;
	}
	int index=0;
	while(index<songList.size())
	{
		Song s=songList.get(index);
		if(s.getSongName().equals(song))
		{
			int delid=s.id;
			if(index!=(songList.size()-1))
			adjustIndex(delid,index);
			removeFromPlayList(delid);
			songList.remove(index);
			return;
		}
		index++;
	}
	System.out.println("Song not found");
	}
void adjustIndex(int delid,int delIndex)
{
	for(int i=delIndex+1;i<songList.size();i++)
	{
		 Song s=songList.get(i);
		 int temp=s.id;
		 temp-=1;
		 s.id=temp;
	}
}
void removeFromPlayList(int delid)
{
	int index=0;
	while(index<playList.size())
	{
		PlayList p=playList.get(index);
		ArrayList<Song> temp=p.getList();
		int index1=0;
		while(index1<temp.size())
		{
			Song s=temp.get(index1);
			if(delid==s.id)
			{
				temp.remove(s);
			}
			index1++;
			
		}
		p.setList(temp);
		index++;
	}
	
}
void allSongs()
{
	if(songList.isEmpty())
		System.out.println("Sorry!! No songs were there");
	int index=0;
	while(index<songList.size())
	{
		Song s=songList.get(index);
		System.out.println(s.id+ ". "+ s.getSongName());
		index++;
	}
	
}
void songDetails()
{
	System.out.println("Enter the song name");
	String song=sc.next();
	//System.out.println(song);
	int index=0;
	if(songList.isEmpty())
		System.out.println("Sorry!! No songs were there");
	while(index<songList.size())
	{
		Song s=songList.get(index);
		if(s.getSongName().equals(song))
		{
		s.songDetails();
		return;
		}
		index++;
	}
	System.out.println("Sorry! song not found");
}
int getId()
{
	if((songList.isEmpty()))
	{
		return 0;
	}
	recentId=songList.size();
	return(recentId);
}
void addSong()
{
Song s=new Song();
System.out.println("Enter name and composer");
String name=sc.next();
String composer=sc.next();
s.addSong(name, composer);
int id=getId();
id+=1;
s.songId(id);
songList.add(s);
//s.songDetails();
}
	public static void main(String[] args) throws IOException {
		MainClass m=new MainClass();
		int temp;
		m.loadData();
		m.loadPlayLists();
		while((temp=m.display())!=6)
		{
			if(temp==1)
				m.addSong();
			if(temp==2)
				m.songDetails();
			if(temp==3)
				m.allSongs();
			if(temp==4)
				m.delSong();	
			if(temp==5)
				m.playLists();
		}
		m.unloadData();
		m.unloadPlayLists();
		
		
		
}
}
