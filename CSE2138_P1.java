import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
public class CSE2138_P1 {
// This method takes input then converts it to binary.
    static String intToBin(String num) {
		int number = Integer.parseInt(num);
        String result = "";
        boolean bool = true;
        char ch;
        do{
            ch = (char)((number % 2) + '0');
            result = ch + result;
            number /= 2;
            if(number == 0){
                bool = false;
            }
        }while(bool);
        return result;
	}


// This method takes float number and converts it to binary.
	static String floatToBin(String floatNum,int floatSize) {
		int fractionSize=0;
		int exponent = 0;
		//this part is for byte size
		switch(floatSize) {
		case 1:
			fractionSize=4;
			exponent=3;
			break;
		case 2:
			fractionSize=7;
			exponent=127;
			break;
		case 3:
			fractionSize=13;
			exponent=511;
			break;
		case 4:
			fractionSize=13;
			exponent=2047;
			break;
		}
	//to determine if the given input is ngative or not
	boolean negative = false;
        if(floatNum.charAt(0) == '-'){
            negative = true;
            floatNum = floatNum.substring(1);
        }
	//this part divides the input into two from the floating point
        String result = "";
        int length = floatNum.length();
        String tempStr = "";
        String tempStr2 = "";
        float tempF;
        for(int i = 0;i < length;i++){
            if(floatNum.charAt(i) == '.'){
                tempStr = floatNum.substring(0, i);
                tempStr2 = "0." + floatNum.substring(i+1,length);
                break;
            }
        }

	//this part converts after the floating point
        tempF = Float.parseFloat(tempStr2);
        tempStr2 = "";
        int j=0;
        for(;j < fractionSize+1;j++){
            tempF *= 2;
            if(tempF >= 1.0){
                tempStr2 += '1';
                tempF -= 1.0;
            }
            else{
                tempStr2 += '0';
            }
        }

        //this part calculates the exponent
        for(int i = 0;i < intToBin(tempStr).length();i++){
            if(intToBin(tempStr).charAt(i) == '1'){
                exponent += ((intToBin(tempStr).length())-1-i);
                break;
            }
        }

	//this part is for rounding
        String fraction = intToBin(tempStr).substring(1) + tempStr2.substring(0,fractionSize);
        if(fraction.charAt(fractionSize)=='0') {
        	fraction = fraction.substring(0,fractionSize);
        }else {
        	String finalBin = "";
            for(int i = fractionSize;i >= 0;i--){
                if(fraction.charAt(i) == '0'){
                    finalBin = fraction.substring(0, i) + '1' + finalBin;
                    break;
                }
                else{
                    finalBin += '0';
                }
            }
            
            fraction = finalBin;
        	
        }
        fraction = fraction.substring(0,fractionSize);
        if(floatSize==4)
        	fraction+="000000";
        if(negative){
            tempStr = "1" + intToBin(exponent + "")  + ""+ fraction;
        }
        else{
            tempStr = "0" + intToBin(exponent + "")  + ""+ fraction;
        }
        result = tempStr;
        return result;
	}


	// This method takes unsigned number and converts it to binary.
	static String unsignedToBin(String num) {
        num = num.substring(0,num.length()-1);
		int number = Integer.parseInt(num);
        String result = "";
        boolean bool = true;
        char ch;
        do{
            ch = (char)((number % 2) + '0');
            result = ch + result;
            number /= 2;
            if(number == 0){
                bool = false;
            }
        }while(bool);

        for(int i = result.length();i <= 16;i++){
            result = '0' + result;
        }
        result = result.substring(1);
        return result;
	}


	// This method takes decimal number and converts it to binary.
    public static String decToBi(String num){
        boolean negative=false;
        if(num.charAt(0)=='-') {
            num=num.substring(1);
            negative=true;
        }
        int number = Integer.parseInt(num);
        String result = "";
        boolean bool = true;
        char ch;
        do{
            ch = (char)((number % 2) + '0');
            result = ch + result;
            number /= 2;
            if(number == 0){
                bool = false;
            }
        }while(bool);

        for(int i = result.length();i <= 16;i++){
            result = '0' + result;
        }
        result = result.substring(1);
	
	//this part of the code converts the number to two's complement representation if the number is negative
        if(negative){
            String negativeString= "";
            for(int i = 0;i < result.length();i++){
                if(result.charAt(i) == '0'){
                    negativeString += '1';
                }
                else{
                    negativeString += '0';
                }
            }
            String finalBin = "";
            for(int i = negativeString.length()-1;i >= 0;i--){
                if(negativeString.charAt(i) == '0'){
                    finalBin = negativeString.substring(0, i) + '1' + finalBin;
                    break;
                }
                else{
                    finalBin += '0';
                }
            }
            result = finalBin;
        }
        
        return result;
    }
	

 // This method takes binary number and converts it to hexadecimal.
	static String binToHex(String entered,boolean shouldConvert) {
		int length=entered.length();
		if(length%4==1)
			entered="000"+entered;
		if(length%4==2)
			entered="00"+entered;
		if(length%4==3)
			entered="0"+entered;
		int currentNumber;
		String currentString="";
		for(int a=0;a<length;) {
			currentNumber=0;
			for(int b=8;b>=1;b/=2,a++) {
				if(entered.charAt(a)=='1')
					currentNumber+=b;
			}
			if(currentNumber<10) {
				currentString+=(char)(currentNumber+'0');
			}else {
				currentString+=(char)(currentNumber-10+'A');
			}
			
		}
		
        return currentString;
		
	}

	
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner (System.in);
		Scanner inputLine = new Scanner(System.in);
// Next two line takes file name from user.
		System.out.print("Enter file name:");
		String inputFileName=input.next();
		// Next three line asks to user whether little or big endian will be used.
		System.out.print("Enter byte ordering type(Little Endian or Big Endian):");
		String byteOrderingType=inputLine.nextLine();
		boolean LittleEndian=true;

		if(byteOrderingType.equals("l"))
			LittleEndian=true;
		if(byteOrderingType.equals("b"))
			LittleEndian=false;

		System.out.print("Enter the size of floating point data type:");
		int floatSize=input.nextInt();

		File inputfile=new File(inputFileName);
		PrintWriter printToFile=new PrintWriter("outputfile.txt");
		Scanner inputFile = new Scanner (inputfile);
//This while loop takes input from file and prints to file after converting to hex by using methods.
		while (inputFile.hasNext()) {
			String line=inputFile.next();
			if(line.contains(".")) {
				printToFile.println(binToHex(floatToBin(line,floatSize),LittleEndian));
			}else if(line.contains("u")) {
				printToFile.println(binToHex(unsignedToBin(line),LittleEndian));
				}else {
					printToFile.println(binToHex(decToBi(line),LittleEndian));
				}

			}
		inputLine.close();
		printToFile.close();
		input.close();
		inputFile.close();
	}
}