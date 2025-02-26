package api.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTest {

	@Test(priority=1, dataProvider="Data",dataProviderClass=DataProviders.class)
	public void tesPostUser(int id,String username,String firstname,String lastname,String email,String password,String phone)
	{
		User userPayload=new User();
		userPayload.setId(id);
		userPayload.setUsername(username);
		userPayload.setFirstname(firstname);
		userPayload.setLastname(lastname);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		
		Response response=UserEndpoints.createUser(userPayload);
		//response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	
		public void testDeleteUser(String username) {
		Response response=UserEndpoints.deleteUser(username);
		//response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		
	}
}
