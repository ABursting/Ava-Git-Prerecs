
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.DigestInputStream;

	 
	public class Blob {
		String origFilePath;
	    public Blob(String filePath) throws Exception
	    {
	    	origFilePath = filePath;
	    	String fileName = sha1Code(filePath); 
	    	System.out.println(fileName); 
	    	BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    	String content = "";
	    	while (reader.ready())
	    	{
	    		content += (char)reader.read();
	    	}
	    	reader.close();
	    	Path filePathToWrite = Paths.get("objects/" + fileName); 
	    	try {
	    		Files.writeString(filePathToWrite, content, StandardCharsets.ISO_8859_1);
	    	} catch (IOException exception) {
	    		System.out.println("Write failed for " + fileName); 
	    	}
	    }
	    
	    
//	    public static void main(String[] args) throws Exception
//	    {
//	    	Blob testBlob = new Blob ("/Users/avagrace/eclipse-workspace/Git-Prerecs/src/testFile.txt"); 
//	    }
	    
//	    public static void main(String[] args) throws IOException
//	    {
//	    	 // Creating two stream
//	        // one input and other output
//	        FileInputStream fis = null;
//	        FileOutputStream fos = null;
//	 
//	        // Try block to check for exceptions
//	        try {
//	 
//	            // Initializing both the streams with
//	            // respective file directory on local machine
//	 
//	            // Custom directory path on local machine
//	            fis = new FileInputStream("/Users/avagrace/eclipse-workspace/Git-Prerecs/src/testFile.txt");
//	 
//	            // Custom directory path on local machine
//	            fos = new FileOutputStream("/Users/avagrace/eclipse-workspace/Git-Prerecs/objects/testFile2");
//	 
//	            int c;
//	 
//	            // Condition check
//	            // Reading the input file till there is input
//	            // present
//	            while ((c = fis.read()) != -1) {
//	 
//	                // Writing to output file of the specified
//	                // directory
//	                fos.write(c);
//	            }
//	 
//	            // By now writing to the file has ended, so
//	 
//	            // Display message on the console
//	            System.out.println("copied the file successfully");
//	        }
//	 
//	        // Optional finally keyword but is good practice to
//	        // empty the occupied space is recommended whenever
//	        // closing files,connections,streams
//	        finally {
//	 
//	            // Closing the streams
//	 
//	            if (fis != null) {
//	 
//	                // Closing the fileInputStream
//	                fis.close();
//	            }
//	            if (fos != null) {
//	 
//	                // Closing the fileOutputStream
//	                fos.close();
//	            }
//	        }
//	    }
	    

	        /**
	         * Generate a file 's sha1 hash code.
	         * @param filePath file path
	         * @return sha1 hash code of this file
	         * @throws IOException if file doesn't or other IOException
	         * @throws NoSuchAlgorithmException
	         */
	        public static String sha1Code(String filePath) throws IOException, NoSuchAlgorithmException {
	            FileInputStream fileInputStream = new FileInputStream(filePath);
	            MessageDigest digest = MessageDigest.getInstance("SHA-1");
	            DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
	            byte[] bytes = new byte[1024];
	            // read all file content
	            while (digestInputStream.read(bytes) > 0);

//	            digest = digestInputStream.getMessageDigest();
	            byte[] resultByteArry = digest.digest();
	            return bytesToHexString(resultByteArry);
	        }

	        /**
	         * Convert a array of byte to hex String. <br/>
	         * Each byte is covert a two character of hex String. That is <br/>
	         * if byte of int is less than 16, then the hex String will append <br/>
	         * a character of '0'.
	         *
	         * @param bytes array of byte
	         * @return hex String represent the array of byte
	         */
	        public static String bytesToHexString(byte[] bytes) {
	            StringBuilder sb = new StringBuilder();
	            for (byte b : bytes) {
	                int value = b & 0xFF;
	                if (value < 16) {
	                    // if value less than 16, then it's hex String will be only
	                    // one character, so we need to append a character of '0'
	                    sb.append("0");
	                }
	                sb.append(Integer.toHexString(value));
	            }
	            return sb.toString();
	        }
	        
	        public static String readFile(String path, Charset encoding) throws IOException
	        {
	            byte[] encoded = Files.readAllBytes(Paths.get(path));
	            return new String(encoded, encoding);
	        }
	        public String getFileName() {
	        	return origFilePath.substring(origFilePath.lastIndexOf('/') + 1);
	        }
	}
