package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.ServiceManager;
import com.oocl.cultivation.SmartParkingBoy;
import com.oocl.cultivation.SuperSmartParkingBoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ParkingBoyFacts {
	@Test
	void should_park_a_car_to_a_parking_lot_and_get_it_back() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		Car car = new Car();

		ParkingTicket ticket = parkingBoy.park(car);
		Car fetched = parkingBoy.fetch(ticket);

		assertSame(fetched, car);
	}

	@Test
	void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		Car firstCar = new Car();
		Car secondCar = new Car();

		ParkingTicket firstTicket = parkingBoy.park(firstCar);
		ParkingTicket secondTicket = parkingBoy.park(secondCar);

		Car fetchedByFirstTicket = parkingBoy.fetch(firstTicket);
		Car fetchedBySecondTicket = parkingBoy.fetch(secondTicket);

		assertSame(firstCar, fetchedByFirstTicket);
		assertSame(secondCar, fetchedBySecondTicket);
	}

	@Test
	void should_not_fetch_any_car_once_ticket_is_wrong() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		Car car = new Car();
		ParkingTicket wrongTicket = new ParkingTicket();

		ParkingTicket ticket = parkingBoy.park(car);

		assertNull(parkingBoy.fetch(wrongTicket));
		assertSame(car, parkingBoy.fetch(ticket));
	}

	@Test
	void should_query_message_once_the_ticket_is_wrong() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		ParkingTicket wrongTicket = new ParkingTicket();

		parkingBoy.fetch(wrongTicket);
		String message = parkingBoy.getLastErrorMessage();

		assertEquals("Unrecognized parking ticket.", message);
	}

	@Test
	void should_clear_the_message_once_the_operation_is_succeeded() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		ParkingTicket wrongTicket = new ParkingTicket();

		parkingBoy.fetch(wrongTicket);
		assertNotNull(parkingBoy.getLastErrorMessage());

		ParkingTicket ticket = parkingBoy.park(new Car());
		assertNotNull(ticket);
		assertNull(parkingBoy.getLastErrorMessage());
	}

	@Test
	void should_not_fetch_any_car_once_ticket_is_not_provided() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		Car car = new Car();

		ParkingTicket ticket = parkingBoy.park(car);

		assertNull(parkingBoy.fetch(null));
		assertSame(car, parkingBoy.fetch(ticket));
	}

	@Test
	void should_query_message_once_ticket_is_not_provided() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

		parkingBoy.fetch(null);

		assertEquals("Please provide your parking ticket.", parkingBoy.getLastErrorMessage());
	}

	@Test
	void should_not_fetch_any_car_once_ticket_has_been_used() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		Car car = new Car();

		ParkingTicket ticket = parkingBoy.park(car);
		parkingBoy.fetch(ticket);

		assertNull(parkingBoy.fetch(ticket));
	}

	@Test
	void should_query_error_message_for_used_ticket() {
		ParkingLot parkingLot = new ParkingLot();
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
		Car car = new Car();

		ParkingTicket ticket = parkingBoy.park(car);
		parkingBoy.fetch(ticket);
		parkingBoy.fetch(ticket);

		assertEquals("Unrecognized parking ticket.", parkingBoy.getLastErrorMessage());
	}

	@Test
	void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

		parkingBoy.park(new Car());

		assertNull(parkingBoy.park(new Car()));
	}

	@Test
	void should_get_message_if_there_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

		parkingBoy.park(new Car());
		parkingBoy.park(new Car());

		assertEquals("The parking lot is full.", parkingBoy.getLastErrorMessage());
	}

	@Test
	void should__park_cars_to_first_parking_lot_if_first_parking_lot_is_enough_position_when_park_given_two_parking_lot_capacity_is_1() {
		final int capacity = 1;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
		
		 parkingBoy.park(new Car());
		
		assertEquals(0,parkingLot1.getAvailableParkingPosition());
	}
	
	@Test
	void should_park_and_fetch_cars_to_first_parking_lot_if_first_parking_lot_is_enough_position_when_park_given_two_parking_lot_capacity_is_1() {
		final int capacity = 1;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
		Car car = new Car();
		
		ParkingTicket ticket =  parkingBoy.park(car);		
		Car fetched = parkingBoy.fetch(ticket);
		
		assertNotNull(ticket);
		assertEquals(car, fetched);
		
	}
	
	
	@Test
	void should_park_cars_to_second_parking_lot_if_first_parking_lot_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

		parkingBoy.park(new Car());
		ParkingTicket parkingTicket = parkingBoy.park(new Car());
		assertNotNull(parkingTicket);
	}

	@Test
	void should_not_park_cars_to_all_parking_lot_if_all_parking_lot_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLots);

		parkingBoy.park(new Car());
		parkingBoy.park(new Car());
		parkingBoy.park(new Car());
		assertEquals("The parking lot is full.", parkingBoy.getLastErrorMessage());
	}

	@Test
	void should_not_fetch_any_car_once_ticket_is_wrong_when_parking_boy_fetch_given_two_parking_lot_capacity_is_1(){
		final int capacity = 1;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
		Car car = new Car();
		
		ParkingTicket ticket =  parkingBoy.park(car);	
		
		assertNull(parkingBoy.fetch(null));
		assertSame(car, parkingBoy.fetch(ticket));
	}
	
	@Test
	void should_query_message_once_ticket_is_not_provided_when_parking_boy_fetch_given_two_parking_lot_capacity_is_1() {
		final int capacity = 1;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
		
		parkingBoy.fetch(null);

		assertEquals("Please provide your parking ticket.", parkingBoy.getLastErrorMessage());
	}
	
	@Test
	void should_query_error_message_for_used_ticket_when_parking_boy_fetch_given_two_parking_lot_capacity_is_1() {
		final int capacity = 1;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLots);
		
		ParkingTicket ticket = parkingBoy.park(new Car());
		parkingBoy.fetch(ticket);
		parkingBoy.fetch(ticket);
		
		assertEquals("Unrecognized parking ticket.", parkingBoy.getLastErrorMessage());
	}

}
