package spring.tool;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

public class ContextTool {
	
	@Autowired
	private ServletContext mServletContext;

	private static String mContextPath = null;
	private static ContextTool thiz;
	
	{
		thiz = this;
	}
	
	public static String getContextPath() {
		if (mContextPath == null)
//			mContextPath = request.getContextPath() + "/";
			mContextPath = thiz.mServletContext.getContextPath() + "/";
		return mContextPath;
	}
	
	public static ServletContext getServletContext() {
		return thiz.mServletContext;
	}

}
