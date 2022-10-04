import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

public class CommitTesterJUnitTesterDoesntWorkForMe {

	public static void main(String[] args) throws Exception {
//		Commit c = new Commit(c, null, "", "Asher");
		Index test = new Index();
		test.add("Ava.txt");
		test.add("file2");
		Commit c1 = new Commit(null, null, "first", "Asher");
		test.add("file4");
		test.add("eliza.txt");
		Commit c2 = new Commit(c1, null, "second", "Asher");
		test.add("file5");
		test.add("file3");
		test.delete("file4");
		Commit c3 = new Commit(c2, null, "third", "Asher");
		test.delete("file2");
		test.add("testFile.txt");
		Commit c4 = new Commit(c3, null, "fourth", "Asher");
		test.add("file1");
		test.add("file7");
		test.add("file8");
		Commit c5 = new Commit(c4, null, "fifth", "Asher");
		PrintWriter pw = new PrintWriter("file7");
		pw.println("hello mr theiss");
		pw.close();
		test.edit("file7");
		test.delete("file2");
		Commit c6 = new Commit(c5, null, "sixth", "Asher");
	}

}
