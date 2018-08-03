package Music;

public class Song {
	String name;
	String composer;
	int id;
	void addSong(String name,String composer)
	{
		this.name=name;
		this.composer=composer;
	}
	void songId(int id)
	{
		this.id=id;
	}
	void songDetails()
	{
		System.out.println("Name:" + name);
		System.out.println("Composer:" + composer);
		System.out.println("Id:" + id);
	}
	String getSongName()
	{
		return name;
	}
	String getComposerName()
	{
		return composer;
	}
}
