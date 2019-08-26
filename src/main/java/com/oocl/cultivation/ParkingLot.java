package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingTicket;

public class ParkingLot {
	private final int capacity;
	public Map<ParkingTicket, Car> cars = new HashMap<>();

	public ParkingLot() {
		this(10);
	}

	public ParkingLot(int capacity) {
		this.capacity = capacity;
	}

	public int getAvailableParkingPosition() {
		int cantAvailableParkingPosition = 0;
		for (Map.Entry<ParkingTicket, Car> car : cars.entrySet()) {
			if (!car.getKey().getIsUse()) {
				cantAvailableParkingPosition++;
			}
		}
		return getCapacity() - cantAvailableParkingPosition;
	}

	public ParkingTicket park(Car car) {
		if (getAvailableParkingPosition() < 1) {
			return null;
		}
		ParkingTicket parkingTicket = new ParkingTicket();
		parkingTicket.setIsUse(false);
		cars.put(parkingTicket, car);
		return parkingTicket;
	}

	public Car fetch(ParkingTicket ticket) {
		if (ticket.getIsUse()) {
			return null;
		}
		Car car = cars.get(ticket);
		if (car != null) {
			ticket.setIsUse(true);
			cars.put(ticket, car);
		}
		return car;

	}

	public int getCapacity() {
		return capacity;
	}
}
