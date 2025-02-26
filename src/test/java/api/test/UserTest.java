package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;
public class UserTest {
 
	Faker faker;
    User userPayload;
    public Logger logger;
    
	@BeforeClass
	public void setup()
	{
		faker = new Faker();
		userPayload=new User();
	
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		logger=LogManager.getLogger(this.getClass());
		
		}
	@Test(priority=1)
	public void testPostUser() {
		logger.info("***************Creating user*************");
		Response response=UserEndpoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("***************User created*************");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("***************Get user details*************");
		Response response =UserEndpoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		//response.getStatusCode();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("***************User details displayed*************");
	}
	
	@Test(priority=3) 
	public void testUpdateUser() {
		logger.info("***************Updating user*************");
		userPayload.setUsername(faker.name().username());
//		userPayload.setFirstname(faker.name().firstName());
//		userPayload.setLastname(faker.name().lastName());
//		userPayload.setEmail(faker.internet().safeEmailAddress());
//		userPayload.setPassword(faker.internet().password(5, 10));
//		userPayload.setPhone(faker.phoneNumber().cellPhone());

		Response response=UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		//Updated data
		Response responseAfterUpdate=UserEndpoints.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		logger.info("***************User updated successfully*************");
	}
	@Test(priority=4)
	public void testDeleteUserByName() {
		logger.info("***************Delete User*************");
		Response response =UserEndpoints.deleteUser(this.userPayload.getUsername());
		//response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("***************User deleted successfully*************");
	}
	
}
