import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class CommitTester {

	public static void main(String[] args) throws Exception {
		Index test = new Index();
		test.initialize();
		test.add("Ava.txt");
		Commit test1 = new Commit(null, null, "first", "asher");
		test.add("eliza.txt");
		test.add("testFile.txt");
		test.add("file4");
		Commit test2 = new Commit(test1, null, "first", "asher");
		System.out.println("prevtree: " + test2.getPrevTree());
		test2.delete("testFile.txt");
		test.add("file5");
		Commit test3 = new Commit(test2, null, "first", "asher");
	}

}
