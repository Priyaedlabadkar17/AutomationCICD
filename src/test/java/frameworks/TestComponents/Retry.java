package frameworks.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer 
{
	int count = 0;
	int maxTry = 1;

	@Override
	public boolean retry(ITestResult result) {
		if(count<maxTry)
		{
			count++; //before rerun we are incrementing the count.mso that next time if count is 1 then 1<1 no and it will not go to rerun the test
			return true;  //test case finds this true so it will again rerun . 
		}
		return false;
	}

}
