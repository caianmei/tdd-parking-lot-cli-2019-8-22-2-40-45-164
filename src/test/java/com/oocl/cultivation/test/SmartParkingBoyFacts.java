package com.oocl.cultivation.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.SmartParkingBoy;

public class SmartParkingBoyFacts {

	@Test
	void should_park_cars_to_max_capacity_parking_lot_of_parkingLot_list_when_smart_parking_boy_park_car() {
		final int capacity1 = 1;
		final int capacity2 = 2;
		ParkingLot parkingLot1 = new ParkingLot(capacity1);
		ParkingLot parkingLot2 = new ParkingLot(capacity2);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);

		ParkingTicket parkingTicket = smartParkingBoy.park(new Car());
		
		assertNotNull(parkingTicket);
		assertEquals(1, parkingLot2.getAvailableParkingPosition());
	}

	@Test
	void should_park_multiple_cars_to_some_parking_lot_and_get_them_back_and_change_availableParkingPosition_when_smart_parking_boy_park_cars() {
		final int capacity1 = 2;
		final int capacity2 = 3;
		ParkingLot parkingLot1 = new ParkingLot(capacity1);
		ParkingLot parkingLot2 = new ParkingLot(capacity2);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
		Car firstCar = new Car();
		Car secondCar = new Car();
		
		ParkingTicket fetchedByFirstTicket = smartParkingBoy.park(firstCar);
		ParkingTicket fetchedBySecondTicket = smartParkingBoy.park(secondCar);
		
		
		
		assertNotNull(fetchedByFirstTicket);
		assertNotNull(fetchedBySecondTicket);	
		
		assertEquals(2, parkingLot2.getAvailableParkingPosition());
		assertEquals(1, parkingLot1.getAvailableParkingPosition());
		
		assertSame(firstCar, smartParkingBoy.fetch(fetchedByFirstTicket));						
		assertSame(secondCar, smartParkingBoy.fetch(fetchedBySecondTicket));
		
		assertEquals(3, parkingLot2.getAvailableParkingPosition());
		assertEquals(2, parkingLot1.getAvailableParkingPosition());
	}
	
	@Test
	void should_get_message_if_there_is_not_enough_position_when_smart_parking_boy_park_car() {
		ParkingLot parkingLot = new ParkingLot(0);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);

		smartParkingBoy.park(new Car());

		assertEquals("The parking lot is full.", smartParkingBoy.getLastErrorMessage());
	}
}
