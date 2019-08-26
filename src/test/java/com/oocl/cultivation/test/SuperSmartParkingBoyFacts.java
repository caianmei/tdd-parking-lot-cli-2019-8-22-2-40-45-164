package com.oocl.cultivation.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.SuperSmartParkingBoy;

public class SuperSmartParkingBoyFacts {
	
	@Test
	void should_park_cars_to_max_capacity_rate_parking_lot_of_parkingLot_list() {
		final int capacity1 = 3;
		final int capacity2 = 2;
		ParkingLot parkingLot1 = new ParkingLot(capacity1);
		ParkingLot parkingLot2 = new ParkingLot(capacity2);
		List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
		parkingLots.add(parkingLot1);
		parkingLots.add(parkingLot2);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLots);

		superSmartParkingBoy.park(new Car());
		superSmartParkingBoy.park(new Car());
		ParkingTicket parkingTicket = superSmartParkingBoy.park(new Car());
		assertNotNull(parkingTicket);
	}

}
