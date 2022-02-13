import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.Random;

/**
 * 
 * @author saebastion cole
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		final GpioController gpio = GpioFactory.getInstance();
		
		/**
		 * Assigns GPIO 02 pin to the button and waits for a momentary button to be pressed.
		 */
		final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		
		/**
		 * Assigns GPIO 01 to the relay's IN port
		 */
		final GpioPinDigitalOutput relay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyRelay", PinState.LOW);
		
		/**
		 * Random object to randomly switch the relay when pressed.
		 */
		Random rand = new Random();
		
		
		button.setShutdownOptions(true);
		relay.setShutdownOptions(true, PinState.LOW);

        //Create and register gpiopinslistener.
        button.addListener(new GpioPinListenerDigital() {
        	
        	
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            	//Checks if the button is pressed.
                if (event.getState().toString().equals("HIGH")) {
                	
                	//Assigns a random value that has a 1/6 chance to hit.
                	int randNum = rand.nextInt(6);
                	if (randNum == 0) {
                		//Turn on the relay for 1/4 of a second.
                		relay.pulse(250);
                	}
                	
                	
                }
            	
            }

        });


        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
        }

	}

}