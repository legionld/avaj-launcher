package simulator.utils;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hashing {

	public static String[] firsValidate(String filename)
			throws NoSuchAlgorithmException {
		byte[] bt = null;

		try {
			bt = Files.readAllBytes(Paths.get(filename));
		} catch (IOException e) {
			ErrorsProvider.printError(20);
			System.out.println(e);
			return null;
		}

		String text = new String(bt);
		String[] lines = text.split("\n");

		if (lines.length == 1) {
			try {
				bt = Files.readAllBytes(Paths.get(Settings.md5Directory + lines[0]));
			}
			catch (IOException e) {
				ErrorsProvider.printError(30);
				System.out.println(e);
				return null;
			}
		} else {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bt);
			byte[] digest = md.digest();
			String hash = DatatypeConverter
					.printHexBinary(digest).toUpperCase();
			try {
				Files.write(Paths.get(Settings.md5Directory + hash), bt);
			} catch (IOException e) {
				System.out.println(e);
				ErrorsProvider.printError(21);
			}
		}

		return lines;
	}
}
