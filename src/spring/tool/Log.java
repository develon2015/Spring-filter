package spring.tool;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss] ");
	
	public static synchronized void d(String content) {
		d(content, true);
	}

	public static synchronized void d(String content, boolean msg) {
		try {
			content = sdf.format(new Date()) + content;
			System.out.println(content);
			content = content.replace(" ", "_").replace("\n", "---").replace("\\", "\\\\");
			if (msg)
				Runtime.getRuntime().exec(String.format("cmd /c \"toast %s\"", content));
		} catch (IOException e) {
		}
	}
}
