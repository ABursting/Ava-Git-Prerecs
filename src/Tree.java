
import java.util.*;
import java.io.*;
import java.security.*;
import java.math.BigInteger;

public class Tree {
	
	private ArrayList<String> arr;
	private String str;
	private String hash;
	private File tree;
	public Tree(ArrayList<String> list) throws IOException, NoSuchAlgorithmException {
		arr = list;
		str = "";
		for (String s : arr)
			str += s + "\n";
		if (str.length() > 0)
			str = str.substring(0, str.length()-1);
		hash = getSHA1(str);
	}
	
	public void writePairs() throws IOException {
		PrintWriter pw = new PrintWriter(new File("objects/" + hash));
		for (String s : arr)
			pw.append(s + "\n");
		pw.close();
	}
	
	private String getSHA1(String s1){
		String value = s1;
		String output = "";

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(value.getBytes("utf8"));
			output = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception exception){
			exception.printStackTrace();
		}

		return output; 
	}
	
	public String getName() {
		return "tree:" + hash;
	}
	public String getNameNoType() {
		return hash;
	}
	
//	private static String getSHA1(String input) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//		 FileInputStream fileInputStream = new FileInputStream(input);
//         MessageDigest digest = MessageDigest.getInstance("SHA-1");
//         DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
//         byte[] bytes = new byte[1024];
//
//         while (digestInputStream.read(bytes) > 0);
//
//         byte[] resultByteArry = digest.digest();
//         return bytesToHexString(resultByteArry);
//	}
//	
//	public static String bytesToHexString(byte[] bytes) {
//        StringBuilder sb = new StringBuilder();
//        for (byte b : bytes) {
//            int value = b & 0xFF;
//            if (value < 16)
//                sb.append("0");
//            sb.append(Integer.toHexString(value));
//        }
//        return sb.toString();
//    }
}
