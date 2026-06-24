import java.awt.image.BufferedImage; //QR code
import java.io.IOException; //exception handling
import java.io.InputStreamReader; //input 
import java.util.ArrayList; //array list data structure
//import java.util.Scanner; //do not need because using buffered reader instead of scanner
import java.io.BufferedReader; //to read in loop
import java.io.File; //file handling
import java.io.FileWriter; //file write
import java.text.DateFormat; //date
import java.text.SimpleDateFormat; //date format
import java.util.Calendar; //for current time
import javax.imageio.ImageIO; //for capturing image
import swiftbot.Button; //for enabling buttons
import swiftbot.ImageSize; //to set the size of the image
import swiftbot.SwiftBotAPI; //to connect to the robot


public class Assignment_3 {
	static SwiftBotAPI PoniyoNeer = new SwiftBotAPI();  //the name of my SwiftBot is Poniyo Neer!
	static int F=0, B=0, R=0, L=0, T=0, W=0;  //to count the commands for button A (additional requirement)
	static long startingTime = System.currentTimeMillis();  //time when the program starts for button Y (additional requirement)
	public static void main(String[] args) throws InterruptedException, IOException {   //exception handling for scanning
		//Welcome message
		System.out.println("Hello, I am Poniyo Neer!");
		System.out.println("Welcome to \"Navigate\" program.");
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("Commands:");
		System.out.println("F [duration] [speed] = Forwawrd movement");
		System.out.println("B [duration] [speed] = Backwawrd movement");
		System.out.println("R [duration] [speed] = Right turn");
		System.out.println("L [duration] [speed] = Left turn");
		System.out.println("T [step(s)] = Retrace previous movement(s)");
		System.out.println("W = Write log to a text file");
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("Please scan the QR code using the SwiftBot to execute a command.");
		System.out.println("The QR code should fill the whole of a smart phone screen or a third of a sheet of A4 paper.");
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("Press \"8\" and enter from your keyboard to start scanning");
		System.out.println("                     OR");
		System.out.println("Press a button on your SwiftBot to execute a feature:");
		System.out.println("A = Write the number of times the SwiftBot executed each command.");
		System.out.println("B = Write the most frequent command the SwiftBot executed.");
		System.out.println("X = Terminate the program or exit");
		System.out.println("Y = Write the total duration of execution \n");
		

		ArrayList<String> trace = new ArrayList<String>();   //for memorising the movements
		
		
			//enabling buttons
		//terminating program
		PoniyoNeer.enableButton(Button.X, () -> {System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("Terminating the program.\n\n");
		System.out.println("Thanks for using \"Navigate\" program.");
		System.out.println("Had fun today! See you again.");
		System.out.println("---------------------------------------------------------------------------------------------");
		System.exit(0);});
		
			
		//display the number of times the SwiftBot executed each command (additional requirement)
		PoniyoNeer.enableButton(Button.A, () -> {
		System.out.println("The number of times the SwiftBot execeuted each command: ");
		System.out.printf("F = %d times, B = %d times, R = %d times, L = %d times, T = %d times,  W = %d times \n ",F,B,R,L,T,W);
		System.out.println("---------------------------------------------------------------------------------------------");
		});
		
		
		//display the most frequent command the SwiftBot executed (additional requirement)
		PoniyoNeer.enableButton(Button.B, () -> {
		int mx = Math.max(F, B);   //comparing the numbers of F & B which one is maximum and storing it in mx
		mx = Math.max(mx, R);      //comparing  mx & R which one is maximum and storing it in mx
		mx = Math.max(mx, L);
		mx = Math.max(mx, T);
		mx = Math.max(mx, W);
		
		if(mx==0)    //if maximum number is 0 
		{
			System.out.println("Have not executed any command yet!");
		}		
		else
		{
			if(F==mx)
			{
				System.out.println("The most frequent command the SwiftBot executed is: F");
			}
			 if(B==mx)
			{
				System.out.println("The most frequent command the SwiftBot executed is: B");
			}
			 if(R==mx)
			{
				System.out.println("The most frequent command the SwiftBot executed is: R");
			}
			 if(L==mx)
			{
				System.out.println("The most frequent command the SwiftBot executed is: L");
			}
			 if(T==mx)
			{
				System.out.println("The most frequent command the SwiftBot executed is: T");
			}
			 if(W==mx)
			{
				System.out.println("The most frequent command the SwiftBot executed is: W");
			}
		}		
		System.out.println("---------------------------------------------------------------------------------------------");
		});
		
		
		//display the total duration of execution (additional requirement)
		PoniyoNeer.enableButton(Button.Y, () -> {
			
		long totalDuration = System.currentTimeMillis()-startingTime;  //current time - starting time
		
		System.out.println("The total duration of execution is : " + (totalDuration/1000) + " seconds");
		System.out.println("---------------------------------------------------------------------------------------------");
	    });
		
		
		BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));  //for taking input 8 to start scanning
//		Scanner scan = new Scanner (System.in);  //using buffered reader instead of scanner 
		
				
		while(true)
		{
			String input = scan.readLine();  //for inputting from keyboard

			String decodedText = QRCodeScan(input);  //received text from QR code

			//adding the inputs in the array list for memorising
			trace.add(decodedText);
			
			//an array named movement, which will split the input into different paths so that it can differentiate the integers	
			String[] movement = decodedText.split(" ");  //it will detect the spaces between the values					
								
//			System.out.println(movement.length);  //just for checking
						
			String direction = movement[0]; // 1st element of the array
								
			int duration = 0; // 2nd element of the array	
			//try catch shows error
			if(movement.length >= 2) duration = Integer.valueOf(movement[1])*1000;  //because not all the inputs have 2nd element
			
			int speed = 0; // 3rd element of the array  //an error came for not declaring the variable before try statement
			//try catch shows error
			if(movement.length == 3) speed = Integer.valueOf(movement[2]);   //because not all the inputs have 3rd element
			
			
			
				//going forward  //"==" does not work but ".equals" work because it is string
				if (direction.equals("F")) { 
					F++; //to count the total number of execution of this command for button A (additional requirement)
					functionForF(duration,speed);
					System.out.println("\n\nExecution successful. \nTook an image of the front and saved it in a text file.\n\n");
					System.out.println("Press \"8\" and enter from your keyboard to start scanning");
					System.out.println("Press button X to terminate the program or exit");
					System.out.println("---------------------------------------------------------------------------------------------");
				}
							
				
				//going backward
				else if (direction.equals("B")) {
					B++;
					functionForB(duration,speed);
					System.out.println("\n\nExecution successful. \nTook an image of the front and saved it in a text file.\n\n");
					System.out.println("Press \"8\" and enter from your keyboard to start scanning");
					System.out.println("Press button X to terminate the program or exit");
					System.out.println("---------------------------------------------------------------------------------------------");					
				}
			

			    //for turning right
				else if (direction.equals("R")) {
					R++;
					functionForR(duration,speed);
					System.out.println("\n\nExecution successful. \nTook an image of the front and saved it in a text file.\n\n");
					System.out.println("Press \"8\" and enter from your keyboard to start scanning");
					System.out.println("Press button X to terminate the program or exit");
					System.out.println("---------------------------------------------------------------------------------------------");
				}
								

			//for turning left
				else if (direction.equals("L")) {
					L++;
					functionForL(duration,speed);
					System.out.println("\n\nExecution successful. \nTook an image of the front and saved it in a text file.\n\n");
					System.out.println("Press \"8\" and enter from your keyboard to start scanning");
					System.out.println("Press button X to terminate the program or exit");
					System.out.println("---------------------------------------------------------------------------------------------");
				}
				

			//for retracing
				else if (direction.equals("T")) {
					T++;
					//underlights blue when retracing the previous movements (additional requirement)
					int[] colourToLightUp = {0,255,0};
					try {
					PoniyoNeer.fillUnderlights(colourToLightUp);
					} catch (Exception e) {
					e.printStackTrace();
					}
									
					int start = trace.size() - 1;     //starts from last index
					int steps = Integer.parseInt(movement[1]);   //steps to retrace
					
					if(steps > trace.size())
					{
						System.out.println("\nThe number of movements to retrace exceeded the number of movements that the SwiftBot has executed so far. \n");
						System.out.println("Please input a valid number\n\n");
					}
					else
					{
						System.out.println("Executing the last " + (movement[1]) + " movements again (the previous " + (movement[1]) + " movement commands starting from the last one.)\n\n");
						
						while(start>=0 && steps>0)
						{
							String decodedText1 = trace.get(start); //first command to execute from array list
							start--;  //search the commands backwards from array list
							
							//an array named retracing, which will split the saved inputs as strings from the array list into different paths 	
							String[] retracing = decodedText1.split(" ");
							
							String command = retracing[0]; // 1st element of the array
							
							int durationRetrace = 0; // 2nd element of the array								
							try{       //try catch because not all the inputs have 2nd element
								durationRetrace = Integer.valueOf(retracing[1])*1000; 		
							}
							catch(Exception e) {}

							int speedRetrace = 0; // 3rd element of the array  //an error came for not declaring the variable before try statement
							try{       //try catch because not all the inputs have 3rd element
								speedRetrace = Integer.valueOf(retracing[2]); 				
							}
							catch(Exception e) {}
							
																
							if((retracing[0].equals("F") || retracing[0].equals("B") || retracing[0].equals("R")|| retracing[0].equals("L"))) 
								steps--;    //remaining steps to execute
							else continue;  //ignore invalid inputs
							
														
							
							//going forward
							if (command.equals("F")) {  
								F++;
								functionForF(durationRetrace,speedRetrace);								
							}
										
							
							//going backward
							else if (command.equals("B")) {
								B++;
								functionForB(durationRetrace,speedRetrace);								
							}
						

						    //for turning right
							else if (command.equals("R")) {
								R++;
								functionForR(durationRetrace,speedRetrace);
							}
											

						//for turning left
							else if (command.equals("L")) {
								L++;
								functionForL(durationRetrace,speedRetrace);
							}							
						}
						System.out.println("\n\nExecution successful. \nTook an image of the front and saved it in a text file.\n\n");
					}
					
					TakePicture();
					PoniyoNeer.disableUnderlights();
					
					System.out.println("Press \"8\" and enter from your keyboard to start scanning");
					System.out.println("Press button X to terminate the program or exit");
					System.out.println("---------------------------------------------------------------------------------------------");				
				}
			

				//for W command
				else if (direction.equals("W")) {
					W++;
					//flash yellow underlight when the “W” command is given (additional requirement)
					int[] colourToLightUp = {255,0,255};
					try {
					PoniyoNeer.fillUnderlights(colourToLightUp);
					} catch (IllegalArgumentException e) {
					e.printStackTrace();
					}

					//current time  
					DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String content = "\n"+"The current time is " + (dateFormat.format(cal.getTime()))+"\n\nDisplaying all the commands that the SwiftBot has received: \n";

					//all the log of commands
					for(String i:trace) 
					{									
						content += i + "\n";  //add every input and a new line
					}
					content += "\n";
					printInFile(content);  //write in a text file

					System.out.println("\nThe current time and all the log of commands have been saved in a \"text file\".");
					System.out.println("Along with saving it in a text file, it displays below..");

					System.out.println(content);

					System.out.println("\nPress \"8\" and enter from your keyboard to start scanning");
					System.out.println("Press button X to terminate the program or exit");
					System.out.println("---------------------------------------------------------------------------------------------");

					PoniyoNeer.disableUnderlights();
				}
				

				//for the invalid commands
				else {    
					//flash purple underlight when the command type is invalid (additional requirement)
					int[] colourToLightUp = {255,255,0};
					try {
					PoniyoNeer.fillUnderlights(colourToLightUp);
					} catch (IllegalArgumentException e) {
					e.printStackTrace();
					}
					
					System.out.println("Command type is invalid. ");
					System.out.println("Please input a valid command (F, B, R, L, T, W) \n" );
					System.out.println("Press \"8\" and enter from your keyboard to start scanning");
					System.out.println("Press button X to terminate the program or exit");
					System.out.println("---------------------------------------------------------------------------------------------");
				
				}
				
				PoniyoNeer.disableUnderlights();				
		}		
	}
	
	
	//method for writing in a text file
	private static void printInFile(String content) throws IOException{
		FileWriter writer = new FileWriter("W.txt");
		writer.write(content);
		writer.close();	
	}
	
	
	//method for going forward 
	private static void functionForF(int duration,int speed){
		
		//underlights green when going forward (additional requirement)
		int[] colourToLightUp = {0,0,255};  //red,blue,green
		try {
		PoniyoNeer.fillUnderlights(colourToLightUp);
		} catch (IllegalArgumentException e) {
		e.printStackTrace();
		}
		

	if (CheckDuration(duration) && CheckSpeed(speed))				
	{			
		try {			
			System.out.println("Moving forward for " + Integer.toString(duration/1000) + " seconds at a speed of " + speed );
			
			PoniyoNeer.move(speed,speed,duration);
			
			TakePicture();		
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	  }
	
	PoniyoNeer.disableUnderlights();
	}
	
	
	//method for going backward
	private static void functionForB(int duration, int speed){
		
		//underlights red when going backward (additional requirement)
		int[] colourToLightUp = {255,0,0};
		try {
		PoniyoNeer.fillUnderlights(colourToLightUp);
		} catch (IllegalArgumentException e) {
		e.printStackTrace();
		}
		
	if (CheckDuration(duration) && CheckSpeed(speed))
	{
		try {
			System.out.println("Moving backward for " + Integer.toString(duration/1000) + " seconds at a speed of " + speed );

			PoniyoNeer.move(-speed,-speed,duration); //the speed will become negative for going backward
			
			TakePicture();
						
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	  }
	
	PoniyoNeer.disableUnderlights();
	}
	
	
	//method for turning right orthogonally
	private static void functionForR (int duration,int speed){
		
		//underlights green when turning right(additional requirement)
		int[] colourToLightUp = {0,0,255};
		try {
		PoniyoNeer.fillUnderlights(colourToLightUp);
		} catch (Exception e) {
		e.printStackTrace();
		}
		

		if (CheckDuration(duration) && CheckSpeed(speed))
		{
			try {
				System.out.println("Turning right orthogonally for " + Integer.toString(duration/1000) + " seconds at a speed of " + speed );

				PoniyoNeer.move(28,-20,1000); //turns right 90 degrees in a hard surface like floor or table
				PoniyoNeer.move(speed,speed,duration);
				
				TakePicture();
							
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PoniyoNeer.disableUnderlights();
	}
	
	
	//method for turning left orthogonally
	private static void functionForL(int duration,int speed){
		
		//underlights red when turning left(additional requirement)
		int[] colourToLightUp = {255,0,0};
		try {
		PoniyoNeer.fillUnderlights(colourToLightUp);
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		
	if (CheckDuration(duration) && CheckSpeed(speed))
	{
		try {
			System.out.println("Turning left orthogonally for " + Integer.toString(duration/1000) + " seconds at a speed of " + speed );
			
			PoniyoNeer.move(-20,28,1000);
			PoniyoNeer.move(speed,speed,duration);
			
			TakePicture();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	
	PoniyoNeer.disableUnderlights();
	}
	
	
	//method for checking valid duration
	private static boolean CheckDuration (int Duration) {
		
		if (0>Duration || Duration>6000) 
		{
			System.out.println("Could not run the program because the duration exceeds 6 seconds.");
			System.out.println("Please input a duration that is less than 6 seconds. \n");
			System.out.println("Press \"8\" and enter from your keyboard to start scanning");
			System.out.println("Press button X to terminate the program or exit");
			System.out.println("---------------------------------------------------------------------------------------------");
			return false;
		}
		else
			return true;
	}

	
	//method for checking valid speed
	private static boolean CheckSpeed (int Speed) {
		
		if (0>Speed || Speed>100)
		{
			System.out.println("Could not run the program because the speed exceeds 100.");
			System.out.println("Please input a speed that is less than 100. \n");
			System.out.println("Press \"8\" and enter from your keyboard to start scanning");
			System.out.println("Press button X to terminate the program or exit");
			System.out.println("---------------------------------------------------------------------------------------------");
			return false;
		}
		else
			return true;
	}

	
	//method for taking picture (additional requirement)
	private static void TakePicture () {
		
	try {
		BufferedImage img = PoniyoNeer.takeStill(ImageSize.SQUARE_144x144);
		ImageIO.write(img, "jpg", new File("/home/pi/Documents/testImage.jpg"));
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

	
	//method for QR code scan
	private static String QRCodeScan (String input) throws InterruptedException { //exception for thread.sleep
		
		BufferedImage img = null;
		if (input.equals("8")) {    //input == "8" did not work
			//underlights teel blue while searching for QR code (additional requirement)
			int[] colourToLightUp = {0,255,255};
			try {
			PoniyoNeer.fillUnderlights(colourToLightUp);
			} catch (Exception e) {
			e.printStackTrace();
			}
			
			System.out.println("Taking a capture in 5 seconds..\n");
			img = PoniyoNeer.getQRImage();	//Gets an image to use for detection when pressing 8, and stores it as a buffer.	
		}
	
		String decodedText = " ";
		
		if (img != null) {
			try{ 
				decodedText = PoniyoNeer.decodeQRImage(img); //Tries to find a QR code within a given BufferedImage to decode & return its contents as a String.
				
				if(!decodedText.isEmpty()){
					System.out.println(decodedText); 
					PoniyoNeer.disableUnderlights();
				}
				
				
				else
				{
					Thread.sleep(5000); //to give 5 seconds to the user for input (non-functional requirement)
					System.out.println("Could not scan QR code.");
					System.out.println("The QR code might be invalid or too close/far from the SwiftBot. Please try again");
					System.out.println("Please scan the QR code using the SwiftBot to execute a command.");
					System.out.println("---------------------------------------------------------------------------------------------");
					
					while (decodedText.isEmpty()) //try to find QR code until founded
					{						
						img = PoniyoNeer.getQRImage();
						decodedText = PoniyoNeer.decodeQRImage(img);
						
						if(!decodedText.isEmpty()){
							System.out.println(decodedText);
						}
					}					
				}
			}
			
			catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
		return decodedText;
	}
}