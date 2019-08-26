package com.oocl.cultivation.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.ServiceManager;
import com.oocl.cultivation.SmartParkingBoy;
import com.oocl.cultivation.SuperSmartParkingBoy;

public class ServiceManagerFacs {

	@Test
	void should_park_cars_to_myself_manage_parking_lot_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingTicket parkingTicket = serviceManager.park(parkingLot, new Car());
		assertNotNull(parkingTicket);
	}

	@Test
	void should_cant_park_cars_to_other_manage_parking_lot_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		serviceManager.park(parkingLot1, new Car());
		assertEquals("This parking lot dont belong with you", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_fetch_cars_to_myself_manage_parking_lot_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingTicket parkingTicket = serviceManager.park(parkingLot, new Car());
		assertNotNull(serviceManager.fetch(parkingLot, parkingTicket));
	}

	@Test
	void should_cant_fetch_cars_to_other_manage_parking_lot_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingTicket parkingTicket = serviceManager.park(parkingLot, new Car());
		serviceManager.fetch(parkingLot1, parkingTicket);
		assertEquals("This parking lot dont belong with you", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_cant_park_cars_to_myself_manage_parking_lot_of_parkingLot_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		serviceManager.park(parkingLot, new Car());
		serviceManager.park(parkingLot, new Car());
		assertEquals("The parking lot is full.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_query_message_once_ticket_is_not_provided_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);

		serviceManager.fetch(parkingLot, null);

		assertEquals("Please provide your parking ticket.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_not_fetch_any_car_once_ticket_has_been_used_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);

		Car car = new Car();

		ParkingTicket ticket = serviceManager.park(parkingLot, car);
		serviceManager.fetch(parkingLot, ticket);

		assertNull(serviceManager.fetch(parkingLot, ticket));
	}

	@Test
	void should_query_error_message_for_used_ticket_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		Car car = new Car();

		ParkingTicket ticket = serviceManager.park(parkingLot, car);
		serviceManager.fetch(parkingLot, ticket);
		serviceManager.fetch(parkingLot, ticket);

		assertEquals("Unrecognized parking ticket.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_call_myself_manage_parking_boy_park_cars_to_myself_manage_parking_lot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingBoy parkingBoy = new ParkingBoy();
		serviceManager.setParkingBoys(parkingBoy);
		ParkingTicket parkingTicket = serviceManager.callParkingBoyPark(parkingBoy, new Car());

		assertNotNull(parkingTicket);
	}

	@Test
	void should_call_myself_manage_parking_boy_fetch_cars_to_myself_manage_parking() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingBoy parkingBoy = new ParkingBoy();
		serviceManager.setParkingBoys(parkingBoy);

		ParkingTicket parkingTicket = serviceManager.callParkingBoyPark(parkingBoy, new Car());

		assertNotNull(serviceManager.callParkingBoyFetch(parkingBoy, parkingTicket));
	}
	
	@Test
	void should_call_myself_manage_parking_boy_park_car_but_cant_park_cars_to_myself_manage_parking_lot_of_parkingLot_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingBoy parkingBoy = new ParkingBoy();
		serviceManager.setParkingBoys(parkingBoy);

		serviceManager.callParkingBoyPark(parkingBoy, new Car());
		serviceManager.callParkingBoyPark(parkingBoy, new Car());
		assertEquals("The parking lot is full.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_query_message_once_call_myself_manage_parking_boy_fetch_car_ticket_is_not_provided_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);

		ParkingBoy parkingBoy = new ParkingBoy();
		serviceManager.setParkingBoys(parkingBoy);

		serviceManager.callParkingBoyFetch(parkingBoy, null);

		assertEquals("Please provide your parking ticket.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_not_fetch_any_car_once_call_myself_manage_parking_boy_fetch_car_ticket_has_been_used_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingBoy parkingBoy = new ParkingBoy();
		serviceManager.setParkingBoys(parkingBoy);

		serviceManager.callParkingBoyPark(parkingBoy, new Car());
		ParkingTicket ticket = serviceManager.park(parkingLot, new Car());
		serviceManager.callParkingBoyFetch(parkingBoy, ticket);

		assertNull(serviceManager.callParkingBoyFetch(parkingBoy, ticket));
	}

	@Test
	void should_query_error_message_for_used_call_myself_manage_parking_boy_fetch_car_ticket_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ParkingBoy parkingBoy = new ParkingBoy();
		serviceManager.setParkingBoys(parkingBoy);

		serviceManager.callParkingBoyPark(parkingBoy, new Car());
		ParkingTicket ticket = serviceManager.park(parkingLot, new Car());
		serviceManager.callParkingBoyFetch(parkingBoy, ticket);
		serviceManager.callParkingBoyFetch(parkingBoy, ticket);

		assertEquals("Unrecognized parking ticket.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_call_other_manage_parking_boy_park_cars_to_myself_manage_parking_boy_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot1);
		ParkingBoy parkingBoy = new ParkingBoy(parkingLot2);

		serviceManager.callParkingBoyPark(parkingBoy, new Car());

		assertEquals("This parking boy dont belong with you", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_call_other_manage_parking_boy_fetch_cars_to_myself_manage_parking_lot_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);

		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ServiceManager serviceManager1 = new ServiceManager(parkingLot);

		ParkingBoy parkingBoy = new ParkingBoy();
		serviceManager1.setParkingBoys(parkingBoy);
		ParkingTicket parkingTicket = serviceManager1.callParkingBoyPark(parkingBoy, new Car());
		serviceManager.callParkingBoyFetch(parkingBoy, parkingTicket);
		assertEquals("This parking boy dont belong with you", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_call_myself_manage_smart_parking_boy_park_cars_to_myself_manage_parking_lot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
		serviceManager.setSmartParkingBoys(smartParkingBoy);

		ParkingTicket parkingTicket = serviceManager.callSmartParkingBoyPark(smartParkingBoy, new Car());
		assertNotNull(parkingTicket);
	}

	
	@Test
	void should_call_myself_manage_smart_parking_boy_park_car_but_cant_park_cars_to_myself_manage_parking_lot_of_parkingLot_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
		serviceManager.setSmartParkingBoys(smartParkingBoy);
		
		
		serviceManager.callSmartParkingBoyPark(smartParkingBoy, new Car());
		serviceManager.callSmartParkingBoyPark(smartParkingBoy, new Car());
		assertEquals("The parking lot is full.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_query_message_once_call_myself_manage_smart_parking_boy_fetch_car_ticket_is_not_provided_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
		serviceManager.setSmartParkingBoys(smartParkingBoy);
		serviceManager.callSmartParkingBoyFetch(smartParkingBoy, null);

		assertEquals("Please provide your parking ticket.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_not_fetch_any_car_once_call_myself_manage_smart_parking_boy_fetch_car_ticket_has_been_used_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
		serviceManager.setSmartParkingBoys(smartParkingBoy);

		serviceManager.callSmartParkingBoyPark(smartParkingBoy, new Car());
		ParkingTicket ticket = serviceManager.park(parkingLot, new Car());
		serviceManager.callSmartParkingBoyFetch(smartParkingBoy, ticket);

		assertNull(serviceManager.callSmartParkingBoyFetch(smartParkingBoy, ticket));
	}

	@Test
	void should_query_error_message_for_used_call_myself_manage_smart_parking_boy_fetch_car_ticket_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
		serviceManager.setSmartParkingBoys(smartParkingBoy);

		serviceManager.callSmartParkingBoyPark(smartParkingBoy, new Car());
		ParkingTicket ticket = serviceManager.park(parkingLot, new Car());
		serviceManager.callSmartParkingBoyFetch(smartParkingBoy, ticket);
		serviceManager.callSmartParkingBoyFetch(smartParkingBoy, ticket);

		assertEquals("Unrecognized parking ticket.", serviceManager.getLastErrorMessage());
	}
	
	@Test
	void should_call_myself_manage_smart_parking_boy_fetch_cars_to_myself_manage_parking() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
		serviceManager.setSmartParkingBoys(smartParkingBoy);

		ParkingTicket parkingTicket = serviceManager.callSmartParkingBoyPark(smartParkingBoy, new Car());

		assertNotNull(serviceManager.callSmartParkingBoyFetch(smartParkingBoy, parkingTicket));
	}

	@Test
	void should_call_other_manage_smart_parking_boy_park_cars_to_myself_manage_parking_boy_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot1);
		SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot2);

		serviceManager.callSmartParkingBoyPark(smartParkingBoy, new Car());

		assertEquals("This smart parking boy dont belong with you", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_call_other_manage_smart_parking_boy_fetch_cars_to_myself_manage_parking_lot_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);

		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ServiceManager serviceManager1 = new ServiceManager(parkingLot);

		SmartParkingBoy smartParkingBoy = new SmartParkingBoy();
		serviceManager1.setSmartParkingBoys(smartParkingBoy);
		
		ParkingTicket parkingTicket = serviceManager1.callSmartParkingBoyPark(smartParkingBoy, new Car());
		serviceManager.callSmartParkingBoyFetch(smartParkingBoy, parkingTicket);
		assertEquals("This smart parking boy dont belong with you", serviceManager.getLastErrorMessage());
	}
	
	@Test
	void should_call_myself_manage_super_smart_parking_boy_park_cars_to_myself_manage_parking_lot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
		serviceManager.setSuperSmartParkingBoys(superSmartParkingBoy);

		ParkingTicket parkingTicket = serviceManager.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());
		assertNotNull(parkingTicket);
	}

	
	@Test
	void should_call_myself_manage_super_smart_parking_boy_park_car_but_cant_park_cars_to_myself_manage_parking_lot_of_parkingLot_is_not_enough_position() {
		final int capacity = 1;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
		serviceManager.setSuperSmartParkingBoys(superSmartParkingBoy);
		
		
		serviceManager.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());
		serviceManager.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());
		assertEquals("The parking lot is full.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_query_message_once_call_myself_manage_super_smart_parking_boy_fetch_car_ticket_is_not_provided_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
		serviceManager.setSuperSmartParkingBoys(superSmartParkingBoy);
		
		serviceManager.callSuperSmartParkingBoyFetch(superSmartParkingBoy, null);

		assertEquals("Please provide your parking ticket.", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_not_fetch_any_car_once_call_myself_manage_super_smart_parking_boy_fetch_car_ticket_has_been_used_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
		serviceManager.setSuperSmartParkingBoys(superSmartParkingBoy);
		
		serviceManager.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());
		ParkingTicket ticket = serviceManager.park(parkingLot, new Car());
		serviceManager.callSuperSmartParkingBoyFetch(superSmartParkingBoy, ticket);

		assertNull(serviceManager.callSuperSmartParkingBoyFetch(superSmartParkingBoy, ticket));
	}

	@Test
	void should_query_error_message_for_used_call_myself_manage_super_smart_parking_boy_fetch_car_ticket_by_manager() {
		ParkingLot parkingLot = new ParkingLot();
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
		serviceManager.setSuperSmartParkingBoys(superSmartParkingBoy);
		
		serviceManager.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());
		ParkingTicket ticket = serviceManager.park(parkingLot, new Car());
		serviceManager.callSuperSmartParkingBoyFetch(superSmartParkingBoy, ticket);
		serviceManager.callSuperSmartParkingBoyFetch(superSmartParkingBoy, ticket);

		assertEquals("Unrecognized parking ticket.", serviceManager.getLastErrorMessage());
	}
	
	@Test
	void should_call_myself_manage_super_smart_parking_boy_fetch_cars_to_myself_manage_parking() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
		serviceManager.setSuperSmartParkingBoys(superSmartParkingBoy);

		ParkingTicket parkingTicket = serviceManager.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());

		assertNotNull(serviceManager.callSuperSmartParkingBoyFetch(superSmartParkingBoy, parkingTicket));
	}

	@Test
	void should_call_other_manage_super_smart_parking_boy_park_cars_to_myself_manage_parking_boy_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot1 = new ParkingLot(capacity);
		ParkingLot parkingLot2 = new ParkingLot(capacity);
		ServiceManager serviceManager = new ServiceManager(parkingLot1);
		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot2);

		serviceManager.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());

		assertEquals("This super smart parking boy dont belong with you", serviceManager.getLastErrorMessage());
	}

	@Test
	void should_call_other_manage_super_smart_parking_boy_fetch_cars_to_myself_manage_parking_lot_of_parkingLot() {
		final int capacity = 3;
		ParkingLot parkingLot = new ParkingLot(capacity);

		ServiceManager serviceManager = new ServiceManager(parkingLot);
		ServiceManager serviceManager1 = new ServiceManager(parkingLot);

		SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy();
		serviceManager1.setSuperSmartParkingBoys(superSmartParkingBoy);
		ParkingTicket parkingTicket = serviceManager1.callSuperSmartParkingBoyPark(superSmartParkingBoy, new Car());
		serviceManager.callSuperSmartParkingBoyFetch(superSmartParkingBoy, parkingTicket);
		assertEquals("This super smart parking boy dont belong with you", serviceManager.getLastErrorMessage());
	}
}
