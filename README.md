# Error Correcting Encoder Decoder

Uses Hamming code to encode a text file, create random 1-bit errors to each byte and then find and correct the errors to restore the original text.

## Things learned 

Bit shifting and bit masking

Significant bits, parity bits and how to use the XOR operator to find and correct errors

Hamming(7,4) code

Reading and writing files with bytes instead of strings or integers

Using a hex editor to examine files at the binary level

### Details

User options:

* Encode - Opens file send.txt, encodes it in Hamming(7,4) code and saves it as file encoded.txt.
* Send - Opens file encoded.txt, simulates the errors in its bytes (1 bit per byte) and saves the resulted bytes into the file received.txt.
* Decode - Opens file received.txt and decodes it to normal text. Saves as file decoded.txt.

Tenth project created for JetBrains Academy Java Developer course - medium level project.

### Sample input and output:

Other than the 3 command line options, all of input and output is reading from and writing to text files. The output below shows the files as viewed with a hex editor.

Example 1:

Write a mode: \> encode

send.txt:\
text view: Test\
hex view: 54 65 73 74\
bin view: 01010100 01100101 01110011 01110100

encoded.txt:\
expand: 001100.. 110011.. 000000.. 111100.. 001100.. 110011.. 111100.. 001111.. 001111.. 110011.. 0000....\
parity: 00110011 11001100 00000000 11110000 00110011 11001100 11110000 00111100 00111100 11001100 00000000\
hex view: 33 CC 00 F0 33 CC F0 3C 3C CC 00

Example 2:

Write a mode: \> send

encoded.txt:\
hex view: 33 CC 00 F0 33 CC F0 3C 3C CC 00\
bin view: 00110011 11001100 00000000 11110000 00110011 11001100 11110000 00111100 00111100 11001100 00000000\

received.txt:\
bin view: 10110011 11011100 00100000 11010000 00110010 11011100 10110000 01111100 00110100 10001100 00010000\
hex view: B3 DC 20 D0 32 DC B0 7C 34 8C 10

Example 3:

Write a mode: \> decode

received.txt:\
hex view: B3 DC 20 D0 32 DC B0 7C 34 8C 10\
bin view: 10110011 11011100 00100000 11010000 00110010 11011100 10110000 01111100 00110100 10001100 00010000

decoded.txt:\
correct: 00110011 11001100 00000000 11110000 00110011 11001100 11110000 00111100 00111100 11001100 00000000\
decode: 01010100 01100101 01110011 01110100 0\
remove: 01010100 01100101 01110011 01110100\
hex view: 54 65 73 74\
text view: Test
