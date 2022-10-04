
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	private Commit p; 
	private Commit c; // will this always be null?
	private String commitLocation;
	private Tree tree; 
	public String s;
	public String a;
	public String date; 
	public String pTree;	
	public Commit(Commit parent, Commit child, String summary, String author) throws NoSuchAlgorithmException, IOException
	{
		p = parent;
		c = child;
		//		date = getDate();
		BufferedReader br = new BufferedReader(new FileReader("index"));
		ArrayList<String> lines = new ArrayList<String>();
		String line; String temp; boolean first = true;
		a = author;
		s = summary;
		if(p == null) {
			while(br.ready()) {
				temp = br.readLine();
				lines.add("blob:" + temp.substring(temp.indexOf(":") + 1) + " " + temp.substring(0, temp.indexOf(":")));
			}
		}
		else {
			lines.add(getPrevTree());
			while(br.ready()) {
				temp = br.readLine();
				if(temp.charAt(1) == 'd' || temp.charAt(1) == 'e') {
					String fileToDelete = temp.substring(8);
					boolean inLines = false;
					for(int i = 0; i < lines.size(); i++) {
						if(lines.get(i).indexOf(fileToDelete) > -1) {
							inLines = true;
							lines.remove(i);
						}
					}
					if(inLines == false) {
						lines = parseFile(fileToDelete, lines);
					}
				}
				else {
					lines.add("blob:" + temp.substring(temp.indexOf(":") + 1) + " " + temp.substring(0, temp.indexOf(":")));
				}
			}
		}
		tree = new Tree(lines);
		tree.writePairs();

		PrintWriter pw = new PrintWriter("head");
		pw.print(tree.getName());
		pw.close();

		PrintWriter writer = new PrintWriter("index");
		writer.print("");
		writer.close();
	}

	public ArrayList<String> parseFile(String deletedFile, ArrayList<String> treeList) throws IOException {
		boolean found = false;
		boolean end = true;
		ArrayList<String> list = treeList;
		String enterTree = treeList.remove(0);
		BufferedReader br = new BufferedReader(new FileReader("objects/" + enterTree.substring(enterTree.indexOf(":") + 1)));
		String temp;
		while (br.ready()) {
			temp = br.readLine();
			if(temp.charAt(0) == 't') {
				list.add(0, temp);
				end = false;
			}
			else if (temp.indexOf(deletedFile) > -1) {
				found = true;
			}
			else {
				list.add(temp);
			}
		}
		if(found == true) {
			return list;
		}
		if(!found && end == false) {
			return parseFile(deletedFile, list);
		}
		if(end == true) {
			return list;
		}
		return list;
	}

	public void setChild(Commit newChild) {
		c = newChild;
	}

	public String getPrevTree() {
		return p.tree.getName();
	}

	//	public String getDate()
	//	{
	//		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	//		Calendar cal = Calendar.getInstance();
	//		String dt = dateFormat.format(cal.getTime());
	//		return dt; // should I use the member variable here?
	//	}

	//	public void updateParent() throws NoSuchAlgorithmException, IOException
	//	{
	//		if (p != null)
	//		{
	//			String location = "objects/" + getLocation();
	//			System.out.println(location);
	//			setVariable(3, location, ("objects/" + p.getLocation())); 
	//			// get parent location, edit the file to make child the location of new node
	//			
	//		}
	//		else
	//		{
	//			return; 
	//		}
	//	}

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

	//	public String getContents() throws NoSuchAlgorithmException, IOException
	//	{
	//		String content = ""; 
	//		content += pTree + "\n"; 
	//		if (p != null)
	//		{
	//			content += p.getLocation() + "\n";
	//		}
	//		else
	//		{
	//			content += "\n";
	//		}
	//		if (c != null)
	//		{
	//			content += c.getLocation() + "\n";
	//		}
	//		else
	//		{
	//			content += "\n";
	//		}
	//		content += a + "\n";
	//		content += getDate() + "\n";
	//		content += s; 
	////		System.out.println(content);
	//		return content;
	//		
	//	}

	//	public String getLocation() throws NoSuchAlgorithmException, IOException
	//	{
	//		return SHA1(getContents()); 
	//	}
	//	
	//	public String writeFile() throws NoSuchAlgorithmException, IOException
	//	{
	//		Path p = Paths.get("objects/" + SHA1(getContents()));
	////		System.out.println(p); 
	//        try {
	//            Files.writeString(p, getContents(), StandardCharsets.ISO_8859_1);
	//        } catch (IOException e) {
	//            // TODO Auto-generated catch block
	//            e.printStackTrace();
	//        }
	//        return p.toString(); 
	//	}

}
