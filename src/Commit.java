
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;


public class Commit {
	private Commit parent = null; 
	private Commit connected = null; // will this always be null?
	private String commitLocation;
	private String pTree; 
	public String summary;
	public String author;
	public String date; 
	public Commit(String parent, String connected, String summary, String author) throws NoSuchAlgorithmException, IOException
	{
		date = getDate();
		BufferedReader br = new BufferedReader(new FileReader("objects/index"));
		ArrayList<String> lines = new ArrayList<String>();
		while(br.ready()) {
			lines.add(br.readLine());
		}
		//have to add pointers to previous tree in lines
		this.summary = summary;
		this.author = author; 
		commitLocation = getLocation();
		writeFile(); 
		updateParent();
	}
	
	public String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		String dt = dateFormat.format(cal.getTime());
		return dt; // should I use the member variable here?
	}
	
	public void updateParent() throws NoSuchAlgorithmException, IOException
	{
		if (parent != null)
		{
			String location = "objects/" + getLocation();
			System.out.println(location);
			setVariable(3, location, ("objects/" + parent.getLocation())); 
			// get parent location, edit the file to make child the location of new node
			
		}
		else
		{
			return; 
		}
	}
	
	public static void setVariable(int lineNumber, String data, String fileName) throws IOException {
	    Path path = Paths.get(fileName);
	    ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(lineNumber - 1, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
//	public static String readFile(String fileName) throws IOException
//	{
//		Path path = Paths.get(fileName);
//		String str = Files.readString(path);
//		System.out.println(str); 
//		return str; 
//	}

	
	private static String SHA1(String contents)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(contents.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	public String getContents() throws NoSuchAlgorithmException, IOException
	{
		String content = ""; 
		content += pTree + "\n"; 
		if (parent != null)
		{
			content += parent.getLocation() + "\n";
		}
		else
		{
			content += "\n";
		}
		if (connected != null)
		{
			content += connected.getLocation() + "\n";
		}
		else
		{
			content += "\n";
		}
		content += author + "\n";
		content += getDate() + "\n";
		content += summary; 
//		System.out.println(content);
		return content;
		
	}
	
	public String getLocation() throws NoSuchAlgorithmException, IOException
	{
		return SHA1(getContents()); 
	}
	
	public String writeFile() throws NoSuchAlgorithmException, IOException
	{
		Path p = Paths.get("objects/" + SHA1(getContents()));
//		System.out.println(p); 
        try {
            Files.writeString(p, getContents(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return p.toString(); 
	}

}
