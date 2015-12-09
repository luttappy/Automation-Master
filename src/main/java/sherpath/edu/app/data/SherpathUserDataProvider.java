package sherpath.edu.app.data;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

import sherpath.edu.app.util.SherpathUtils;




public class SherpathUserDataProvider {

	@DataProvider(name="userdata")
	public static Object[][] userSampleData() 
	{
		
		SherpathUtils su = new SherpathUtils();
		
		Map<String,String> userDataMap = new HashMap<String,String>();
		
		return new Object[][] { { userDataMap } };

		
	}
	
    
	
}
