package com.oocl.cultivation;

public interface Parking {
	
	ParkingTicket park(ParkingLot parkingLot, Car car);
	
	Car fetch(ParkingLot parkingLot, ParkingTicket ticket);
}
