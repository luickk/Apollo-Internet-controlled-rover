package de.ye.rover_led;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import de.ye.rover_eng.ENG_CALC;
import de.ye.rover_main.Main;

public class THREAD_LED implements Runnable {
	
	public static Socket client;
	
	public THREAD_LED(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			//Streams
			OutputStream out = client.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			
			InputStream in = client.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			//-------------
			System.out.println("STATUS CLIENT STREAM THREAD STARTED._LED");
			String s = null;

			while((s = reader.readLine()) != null){
				System.out.println(s);
				if(s.equalsIgnoreCase("ledon_WARN")){
					LED.LED_on_WARN();
					System.out.println("LED ON");
				}
				if(s.equalsIgnoreCase("ledoff_WARN")){
					LED.LED_off_WARN();
					System.out.println("LED OFF");
				}
				if(s.equalsIgnoreCase("ledon_FINE")){
					LED.LED_on_FINE();
					System.out.println("LED ON");
				}
				if(s.equalsIgnoreCase("ledoff_FINE")){
					LED.LED_off_FINE();
					System.out.println("LED OFF");
				}
				if(s.equalsIgnoreCase("ledon_ERR")){
					LED.LED_on_ERR();
					System.out.println("LED ON");
				}
				if(s.equalsIgnoreCase("ledoff_ERR")){
					LED.LED_off_ERR();
					System.out.println("LED OFF");
				}
				if(s.equalsIgnoreCase("pic_load")){
					Process Process1 = Runtime.getRuntime().exec("sudo java -jar loadup.jar");
				}
				if(s.equalsIgnoreCase("POWER_1")){
					ENG_CALC.mode="1";
				} 
				if(s.equalsIgnoreCase("POWER_0")){
					ENG_CALC.mode="0";
				}
			}
			System.out.println("STATUS CLIENT LISTENING READY._LED");
			writer.close();
			reader.close();
			client.close();
			System.out.println("STATUS CLIENT THREAD ENDED._LED");
			Main.STATUS=false;
		} catch (Exception e) {
			LED.LED_off_FINE();
		}
	}

}
