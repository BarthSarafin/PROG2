package lab02.TrafficLight;

// Template for Lab P02 Excercise 1
class TrafficLight {
	private boolean red;

	public TrafficLight() {
		red = true;
	}

	public synchronized void passby() {
		if(red){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public synchronized void switchToRed() {
		this.red = true;
	}

	public synchronized void switchToGreen() {
		// waiting cars can now pass by
		this.red = false;
		notifyAll();
	}
}

class Car extends Thread {
	private TrafficLight[] trafficLights;
	private int pos;

	public Car(String name, TrafficLight[] trafficLights) {
		super(name);
		this.trafficLights = trafficLights;
		pos = 0;				// start at first light
		start();
	}

	public int position() {
		return pos;
	}
	
	private void gotoNextLight() {
		pos++;
		pos = pos%trafficLights.length;
	}

	public void run() {
		while (true) {
			try {
				sleep((int)(Math.random() * 500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			trafficLights[pos].passby();
			gotoNextLight();
		}
	}
}

public class TrafficLightOperation {
	public static void main(String[] args) {
		TrafficLight[] trafficLights = new TrafficLight[7];
		Car[] cars = new Car[20];
		for (int i = 0; i < trafficLights.length; i++)
			trafficLights[i] = new TrafficLight();
		for (int i = 0; i < cars.length; i++) {
			cars[i] = new Car("Car " + i, trafficLights);
		}

		// Simulation
		while (true) {
			for (int i = 0; i < trafficLights.length; i = i + 2) {

				// Display state of simulation
				System.out.println(
					"=====================================================");
				for (int j = 0; j < trafficLights.length; j++) {
					String prefix;
					if (j == i || j == i + 1)
						prefix = "->";
					else
						prefix = "  ";
					System.out.print(prefix + " at Light " + j + ":");
					for (int k = 0; k < cars.length; k++) {
						if (cars[k].position() == j)
							System.out.print(" " + k);
					}
					System.out.println();
				}
				System.out.println(
					"=====================================================");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException logOrIgnore) {;}
				trafficLights[i].switchToGreen();
				if (i + 1 < trafficLights.length) {
					trafficLights[i + 1].switchToGreen();
				}
				// green period
				try {
					Thread.sleep((int) (Math.random() * 500));
				} catch (InterruptedException logOrIgnore) {;}
				trafficLights[i].switchToRed();
				if (i + 1 < trafficLights.length)
					trafficLights[i + 1].switchToRed();
				// red period
				try {
					Thread.sleep(1000);
				} catch (InterruptedException logOrIgnore) {;}
			}
		}
	}
}
