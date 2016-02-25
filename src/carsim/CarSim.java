package carsim;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Each turn of a simulation, car A has a 10% chance of moving 5 feet forward, 
 * and a 90% chance of staying still. Similarly, each turn of the simulation 
 * car B has a 60% chance of moving 1 feet forward, and a 40% chance of staying 
 * still. A race consists of however many turns in the simulation it takes to 
 * travel 100ft. Write a simulation to run such a race, and run it 100 times. 
 * Count how many times car A wins and how many times car B wins. Do the results 
 * match up with what you would expect from a mathematical analysis of the problem?
 * 
 * 
 * round(random  + %)  : then action
 * 
 * mathematical:
 * Chance of moving a foot per turn
 * CarA (5*.1)*100 50%  CarB (1*.6)*100 60%
 * 
 * Mathematically I would expect car B to win 10% more races than car A
 * However, the sim gives CarB about 50% more wins!
 * 
 * @author rzabrisk
 *
 */
public class CarSim {
	static class Car {
		int wins;
		float distance = 0f;
	}
	
	static class CarA extends Car {
		final static float FIVE_FEET_FORWARD_CHANCE = 0.10f;
		final static float STAY_STILL_CHANCE = 0.90f;
		
	}
	
	static class CarB extends Car {
		final static float ONE_FEET_FORWARD_CHANCE = 0.60f;
		final static float STAY_STILL_CHANCE = 0.40f;
			
	}
	
	static final float RACE_LENGTH = 100.0f;
	
	static CarA carA = new CarA();
	static CarB carB = new CarB();

	private static Comparator<Car> finishOrderComparator = new Comparator<CarSim.Car>() {

		@Override
		public int compare(Car o1, Car o2) {
			if (o1.distance > o2.distance) 
				return -1;
			else if (o1.distance < o2.distance)
				return 1;
			else
				return 0;

		}
	};
	
	

	public static void main(String[] args) {
		Car[]  raceCars = new Car[] { carA, carB };
			
		for (int i = 0; i<100 ;i++) {
			Car winner = race(raceCars);
			winner.wins++;
			reset(raceCars);
		}
		
		System.out.printf("Car A wins %d times  Car B wins %d times \n",carA.wins, carB.wins);
	}
	
	private static void reset(Car[] raceCars) {
			for (Car car : raceCars) {
				car.distance = 0.0f;
			}
	}
	
	private static Car race(Car[] raceCars) {
		while (determineRaceInProgress(raceCars)) {
			simulationTurn(raceCars);
		}
		

		return determineWinner(raceCars);
	}
	
	/**
	 * Interrogates progress of race cars.
	 * 
	 * @param raceCars
	 * @return
	 */
	private static boolean determineRaceInProgress(Car[] raceCars) {
		
		for (Car car : raceCars) {
			if (car.distance >= RACE_LENGTH) {
				return false;
			}
		}
		return true;
	}
	
	private static Car determineWinner(Car[] raceCars) {
		
		Arrays.sort(raceCars, finishOrderComparator);

		return raceCars[0];
	}

	
	private static void simulationTurn(Car[] raceCars) {
		
		for (Car car : raceCars) {
		
			if (car instanceof CarA) {
				if (chance(CarA.FIVE_FEET_FORWARD_CHANCE)) {
					car.distance += 5.0f;
				}
			} else if (car instanceof CarB) {
				if (chance(CarB.ONE_FEET_FORWARD_CHANCE)) {
					car.distance += 1.0f;
				}
			}
			
		}
	}
	
	private static boolean chance(float percentage) {
		
		return (Math.random() + percentage) >= 1.0f;
	}

}
