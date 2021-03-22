package edu.pitt.cs.cs1632;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class StepDefinitions {
	private RentACat r;
	private String listResult;
	private int rentedCatID, returnedCatID;
	private boolean isReturned, isRented;

	@Given("a rent-a-cat facility")
	public void aRentACatFacility() {
		r = RentACat.createInstance();
	}
	
	@Given("no cats")
	public void noCats() {
		// nothing to do really
	}
	
	@Given("a cat with ID {int} and name {string}")
	public void aCatWithIDAndName(Integer id, String name) {
		r.addCat(new Cat(id, name));
		System.out.println("Created cat " + id + ". " + name);
	}
	
	@When("I list the cats")
	public void iListTheCats() {
		listResult = r.listCats();
	}
	
	@When("I rent cat number {int}")
	public void iRentCatNumber(Integer id) {
		try {
			if(!r.getCat(id).getRented()){
				rentedCatID = id;
				r.rentCat(id);
				isRented = true;
				System.out.println("Rented cat " + id + ". " + r.getCat(id).getName());
			} else {
				isRented = false;
			}
		}catch (Exception e) {
			isRented = false;
		}
	}

	@When("I return cat number {int}")
	public void iReturnCatNumber(Integer id) {
		returnedCatID = id;
		isReturned = r.returnCat(id);
		if(isReturned) {
			System.out.println("Returned cat " + id + ". " + r.getCat(id).getName());
		}
	}
	
	@Then("the listing is: {string}")
	public void theListingIs(String result) {
		assertEquals(result.replaceAll("\\\\n", "\n"), listResult);
	}
	
	@Then("the rent is successful")
	public void theRentIsSuccessful() {
		assertTrue(isRented);
	}

	@Then("the rent is unsuccessful")
	public void theRentIsUnsuccessful() {
		assertFalse(isRented);
	}

	@Then("the return is successful")
	public void theReturnIsSuccessful() {
		assertTrue(isReturned);
	}

	@Then("the return is unsuccessful")
	public void theReturnIsUnsuccessful() {
		assertFalse(isReturned);
	}
}
