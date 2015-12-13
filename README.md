# KooKooMashUp
Get BMTC bus route details by sending SMS

# AN SMS Based application to know the BMTC buses and their route details

Steps to use the service: Before using the service, you must ensure “DND” is not activated on your number.

~~~
Go to your phone’s “Messages” menu and select new message

And Type  "HACKAR source to destination" like HACKAR majestic to electronic city or
HACKAR btm layout water tank to eletronic city, you message should start with "HACKAR" folowed by 
single space and source name followed by "to" and then destination name.

And send the message to " 09227507512 " 

If given source and destination is valid, you will receive the sms with bus route details.

~~~

# USING IVR to know the BMTC bus route details.

Steps: still optimation required.
~~~~
Dial the number : "08030178467" from your phone.
After successful dail, IVR system will handle the call and and it will 
play the greeting messages and it will ask you to say about journey/trip source to destination details. 
Suppose you want to go to majestic bus stand from electronic city, start by 
saying electronic city to mejestic. or majestic to electronic city.

Then wait for 15-20 seconds,
During this period system will try to find the valid route details if any available and play back to you.
Don't hangup the phone wait for the system response.

Basically it records the voice over the phone,
Converts the recored voice to .wav and .flac format and sends to Google Speech Engine to translate
from voice to text. Then it finds the source and destination places name from extracted text and search for 
the bus routes and if any match found, it plays back over the phone.
