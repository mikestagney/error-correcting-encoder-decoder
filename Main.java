package correcter;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Write a mode:");
        String userChoice = input.nextLine();

        switch (userChoice) {
            case ("encode"):
                encodeMessage();
                break;
            case ("send"):
                scrambleMessage();
                break;
            case ("decode"):
                decodeMessage();
                break;
        }
    }

    static void decodeMessage() {
        try {
            FileInputStream inputFile = new FileInputStream("received.txt");
            FileOutputStream outputFile = new FileOutputStream("decoded.txt");
            int tempByte;
            StringBuilder outputBuffer = new StringBuilder();
            while ((tempByte = inputFile.read()) != -1) {
                int digit3 = tempByte >>> 5 & 0b00000001;
                int digit5 = tempByte >>> 3 & 0b00000001;
                int digit6 = tempByte >>> 2 & 0b00000001;
                int digit7 = tempByte >>> 1 & 0b00000001;

                int parity1 = tempByte >>> 7;
                int parity2 = tempByte >>> 6 & 0b00000001;
                int parity4 = tempByte >>> 4 & 0b00000001;

                boolean isParity1Wrong = parity1 != (digit3 ^ digit5 ^ digit7);
                boolean isParity2Wrong = parity2 != (digit3 ^ digit6 ^ digit7);
                boolean isParity4Wrong = parity4 != (digit5 ^ digit6 ^ digit7);

                if (isParity1Wrong && isParity2Wrong && isParity4Wrong) {
                    digit7 = digit7 ^ 0b00000001;
                } else if (isParity2Wrong && isParity4Wrong) {
                    digit6 = digit6 ^ 0b00000001;
                } else if (isParity1Wrong && isParity4Wrong) {
                    digit5 = digit5 ^ 0b00000001;
                 } else if (isParity1Wrong && isParity2Wrong) {
                    digit3 = digit3 ^ 0b00000001;
                }

                outputBuffer.append(digit3).append(digit5).append(digit6).append(digit7);
                }
                for (int i = 0; i < outputBuffer.length(); i += 8) {
                    String byteText = outputBuffer.substring(i, i + 8);
                    outputFile.write(Integer.parseInt(byteText, 2));
                }
            } catch(IOException e){
                System.out.println("File not found");
            }
        }


        static void encodeMessage() {
            try (FileInputStream inputFile = new FileInputStream("send.txt")) {
                try (FileOutputStream outputFile = new FileOutputStream("encoded.txt")) {

                    StringBuilder binaryInput = new StringBuilder();
                    int currentByte;

                    while ((currentByte = inputFile.read()) != -1) {
                        String temp = Integer.toBinaryString(currentByte);
                        while (temp.length() < 8) {
                            temp = "0" + temp;
                        }
                        binaryInput.append(temp);
                    }
                    for (int i = 0; i < binaryInput.length(); i += 4) {
                        StringBuilder outputByte = new StringBuilder();
                        int digit3 = binaryInput.charAt(i) - 48;
                        int digit5 = binaryInput.charAt(i + 1) - 48;
                        int digit6 = binaryInput.charAt(i + 2) - 48;
                        int digit7 = binaryInput.charAt(i + 3) - 48;

                        int parity1 = digit3 ^ digit5 ^ digit7;
                        int parity2 = digit3 ^ digit6 ^ digit7;
                        int parity4 = digit5 ^ digit6 ^ digit7;

                        outputByte.append(parity1).append(parity2).append(digit3).append(parity4)
                                .append(digit5).append(digit6).append(digit7).append("0");

                        outputFile.write(Integer.parseInt(outputByte.toString(), 2));
                    }
                }
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }

        static void scrambleMessage () {
            try (FileInputStream inputFile = new FileInputStream("encoded.txt")) {
                try (FileOutputStream outputFile = new FileOutputStream("received.txt")) {
                    Random random = new Random();
                    int tempByte;
                    while ((tempByte = inputFile.read()) != -1) {
                        int indexChange = random.nextInt(7) + 1; // moved to 7 and add one to index so eighth bit is always 0
                        tempByte ^= 1 << indexChange;
                        outputFile.write(tempByte);
                    }
                }
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }

    }

