import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

public class CommitTester {

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
		c2.delete("file4");
		Commit c3 = new Commit(c2, null, "third", "Asher");
		c2.delete("file2");
		test.add("testFile.txt");
		Commit c4 = new Commit(c3, null, "fourth", "Asher");
	}

}
