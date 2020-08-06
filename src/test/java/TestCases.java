import org.json.simple.JSONObject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestCases {

	@Test
	public void get_data() {
		baseURI="http://localhost:3000/Car";
		given().
		get().
		then().
		statusCode(200).
		log().all();
		Response res= RestAssured.given().get();
	
		
		List<String> mList=res.jsonPath().getList("make");
		
	List <String>cList=res.jsonPath().getList("metadata.Color");
	List<String> vList=res.jsonPath().getList("vin");
		
		for(int i=0;i<mList.size();i++) {
			if(mList.get(i).equals("Tesla") && cList.get(i).equals("Blue")) {
				System.out.println(res.jsonPath().getString("metadata["+i+"]"));
			}
		}
		
		List<Integer> pList=res.jsonPath().getList("perdayrent.Price");
		List<Integer> dList=res.jsonPath().getList("perdayrent.Discount");
		
		
		float discount=pList.get(0)-dList.get(0);
		String mk1=mList.get(0);
		String mk2=mList.get(0);
		String vin1=vList.get(0);
		String vin2=vList.get(0);
		float temp=(float)pList.get(0);
		float num=0;
		for(int i=0;i<pList.size();i++) {
			num=pList.get(i)-dList.get(i);
			if(temp>pList.get(i)) {
				temp=pList.get(i);
				mk1=mList.get(i);
				vin1=vList.get(i);
			}
			if(discount>num) {
				discount=(int) num;
				mk2=mList.get(i);
				vin2=vList.get(i);
			}
		}
		
		System.out.println("\n*****************************************************");
	System.out.println(" lowest price car is "+temp+" car make "+ mk1+" vin is "+ vin1);	
	
	System.out.println(" lowest price after discount  car is "+discount +" car make "+ mk2+" vi is "+vin2);
	
	System.out.println("\n*****************************************************");
	float totalExpanse=0;
	float profit=0;
	float revenue=0;
	
	List<Float> cost=res.jsonPath().getList("metrics.yoymaintenancecost");
	
	List<Float> depr=res.jsonPath().getList("metrics.depreciation");
	List<Integer> count=res.jsonPath().getList("metrics.rentalcount.yeartodate");
	
	totalExpanse=(cost.get(0)).floatValue()+((depr.get(0)).floatValue());
	revenue=count.get(0)*(pList.get(0)-dList.get(0));
	temp=(float)revenue-totalExpanse;
	for(int i=0;i<mList.size();i++) {
		totalExpanse=cost.get(i)+depr.get(i);
		revenue=count.get(i)*(pList.get(i)+dList.get(i));
		profit=revenue-totalExpanse;
		if(temp>profit) {
			profit=temp;
			mk1=mList.get(i);
			vin1=vList.get(i);
		}
		
		
		System.out.println(" car "+ mk1+" Vin is "+vin1+" profit is "+ profit );
	}
	System.out.println("\n*****************************************************");
	
	System.out.println("");
	
	System.out.println("*****************************************************");
	System.out.println(" highest profit car is "+ mk1+" Vin is "+vin1+" profit is "+ profit );
	
	}
	
	
	

	
}
